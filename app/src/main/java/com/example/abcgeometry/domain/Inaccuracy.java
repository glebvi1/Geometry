package com.example.abcgeometry.domain;

import android.util.Pair;

public class Inaccuracy {

    private static final float EPS = 45;

    public static Point coincidence_of_point(Point pastPoint, Point currentPoint) {
        float difPointX, difPointY;

        difPointX = Math.abs(pastPoint.getX() - currentPoint.getX());
        difPointY = Math.abs(pastPoint.getY() - currentPoint.getY());
        if (difPointX <= EPS && difPointY <= EPS) {
            return pastPoint;
        }
        return null;
    }

    public static Pair<Point, Integer> coincidence_of_point2(Point A, Point B, Point circle, float radius) {

        Pair<Point, Point> pair = Intersection.intersectionLineWithCircle(A, B, circle, radius);
        if (pair != null) {
            float difPointX1 = Math.abs(pair.first.getX() - A.getX());
            float difPointY1 = Math.abs(pair.first.getY() - A.getY());
            float difPointX2 = Math.abs(pair.first.getX() - B.getX());
            float difPointY2 = Math.abs(pair.first.getY() - B.getY());

            if (difPointX1 <= EPS && difPointY1 <= EPS) {
                return new Pair<Point, Integer>(pair.first, 1); // номер
            } else if (difPointX2 <= EPS && difPointY2 <= EPS) {
                return new Pair<Point, Integer>(pair.first, 2); // todo: return точка на окружности!!!

            } else if (pair.second != null) {
                float difPointX11 = Math.abs(pair.second.getX() - A.getX());
                float difPointY11 = Math.abs(pair.second.getY() - A.getY());
                float difPointX12 = Math.abs(pair.second.getX() - B.getX());
                float difPointY12 = Math.abs(pair.second.getY() - B.getY());

                if (difPointX11 <= EPS && difPointY11 <= EPS) {
                    return new Pair<Point, Integer>(pair.second, 1); // todo: return точка на окружности!!!
                } else if (difPointX12 <= EPS && difPointY12 <= EPS) {
                    return new Pair<Point, Integer>(pair.second, 2); // todo: return точка на окружности!!!
                }
            }
        }
        return null;
    }

/*    static boolean coincidence_of_point3(Point c1, float r1, Point c2, float r2, Point lastTouch) {
        float difX = Math.abs(c1.x - lastTouch.x);
        float difY = Math.abs(c1.y - lastTouch.y);
        float distX = Math.abs(c1.x - c2.x);
        float distY = Math.abs(c1.y - c2.y);
        if (difX <= EPS && difY <= EPS && distX <= EPS && distY <= EPS) {
            return true;
        } else {
            return false;
        }
    }*/

}
