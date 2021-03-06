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

    // Struct of figure
    public static ArrayList<float[]> lines = new ArrayList<>(), circles = new ArrayList<>();
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
        thread = new DrawThread(this.getContext(), surfaceHolder);
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
        Paint pointPaint = new Paint();
        Paint paintCenterOfCircle = new Paint();
        Paint paintConstantFigure = new Paint();
        Paint circlePaint = new Paint();

        public DrawThread(Context context, SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void requestStop() {
            running = false;
        }

        private void setParam(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            paintCircle.setColor(getResources().getColor(R.color.circle)); // lines drawing
            paintCircle.setStyle(Paint.Style.STROKE);
            paintCircle.setStrokeWidth(5);

            paintLine.setColor(getResources().getColor(R.color.lines)); // lines drawing
            paintLine.setStyle(Paint.Style.STROKE);
            paintLine.setStrokeWidth(5);

            pointPaint.setStyle(Paint.Style.STROKE);
            paintConstantFigure.setStrokeWidth(8);
            paintConstantFigure.setColor(Color.GREEN);

            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setColor(Color.RED);

            paintCenterOfCircle.setColor(Color.YELLOW);
            paintCenterOfCircle.setStrokeWidth(15);
        }

        @Override
        public void run() {
            Log.d("PATH CLASS DRAW", "RUN");

            while (running) {
                Log.d("PATH CLASS DRAW", "while(running)");

                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    setParam(canvas);

                    // The current points
                    Point currentTouch1 = new Point(touchX1Line, touchY1Line);
                    Point currentTouch2 = new Point(touchX2Line, touchY2Line);

                    // Drawing circle
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
                    for (float[] e : lines) {

                        // points line
                        canvas.drawLine(e[0], e[1], e[2], e[3], paintLine);
                        Point pastTouch1 = new Point(e[0], e[1]);
                        Point pastTouch2 = new Point(e[2], e[3]);
                        Point pointLineWithLine = Intersection.intersectionLineWithLine(pastTouch1, pastTouch2, currentTouch1, currentTouch2);

                        // ПЕРЕСЕЧЕНИЕ
                        // точка пересечение прямой с прямой
                        if (pointLineWithLine != null) {
                            points.add(pointLineWithLine);
                        }
                        // пересечение прямой с окружностью
                        Pair<Point, Point> pairP = Intersection.intersectionLineWithCircle(pastTouch1,
                                pastTouch2, new Point(touchX1Circle, touchY1Circle), radiusCircle);
                        // проверка пересечения
                        if (pairP != null) {
                            if (draw1) {
                                points.add(pairP.first);
                            } else {
                                canvas.drawPoint(pairP.first.getX(), pairP.first.getY(), pointPaint);
                            }
                            if (pairP.second != null) {
                                if (draw1) {
                                    points.add(pairP.second);
                                } else {
                                    canvas.drawPoint(pairP.second.getX(), pairP.second.getY(), pointPaint);
                                }
                            }
                        }

                        // ПРИТЯЖЕНИЕ ЛИНИИ

                        // "притяжение" точки (past and current) (работает)
                        Point point1 = Inaccuracy.coincidence_of_point(pastTouch1, currentTouch1);
                        Point point2 = Inaccuracy.coincidence_of_point(pastTouch1, currentTouch2);
                        Point point3 = Inaccuracy.coincidence_of_point(pastTouch2, currentTouch1);
                        Point point4 = Inaccuracy.coincidence_of_point(pastTouch2, currentTouch2);
                        // проверка: есть ли "притяжение" прямой с прямой

                        if (point1 != null && wasInaccuracyLine1
                                && touchX1Line != point1.getX() && touchY1Line != point1.getY()) {
                            touchX1Line = point1.getX();
                            touchY1Line = point1.getY();
                            wasInaccuracyLine1 = false;
                            canvas.drawCircle(touchX1Line, touchY1Line, 30, circlePaint);
                        } else if (point3 != null && wasInaccuracyLine1
                                && touchX1Line != point3.getX() && touchY1Line != point3.getY()) {
                            touchX1Line = point3.getX();
                            touchY1Line = point3.getY();
                            wasInaccuracyLine1 = false;
                            canvas.drawCircle(touchX1Line, touchY1Line, 30, circlePaint);
                        }
                        if (point2 != null && wasInaccuracyLine2
                                && touchX2Line != point2.getX() && touchY2Line != point2.getY()) {
                            touchX2Line = point2.getX();
                            touchY2Line = point2.getY();
                            wasInaccuracyLine2 = false;
                            canvas.drawCircle(touchX2Line, touchY2Line, 30, circlePaint);
                        } else if (point4 != null && wasInaccuracyLine2
                                && touchX2Line != point4.getX() && touchY2Line != point4.getY()) {
                            touchX2Line = point4.getX();
                            touchY2Line = point4.getY();
                            wasInaccuracyLine2 = false;
                            canvas.drawCircle(touchX2Line, touchY2Line, 30, circlePaint);
                        }

                        // ПРИТЯЖЕНИЕ ОКРУЖНОСТИ

                        // "прияжение" центра окружности к линии
                        Point centerAndLine = Inaccuracy.coincidence_of_point(pastTouch1, new Point(touchX1Circle, touchY1Circle));
                        Point centerAndLine2 = Inaccuracy.coincidence_of_point(pastTouch2, new Point(touchX1Circle, touchY1Circle));
                        Point centerAndLine3 = Inaccuracy.coincidence_of_point(pastTouch1, new Point(touchX1Circle, touchY1Circle));
                        Point centerAndLine4 = Inaccuracy.coincidence_of_point(pastTouch2, new Point(touchX1Circle, touchY1Circle));
                        if (centerAndLine != null && wasInaccuracyCircle) {
                            wasInaccuracyCircle = false;
                            touchX1Circle = pastTouch1.getX();
                            touchY1Circle = pastTouch1.getY();
                        } else if (centerAndLine2 != null && wasInaccuracyCircle) {
                            wasInaccuracyCircle = false;
                            touchY1Circle = pastTouch2.getY();
                            touchX1Circle = pastTouch2.getX();
                        }
                        if (centerAndLine3 != null) {
                            touchX2Circle = centerAndLine3.getX();
                            touchY2Circle = centerAndLine3.getY();
                            radiusCircle = lengthLine.get(i);
                        } else if (centerAndLine4 != null) {
                            touchY2Circle = centerAndLine4.getY();
                            touchX2Circle = centerAndLine4.getX();
                            radiusCircle = lengthLine.get(i);
                        }

                        // "притяжение" окружности к линии
  /*                      Pair<Point, Point> circleAndLine = Intersection.intersectionLineWithCircle(pastTouch1,
                                pastTouch2, new Point(touchX1Circle, touchY1Circle), radiusCircle);
                        if (circleAndLine != null) { // есть пересечение
                        }*/
                        i++;
                    }

                    for (float[] e : circles) {
                        // точки окружности
                        canvas.drawCircle(e[0], e[1], e[2], paintCircle);
                        canvas.drawPoint(e[0], e[1], paintCenterOfCircle);

                        Point pastTouch = new Point(e[0], e[1]);
                        Point currentTouch = new Point(touchX1Circle, touchY1Circle);

                        // пересечение окружности с окружностью (past и current)
                        Pair<Point, Point> pair = Intersection.intersectionCircleWithCircle(
                                pastTouch, e[2], currentTouch, radiusCircle);
                        // проверка пересечения
                        if (pair != null) {
                            if (draw1) {
                                points.add(pair.first);
                                boolean difX = Math.abs(pair.first.getX() - touchX1Line) <= 45.0;
                                boolean difY = Math.abs(pair.first.getY() - touchY1Line) <= 45.0;
                                boolean difX2 = Math.abs(pair.first.getX() - touchX2Line) <= 45.0;
                                boolean difY2 = Math.abs(pair.first.getY() - touchY1Line) <= 45.0;
                                if (difX && difY) {
                                    touchX1Line = pair.first.getX();
                                    touchY1Line = pair.first.getY();
                                    wasInaccuracyLine1 = false;
                                } else if (difX2 && difY2) {
                                    touchX2Line = pair.first.getX();
                                    touchY2Line = pair.first.getY();
                                    wasInaccuracyLine2 = false;
                                }
                            }
                            if (!pair.first.equals(pair.second)) {
                                if (draw1) {
                                    points.add(pair.second);
                                    points.add(pair.first);
                                    boolean difX3 = Math.abs(pair.second.getX() - touchX1Line) <= 45.0;
                                    boolean difY3 = Math.abs(pair.second.getY() - touchY1Line) <= 45.0;
                                    boolean difX4 = Math.abs(pair.second.getX() - touchX2Line) <= 45.0;
                                    boolean difY4 = Math.abs(pair.second.getY() - touchY1Line) <= 45.0;
                                    if (difX3 && difY3) {
                                        touchX1Line = pair.second.getX();
                                        touchY1Line = pair.second.getY();
                                        wasInaccuracyLine1 = false;
                                    } else if (difX4 && difY4) {
                                        touchX2Line = pair.second.getX();
                                        touchY2Line = pair.second.getY();
                                        wasInaccuracyLine2 = false;
                                    }
                                }
                            }
                        }

                        // пересечение окружности с прямой
                        Pair<Point, Point> pairC = Intersection.intersectionLineWithCircle(
                                new Point(touchX1Line, touchY1Line), new Point(touchX2Line, touchY2Line),
                                pastTouch, e[2]);
                        // проверка пересечения
                        if (pairC != null) {
                            if (draw1) {
                                points.add(pairC.first);
                            } else {
                                canvas.drawPoint(pairC.first.getX(), pairC.first.getY(), pointPaint);
                            }
                            if (pairC.second != null) {
                                if (draw1) {
                                    points.add(pairC.second);
                                } else {
                                    canvas.drawPoint(pairC.second.getX(), pairC.second.getY(), pointPaint);
                                }
                            }
                        }

                        // ПРИТЯЖЕНИЕ ЛИНИИ

                        // "притяжение" прямой с центром окружности (работает)
                        Point pointC = Inaccuracy.coincidence_of_point(pastTouch,
                                currentTouch1);
                        Point pointC2 = Inaccuracy.coincidence_of_point(pastTouch,
                                currentTouch2);
                        // проверка "притяжения"
                        if (pointC != null && wasInaccuracyLine1) {
                            touchX1Line = e[0];
                            touchY1Line = e[1];
                            wasInaccuracyLine1 = false;
                            canvas.drawCircle(touchX1Line, touchY1Line, 30, circlePaint);
                        }
                        if (pointC2 != null && wasInaccuracyLine2) {
                            touchX2Line = e[0];
                            touchY2Line = e[1];
                            wasInaccuracyLine2 = false;
                            canvas.drawCircle(touchX2Line, touchY2Line, 30, circlePaint);
                        }

                        // "притяжение" прямой к окружности
                        Pair<Point, Integer> coincidence = Inaccuracy.coincidence_of_point2(currentTouch1, currentTouch2,
                                pastTouch, e[2]);
                        // проверка "притяжения"
                        if (coincidence != null) {
                            //Log.d("WasTouchLine", "был");
/*                            Log.d("InaccuracyLineAndCircle", "Pair:\n" + Point.toString(coincidence.first) + " " + coincidence.second + "\n" +
                                    "Touch:\n" + touchX1Line + " " + touchY1Line + " " + touchX2Line + " " +touchY2Line + "\n" +
                                    "Inaccuracy\n" + wasInaccuracyLine1 + " " + wasInaccuracyLine2 + "Circle:\n" +
                                    Point.toString(pastTouch) + " " + radiusCircle);*/
                            if (coincidence.second == 1 && wasInaccuracyLine1) {
                                Log.d("WasTouchLine", "1был");
                                touchX1Line = coincidence.first.getX();
                                touchY1Line = coincidence.first.getY();
                                canvas.drawCircle(touchX1Line, touchY1Line, 30, circlePaint);
                            } else if (coincidence.second == 2 && wasInaccuracyLine2) {
                                Log.d("WasTouchLine", "2был");
                                touchX2Line = coincidence.first.getX();
                                touchY2Line = coincidence.first.getY();
                                canvas.drawCircle(touchX2Line, touchY2Line, 30, circlePaint);
                            }
                        }
                    }

                    for (Point e : points) {

                        pointPaint.setColor(getResources().getColor(R.color.points));
                        pointPaint.setStrokeWidth(10);
                        canvas.drawPoint(e.getX(), e.getY(), pointPaint);
                    }

                    if (lines.size() + circles.size() >= 10) {
                        countFigure = true;
                    }
                    else {
                        countFigure = false;
                        if (ActivityTabTwo.flag == 1) {
                            if (draw1) {
                                Log.i("COUNTFIGURE line", countFigure + " " + lines.size() + " " + circles.size());
                                whatIsFigure.add(1);
                                circles.add(new float[]{touchX1Circle, touchY1Circle, radiusCircle});
                                Log.d("NewList", circles.size() + "");
                                draw1 = false;
                                canMakeToast = true;
                            }
                        }
                        else if (ActivityTabTwo.flag == 0 && draw) {
                            Log.i("COUNTFIGURE circle", countFigure + " " + lines.size() + " " + circles.size());
                            whatIsFigure.add(0);
                            lines.add(new float[]{touchX1Line, touchY1Line, touchX2Line, touchY2Line});
                            float temp = (float) Math.sqrt(Math.pow(touchX1Line - touchX2Line, 2) + Math.pow(touchY1Line - touchY2Line, 2));
                            lengthLine.add(temp);
                            canMakeToast = true;
/*                        Log.d("NewList", lines.size() + "");
                        Log.d("CoordinatsOfLine", touchX1Line + " " + touchY1Line + " " + touchX2Line + " " + touchY2Line);
                        String a = "";
                        for (float e : lengthLine) {
                            a += e + " ";
                        }
                        Log.d("ArrayListlengthLine", a);
                        a = "";*/
                            draw = false;

                        }
                    }

                    getHolder().unlockCanvasAndPost(canvas);
                    Log.i("COUNTFIGURE 3333", countFigure+" " + lines.size() + " " + circles.size());
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}