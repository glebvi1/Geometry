package com.example.abcgeometry.assigment;

import android.util.Log;

import com.example.abcgeometry.domain.Intersection;
import com.example.abcgeometry.drawing.TouchEvent;

public class Exercise {

    private static final float EPS = 0.01f;
    private static final float EPS2 = 15;

    public static String setConditional(int number) {
        switch (number) {
            case 1 : return "Нарисуйте прямую линию. Для этого на синем фоне нажмите два раза в двух разных местах.";
            case 2 : return "Нарисуйте окружность. Для этого на синем фоне нажмите и неотрывая пальца, ведите по экрану.";
            case 4 : return "Постройте равнобедренный треугольник.";
            case 3 : return "Постройте отрезок данной длины.";
            case 5 : return "Постройте равносторонний треугольник. Можно использовать только 3 линии.";
            default: return "";
        }
    }

    public static boolean setChecked(int number) {
        switch (number) {

            case 1 :
                if (TouchEvent.lines.size() != 0) {
                    return true;
                } return false;

            case 2 :
                if (TouchEvent.circles.size() != 0) {
                    return true;
                } return false;

            case 4 :
                for (int i = 0; i < TouchEvent.lengthLine.size(); i++) {
                    for (int j = i+1; j < TouchEvent.lengthLine.size(); j++) {
                        float eps = Math.abs(TouchEvent.lengthLine.get(i) - TouchEvent.lengthLine.get(j));
                        //Log.d("123654321", TouchEvent.lengthLine.get(0) + " " +TouchEvent.lengthLine.get(1) + " " +TouchEvent.lengthLine.get(2) + " " + temp);
                        if (eps <= EPS && TouchEvent.circles.size() != 0 &&
                        Intersection.intersectionLineWithLine(TouchEvent.lines.get(i)[0],
                                TouchEvent.lines.get(i)[1],
                                TouchEvent.lines.get(j)[0],
                                TouchEvent.lines.get(j)[1]) != null
                        && TouchEvent.lines.size() >= 3) {
                            return true;
                        }

                    }
                }

                 return false;

            case 3 :
                //boolean isRight = false;
                float eps = 0;
                for (int i = 0; i < TouchEvent.lengthLine.size(); i++) {
                    for (int j = i+1; j < TouchEvent.lengthLine.size(); j++) {
                        eps = Math.abs(TouchEvent.lengthLine.get(i) - TouchEvent.lengthLine.get(j));
                        Log.d("ArrayListlengthLine", eps + " " + TouchEvent.circles.size());
                        if (eps <= EPS && TouchEvent.circles.size() != 0) {
                            return true;
                        }

                    }
                }
                Log.d("ArrayListlengthLine", TouchEvent.circles.size()+" " + eps);
                return false;
            case 5:
                if (TouchEvent.lines.size() == 3) {
                    float a, b, c;
                    a = TouchEvent.lengthLine.get(0);
                    b = TouchEvent.lengthLine.get(1);
                    c = TouchEvent.lengthLine.get(2);
                    Log.d("qwertyuiop[]", a + " " + b + " " + c);
                    if (Math.abs(a-b) <= EPS2 && Math.abs(a-c) <= EPS2 && Math.abs(b-c) <= EPS2) {
                        return true;
                    }
                }
                return false;

            default: return false;
        }
    }
}
