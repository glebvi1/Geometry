package com.example.abcgeometry.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.abcgeometry.R;
import com.example.abcgeometry.domain.Inaccuracy;
import com.example.abcgeometry.domain.Intersection;
import com.example.abcgeometry.domain.Point;

import java.util.ArrayList;

public class TouchEvent extends SurfaceView implements SurfaceHolder.Callback {

    DrawThread thread;
    private final String ISTOUCH = "Was touch";
    private static float EPS = 45f;

    // Struct of figure
    public static ArrayList<Point[]> lines = new ArrayList<Point[]>();
    public static ArrayList<float[]> circles = new ArrayList<>();
    public static ArrayList<Float> lengthLine = new ArrayList<>();
    public static ArrayList<Point> points = new ArrayList<>();
    public static ArrayList<Integer> whatIsFigure = new ArrayList<>();
    public static ArrayList<float[]> constantLine = new ArrayList<>();
    public static ArrayList<float[]> constantCircle = new ArrayList<>();

    // The process of drawing
    public static boolean countFigure = false;
    private boolean wasInaccuracyLine1 = true; // The first point
    private boolean wasInaccuracyLine2 = true; // The second point
    private boolean wasInaccuracyCircle = true;

    private int countTouchLine = 1;
    boolean isTouchLine = false;
    boolean draw = false, draw1 = false;
    boolean canMakeToast = false;

    // The start coordinates
    float touchX1Line = -1000, touchY1Line = -1000, touchX2Line = -1000, touchY2Line = -1000;
    static float touchX1Circle = -1000, touchY1Circle = -1000, touchX2Circle = -1000, touchY2Circle = -1000, radiusCircle = -1000;

