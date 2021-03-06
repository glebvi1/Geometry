package com.example.abcgeometry.domain;

import android.util.Pair;

public class Intersection {

    // O(1)
    public static Point intersectionLineWithLine(Point A, Point B, Point C, Point D) {

        float a1 = B.getY() - A.getY();
        float b1 = A.getX() - B.getX();
        float c1 = a1*(A.getX()) + b1*(A.getY());

        float a2 = D.getY() - C.getY();
        float b2 = C.getX() - D.getX();
        float c2 = a2*(C.getX())+ b2*(C.getY());

        float determinant = a1*b2 - a2*b1;

        if (determinant == 0) { // совподают или параллельны
            return null;
        } else {
            float x = (b2*c1 - b1*c2)/determinant;
            float y = (a1*c2 - a2*c1)/determinant;
            return new Point(x, y);
        }
    }

    // O(1)
    public static Pair<Point, Point> intersectionCircleWithCircle(Point center1, float radius1, Point center2, float radius2) {

        float distance = (float) Math.sqrt(Math.pow(center1.getX() - center2.getX(), 2) +
                Math.pow(center1.getY() - center2.getY(), 2)); // дистанция между центрами
        float sumRadius = radius1 + radius2;

        if (distance > sumRadius || distance < Math.abs(radius1 - radius2) ||
        distance == 0 && radius1 == radius2) { // случаи, в которых нет пересечения
            return null;
        } else { // есть пересечение
            float a, h, x1, y1, x2, y2, tempX, tempY;

            a = (radius1*radius1-radius2*radius2+distance*distance) / (2*distance);
            h = (float) Math.sqrt(radius1*radius1-a*a);

            // P3
            tempX = center1.getX() + a * (center2.getX() - center1.getX()) / distance;
            tempY = center1.getY() + a * (center2.getY() - center1.getY()) / distance;

            x1 = tempX + h * (center2.getY() - center1.getY()) / distance;
            y1 = tempY - h * (center2.getX() - center1.getX()) / distance;

            x2 = tempX - h * (center2.getY() - center1.getY()) / distance;
            y2 = tempY + h * (center2.getX() - center1.getX()) / distance;
            return new Pair<>(new Point(x1, y1), new Point(x2, y2));
        }
    }

    // O(1)
    public static Pair<Point, Point> intersectionLineWithCircle(Point A, Point B, Point center, float radius) {

        float baX = B.getX() - A.getX();
        float baY = B.getY() - A.getY();
        float caX = center.getX() - A.getX();
        float caY = center.getY() - A.getY();

        float a = baX * baX + baY * baY;
        float bBy2 = baX * caX + baY * caY;
        float c = caX * caX + caY * caY - radius * radius;

        float pBy2 = bBy2 / a;
        float q = c / a;

        float disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return null;
        }

        float tmpSqrt = (float) Math.sqrt(disc);
        float abScalingFactor1 = -pBy2 + tmpSqrt;
        float abScalingFactor2 = -pBy2 - tmpSqrt;

        Point p1 = new Point(A.getX() - baX * abScalingFactor1, A.getY()
                - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return new Pair<>(p1, null);
        }
        Point p2 = new Point(A.getX() - baX * abScalingFactor2, A.getY()
                - baY * abScalingFactor2);
        return new Pair<>(p1, p2);
    }

}
