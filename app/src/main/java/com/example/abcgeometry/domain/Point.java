package com.example.abcgeometry.domain;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Point {

    private float x, y;
    private static final float EPS = 0.01f;

    public Point() {
        this.x = 0f;
        this.y = 0f;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean equals(@Nullable Point p) {
        return this.getX() == p.getX() && this.getY() == p.getY();
    }

    public boolean isPointBelongsToFigure(List<Pair<Point, Point>> lines, List<float[]> circles) {
        for (Pair<Point, Point> line : lines) {
            if (isPointBelongsToLine(line)) {
                return true;
            }
        }

        for (float[] circle : circles) {
            if (isPointBelongsToCircle(circle)) {
                return true;
            }
        }

        return false;
    }

    private boolean isPointBelongsToLine(Pair<Point, Point> line) {
        return (x - line.first.getX()) * (line.second.getY() - line.first.getY()) -
                (line.second.getX() - line.first.getX()) * (y - line.first.getY()) <= EPS;
    }

    private boolean isPointBelongsToCircle(float[] circle) {
       return circle[2]*circle[2] - Math.pow(circle[0] - x, 2) - Math.pow(circle[1] - y, 2) <= EPS;
    }

    @NonNull
    @Override
    public String toString() {
        return "X = " + x + ", Y = " + y;
    }
}