    public TouchEvent(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (countFigure) {
            if (canMakeToast) {
                canMakeToast = false;
                Toast.makeText(getContext(), "Превышен лимит фигур!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (ActivityTabTwo.flag == 0) { // Line: flag == 0
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isTouchLine = true;
                }

                while (isTouchLine) {
                    if (countTouchLine == 1) {
                        touchX1Line = event.getX();
                        touchY1Line = event.getY();
                        isTouchLine = false;
                        draw = false;
                        countTouchLine = 2;
                        Log.d(ISTOUCH, "LINE Первое касание " + touchX1Line + " " + touchY1Line);
                        break;
                    }

                    if (countTouchLine == 2 && isTouchLine) {
                        touchX2Line = event.getX();
                        touchY2Line = event.getY();
                        isTouchLine = false;
                        countTouchLine = 1;
                        draw = true;
                        Log.d(ISTOUCH, "LINE Второе касание " + touchX2Line + " " + touchY2Line);
                        break;
                    }

                }
            }

            // ---------------------------   Circle   -----------------------------------

            else if (ActivityTabTwo.flag == 1) { // Circle

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        touchX1Circle = event.getX();
                        touchY1Circle = event.getY();
                        Log.d(ISTOUCH, "CIRCLE Центр окружности " + touchX1Circle + " " + touchY1Circle);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        touchX2Circle = event.getX();
                        touchY2Circle = event.getY();
                        Log.d(ISTOUCH, "CIRCLE Текущая координата " + touchX2Circle + " " + touchY2Circle);
                        break;

                    case MotionEvent.ACTION_UP:
                        draw1 = true;
                        Log.d(ISTOUCH, "CIRCLE Конечная точка");
                        break;
                }

                radiusCircle = (float) Math.pow((touchY1Circle - touchY2Circle) * (touchY1Circle - touchY2Circle)
                        + ((touchX1Circle - touchX2Circle) * (touchX1Circle - touchX2Circle)), 0.5);
            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new DrawThread(surfaceHolder);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }

    class DrawThread extends Thread {

        private SurfaceHolder surfaceHolder;
        private volatile boolean running = true;
        Paint paintCircle = new Paint();
        Paint paintLine = new Paint();
        Paint paintPaint = new Paint();
        Paint paintCenterOfCircle = new Paint();
        Paint paintConstantFigure = new Paint();
        Paint paintInaccuracyCircle = new Paint();
        private int countSecondsAfterAddFigure = 0;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void requestStop() {
            running = false;
        }

        private void drawInaccuracyCircle(Canvas canvas) {
            if (countSecondsAfterAddFigure < 3) {
                canvas.drawCircle(touchX1Line, touchY1Line, 30, paintInaccuracyCircle);
            }
        }

        // The set of param such as color, style and width
        private void setParam(Canvas canvas) {
            // Color of canvas
            canvas.drawColor(Color.BLACK);

            // Color of lines
            paintLine.setColor(getResources().getColor(R.color.lines)); // lines drawing
            paintLine.setStyle(Paint.Style.STROKE);
            paintLine.setStrokeWidth(5);

            // Color of circles
            paintCircle.setColor(getResources().getColor(R.color.circle)); // lines drawing
            paintCircle.setStyle(Paint.Style.STROKE);
            paintCircle.setStrokeWidth(5);

            // Color of circles's center
            paintCenterOfCircle.setColor(Color.YELLOW);
            paintCenterOfCircle.setStrokeWidth(15);

            // Color of intersection point
            paintPaint.setColor(getResources().getColor(R.color.points));
            paintPaint.setStrokeWidth(10);
            paintPaint.setStyle(Paint.Style.STROKE);

            // Color of constants figure
            paintConstantFigure.setStrokeWidth(8);
            paintConstantFigure.setColor(Color.GREEN);

            // Color of inaccuracy
            paintInaccuracyCircle.setStyle(Paint.Style.FILL);
            paintInaccuracyCircle.setColor(Color.RED);
        }

        // The draw point of intersection if it exist
        private void drawPointIntersection(Canvas canvas, Pair<Point, Point> pair) {
            if (pair != null) {
                if (draw1) {
                    TouchEvent.points.add(pair.first);
                } else {
                    canvas.drawPoint(pair.first.getX(), pair.first.getY(), paintPaint);
                }
                if (pair.second != null) {
                    if (draw1) {
                        TouchEvent.points.add(pair.second);
                    } else {
                        canvas.drawPoint(pair.second.getX(), pair.second.getY(), paintPaint);
                    }
                }
            }
        }

        // The method check was inaccuracy?
        // If bool == true, it means that we check point this start coordinates enother line
        // Else we check this finish coordinates
        private void checkInaccuracyLineWithLine(Canvas canvas, Point point, boolean bool) {
            if (bool) {
                if (point != null && wasInaccuracyLine1
                        && touchX1Line != point.getX() && touchY1Line != point.getY()) {
                    touchX1Line = point.getX();
                    touchY1Line = point.getY();
                    wasInaccuracyLine1 = false;
                    drawInaccuracyCircle(canvas);
                }
            } else {
                if (point != null && wasInaccuracyLine2
                        && touchX2Line != point.getX() && touchY2Line != point.getY()) {
                    touchX2Line = point.getX();
                    touchY2Line = point.getY();
                    wasInaccuracyLine2 = false;
                    drawInaccuracyCircle(canvas);
                }
            }
        }

        private void checkInaccuracyLineWithCircle(Point point, boolean bool, int i) {
            if (bool) {
                if (point != null && wasInaccuracyCircle) {
                    wasInaccuracyCircle = false;
                    touchX1Circle = point.getX();
                    touchY1Circle = point.getY();
                }
            } else {
                if (point != null) {
                    touchX2Circle = point.getX();
                    touchY2Circle = point.getY();
                    radiusCircle = lengthLine.get(i);
                }
            }
        }

        private void checkIntersectionCircleWithCircle(Point point) {
            if (draw1) {
                boolean difX = Math.abs(point.getX() - touchX1Line) <= EPS;
                boolean difY = Math.abs(point.getY() - touchY1Line) <= EPS;
                boolean difX2 = Math.abs(point.getX() - touchX2Line) <= EPS;
                boolean difY2 = Math.abs(point.getY() - touchY1Line) <= EPS;
                if (difX && difY) {
                    touchX1Line = point.getX();
                    touchY1Line = point.getY();
                    wasInaccuracyLine1 = false;
                } else if (difX2 && difY2) {
                    touchX2Line = point.getX();
                    touchY2Line = point.getY();
                    wasInaccuracyLine2 = false;
                }
            }
        }

        @Override
        public void run() {
            Log.d("PATH CLASS DRAW", "RUN");

            while (running) {
                Log.d("PATH CLASS DRAW", "while(running)");

                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    setParam(canvas);
                    points = new ArrayList<Point>();

                    // The current points
                    Point currentTouch1 = new Point(touchX1Line, touchY1Line);
                    Point currentTouch2 = new Point(touchX2Line, touchY2Line);

                    // Drawing circle, when user move finger
                    if (ActivityTabTwo.flag == 1) {
                        canvas.drawCircle(touchX1Circle, touchY1Circle, radiusCircle, paintCircle);
                        canvas.drawPoint(touchX1Circle, touchY1Circle, paintCenterOfCircle);
                    }

                    wasInaccuracyLine1 = true; wasInaccuracyLine2 = true;
                    wasInaccuracyCircle = true;

                    int i = 0;
                    // Drawing constant figure
                    for (float[] e : constantLine) {
                        canvas.drawLine(e[0], e[1], e[2], e[3], paintConstantFigure);
                    }

                    // Drawing lines
                    for (Point[] points : lines) {
                        // Drawing current line
                        canvas.drawLine(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), paintLine);
                        Point pointOfStart = points[0];
                        Point pointOfFinish = points[1];
                        Point pointLineWithLine = Intersection.intersectionLineWithLine(pointOfStart, pointOfFinish, currentTouch1, currentTouch2);

                        // The point of intersection line with line
                        if (pointLineWithLine != null) {
                            TouchEvent.points.add(pointLineWithLine);
                        }

                        // The points of intersection line with circle
                        Pair<Point, Point> pairP = Intersection.intersectionLineWithCircle(pointOfStart,
                                pointOfFinish, new Point(touchX1Circle, touchY1Circle), radiusCircle);

                        // Draw with point
                        drawPointIntersection(canvas, pairP);

                        // Inaccuracies line with line
                        Point point1 = Inaccuracy.coincidenceLineWithLine(pointOfStart, currentTouch1);
                        Point point2 = Inaccuracy.coincidenceLineWithLine(pointOfStart, currentTouch2);
                        Point point3 = Inaccuracy.coincidenceLineWithLine(pointOfFinish, currentTouch1);
                        Point point4 = Inaccuracy.coincidenceLineWithLine(pointOfFinish, currentTouch2);

                        checkInaccuracyLineWithLine(canvas, point1, true);
                        checkInaccuracyLineWithLine(canvas, point3, true);
                        checkInaccuracyLineWithLine(canvas, point2, false);
                        checkInaccuracyLineWithLine(canvas, point4, false);

                        // Inaccuracies circles
                        // Inaccuracies center of circle with line
                        Point centerAndLine = Inaccuracy.coincidenceLineWithLine(pointOfStart, new Point(touchX1Circle, touchY1Circle));
                        Point centerAndLine2 = Inaccuracy.coincidenceLineWithLine(pointOfFinish, new Point(touchX1Circle, touchY1Circle));
                        Point centerAndLine3 = Inaccuracy.coincidenceLineWithLine(pointOfStart, new Point(touchX1Circle, touchY1Circle));
                        Point centerAndLine4 = Inaccuracy.coincidenceLineWithLine(pointOfFinish, new Point(touchX1Circle, touchY1Circle));

                        checkInaccuracyLineWithCircle(centerAndLine, true, 0);
                        checkInaccuracyLineWithCircle(centerAndLine2, true, 0);
                        checkInaccuracyLineWithCircle(centerAndLine3, false, i);
                        checkInaccuracyLineWithCircle(centerAndLine4, false, i);

                        i++;
                    }

                    // Drawing circles
                    for (float[] e : circles) {
                        // Drawing circle
                        canvas.drawCircle(e[0], e[1], e[2], paintCircle);
                        canvas.drawPoint(e[0], e[1], paintCenterOfCircle);

                        Point pointPastCenterCircle = new Point(e[0], e[1]);
                        Point pointCurrentCenterCircle = new Point(touchX1Circle, touchY1Circle);

                        // Intersection Circle with circle
                        Pair<Point, Point> pair = Intersection.intersectionCircleWithCircle(
                                pointPastCenterCircle, e[2], pointCurrentCenterCircle, radiusCircle);

                        // The check intersection
                        if (pair != null && draw1) {
                            points.add(pair.first);
                            checkIntersectionCircleWithCircle(pair.first);

                            if (!pair.first.equals(pair.second)) {
                                points.add(pair.second);
                                points.add(pair.first);
                                checkIntersectionCircleWithCircle(pair.second);
                            }
                        }

                        // The intersection line with circle
                        Pair<Point, Point> pairC = Intersection.intersectionLineWithCircle(
                                new Point(touchX1Line, touchY1Line), new Point(touchX2Line, touchY2Line),
                                pointPastCenterCircle, e[2]);

                        // The check intersection
                        if (pairC != null) {
                            if (draw1) {
                                points.add(pairC.first);
                            } else {
                                canvas.drawPoint(pairC.first.getX(), pairC.first.getY(), paintPaint);
                            }
                            if (pairC.second != null) {
                                if (draw1) {
                                    points.add(pairC.second);
                                } else {
                                    canvas.drawPoint(pairC.second.getX(), pairC.second.getY(), paintPaint);
                                }
                            }
                        }

                        // Inaccuracy line
                        // Inaccuracy line with center of circle
                        Point pointC = Inaccuracy.coincidenceLineWithLine(pointPastCenterCircle,
                                currentTouch1);
                        Point pointC2 = Inaccuracy.coincidenceLineWithLine(pointPastCenterCircle,
                                currentTouch2);

                        // Check inaccuracy
                        if (pointC != null && wasInaccuracyLine1) {
                            touchX1Line = e[0];
                            touchY1Line = e[1];
                            wasInaccuracyLine1 = false;
                            drawInaccuracyCircle(canvas);
                        }
                        if (pointC2 != null && wasInaccuracyLine2) {
                            touchX2Line = e[0];
                            touchY2Line = e[1];
                            wasInaccuracyLine2 = false;
                            drawInaccuracyCircle(canvas);
                        }

                        // Inaccuracy line with circle
                        Pair<Point, Integer> coincidence = Inaccuracy.coincidenceLineWithCircle(currentTouch1, currentTouch2,
                                pointPastCenterCircle, e[2]);

                        // The check inaccuracy
                        if (coincidence != null) {
                            if (coincidence.second == 1 && wasInaccuracyLine1) {
                                Log.d("WasTouchLine", "1был");
                                touchX1Line = coincidence.first.getX();
                                touchY1Line = coincidence.first.getY();
                                drawInaccuracyCircle(canvas);
                            } else if (coincidence.second == 2 && wasInaccuracyLine2) {
                                Log.d("WasTouchLine", "2был");
                                touchX2Line = coincidence.first.getX();
                                touchY2Line = coincidence.first.getY();
                                drawInaccuracyCircle(canvas);
                            }
                        }
                    }

                    // Drawing points of intersection
                    for (Point e : points) {
                        canvas.drawPoint(e.getX(), e.getY(), paintPaint);
                    }

                    // The count of figure not more than 10 on canvas
                    if (lines.size() + circles.size() >= 10) {
                        countFigure = true;
                    }
                    else {
                        countFigure = false;
                        // Add line on list
                        if (ActivityTabTwo.flag == 1) {
                            if (draw1) {
                                countSecondsAfterAddFigure = 0;
                                Log.i("COUNTFIGURE line", countFigure + " " + lines.size() + " " + circles.size());
                                whatIsFigure.add(1);
                                circles.add(new float[]{touchX1Circle, touchY1Circle, radiusCircle});
                                Log.d("NewList", circles.size() + "");
                                draw1 = false;
                                canMakeToast = true;
                            }
                        }
                        // Add circle on list
                        else if (ActivityTabTwo.flag == 0 && draw) {
                            countSecondsAfterAddFigure = 0;
                            Log.i("COUNTFIGURE circle", countFigure + " " + lines.size() + " " + circles.size());
                            whatIsFigure.add(0);
                            lines.add(new Point[]{new Point(touchX1Line, touchY1Line), new Point(touchX2Line, touchY2Line)});
                            float temp = (float) Math.sqrt(Math.pow(touchX1Line - touchX2Line, 2) + Math.pow(touchY1Line - touchY2Line, 2));
                            lengthLine.add(temp);
                            canMakeToast = true;

                            draw = false;
                        }
                    }

                    getHolder().unlockCanvasAndPost(canvas);
                    Log.i("COUNTFIGURE 3333", countFigure+" " + lines.size() + " " + circles.size());
                }

                try {
                    Thread.sleep(1000);
                    countSecondsAfterAddFigure++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
