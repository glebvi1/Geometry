package com.example.abcgeometry.drawing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.abcgeometry.R;
import com.example.abcgeometry.assigment.Exercise;
import com.example.abcgeometry.domain.Point;

import java.util.ArrayList;
import java.util.HashSet;

public class ActivityTabTwo extends AppCompatActivity {

    public static int flag = -1;
    public static boolean colorFlag1 = false, colorFlag2 = false, colorFlag3 = false, colorFlag4 = false, colorFlag5 = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Conductor.buttonFlag == 1) {
            clearAll(true);
            flag = -1;

            setContentView(R.layout.field_for_redactor);

            final Button conductor1 = (Button) findViewById(R.id.conductor1);

            createAlertDialog("Условие к задаче №1", Exercise.setConditional(1), 0);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.redactor, DrawFragment.newInstance(1))
                    .commitNow();

            RadioGroup radio = (RadioGroup) findViewById(R.id.radio);
            Button delAll = (Button) findViewById(R.id.delAll);
            final Button cancel = (Button) findViewById(R.id.cancel);
            Button condition = (Button) findViewById(R.id.condition);
            Button checked = (Button) findViewById(R.id.checked);

            delAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearAll(false);
                }
            });

            radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Line:
                            flag = 0;
                            break;
                        case R.id.Circle:
                            flag = 1;
                            break;
                    }
                }
            });

            condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAlertDialog("Условие к задаче №1", Exercise.setConditional(1), 0);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });

            checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Exercise.setChecked(1)) {
                        createAlertDialog("Вы решили задачу №1", "Ура!", 1);
                        colorFlag1 = true;
                    }
                    else {
                        createAlertDialog("Неправильно", "В следующий раз все получится!", 2);
                    }

                }
            });

        }
        else if (Conductor.buttonFlag == 2) {
            clearAll(true);
            flag = -1;

            createAlertDialog("Условие к задаче №2", Exercise.setConditional(2), 0);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.redactor, DrawFragment.newInstance(1))
                    .commitNow();

            setContentView(R.layout.field_for_redactor);

            RadioGroup radio = (RadioGroup) findViewById(R.id.radio);
            final Button cancel = (Button) findViewById(R.id.cancel);
            Button condition = (Button) findViewById(R.id.condition);
            Button checked = (Button) findViewById(R.id.checked);
            Button delAll = (Button) findViewById(R.id.delAll);

            delAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearAll(false);
                }
            });

            radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Line:
                            flag = 0;
                            break;
                        case R.id.Circle:
                            flag = 1;
                            break;
                    }
                }
            });


            condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAlertDialog("Условие к задаче №2", Exercise.setConditional(2), 0);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });

            checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Exercise.setChecked(2)) {
                        createAlertDialog("Вы решили задачу №2!", "Ура!", 1);
                        colorFlag2 = true;
                    } else {
                        createAlertDialog("Неправильно", "В следующий раз все получится!", 2);
                    }

                }
            });
        }
        else if (Conductor.buttonFlag == 3) {
            clearAll(true);
            flag = -1;

            createAlertDialog("Условие к задаче №3", Exercise.setConditional(3), 0);

            TouchEvent.constantLine.add(new float[]{400, 385, 400, 755});
            TouchEvent.lines.add(new Pair<Point, Point>(new Point(400, 385), new Point(400, 755)));
            TouchEvent.lengthLine.add((float)Math.sqrt(Math.pow(385 - 755, 2)));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.redactor, DrawFragment.newInstance(1))
                    .commitNow();

            setContentView(R.layout.field_for_redactor);

            RadioGroup radio = (RadioGroup) findViewById(R.id.radio);
            final Button cancel = (Button) findViewById(R.id.cancel);
            Button condition = (Button) findViewById(R.id.condition);
            Button checked = (Button) findViewById(R.id.checked);
            Button delAll = (Button) findViewById(R.id.delAll);

            delAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearAll(false);
                    TouchEvent.lines.add(new Pair<Point, Point>(new Point(400, 385), new Point(400, 755)));
                    TouchEvent.lengthLine.add((float)Math.sqrt(Math.pow(385 - 755, 2)));
                }
            });


            radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Line:
                            flag = 0;
                            break;
                        case R.id.Circle:
                            flag = 1;
                            break;
                    }
                }
            });


            condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    createAlertDialog("Условие к задаче №3", Exercise.setConditional(3), 0);

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });

            checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Exercise.setChecked(3)) {
                        createAlertDialog("Вы решили задачу №3!", "Ура!", 1);
                        colorFlag3 = true;
                    }
                    else {
                        createAlertDialog("Неправильно", "В следующий раз все получится!", 2);
                    }

                }
            });
        }
        else if (Conductor.buttonFlag == 4) {
            clearAll(true);
            flag = -1;

            setContentView(R.layout.field_for_redactor);

            createAlertDialog("Условие к задаче №4", Exercise.setConditional(4), 0);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.redactor, DrawFragment.newInstance(1))
                    .commitNow();

            RadioGroup radio = (RadioGroup) findViewById(R.id.radio);
            Button delAll = (Button) findViewById(R.id.delAll);
            final Button cancel = (Button) findViewById(R.id.cancel);
            Button condition = (Button) findViewById(R.id.condition);
            Button checked = (Button) findViewById(R.id.checked);

            delAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearAll(false);
                }
            });

            radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Line:
                            flag = 0;
                            break;
                        case R.id.Circle:
                            flag = 1;
                            break;
                    }
                }
            });

            condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAlertDialog("Условие к задаче №4", Exercise.setConditional(4), 0);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });

            checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Exercise.setChecked(4)) {
                        createAlertDialog("Вы решили задачу №4", "Ура!", 1);
                        colorFlag4 = true;
                    } else {
                        createAlertDialog("Неправильно", "В следующий раз все получится!", 2);
                    }
                }
            });

        }
        else if (Conductor.buttonFlag == 5) {
            clearAll(true);
            flag = -1;

            setContentView(R.layout.field_for_redactor);

            createAlertDialog("Условие к задаче №5", Exercise.setConditional(5), 0);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.redactor, DrawFragment.newInstance(1))
                    .commitNow();

            RadioGroup radio = (RadioGroup) findViewById(R.id.radio);
            Button delAll = (Button) findViewById(R.id.delAll);
            final Button cancel = (Button) findViewById(R.id.cancel);
            Button condition = (Button) findViewById(R.id.condition);
            Button checked = (Button) findViewById(R.id.checked);

            delAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearAll(false);
                }
            });

            radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Line:
                            flag = 0;
                            break;
                        case R.id.Circle:
                            flag = 1;
                            break;
                    }
                }
            });

            condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAlertDialog("Условие к задаче №5", Exercise.setConditional(5), 0);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });

            checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Exercise.setChecked(5)) {
                        createAlertDialog("Вы решили задачу №5", "Ура!", 1);
                        colorFlag5 = true;
                    } else {
                        createAlertDialog("Неправильно", "В следующий раз все получится!", 2);
                    }
                }
            });

        }
    }

    private void createAlertDialog(String title, String condition, int number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTabTwo.this);

        builder.setTitle(title);
        builder.setMessage(condition);

        if (number == 0) {
            builder.setPositiveButton("Ясно",

                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {

                        }
                    });
        } else if (number == 1) {
            builder.setPositiveButton("Отлично!",

                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {

                        }
                    });
        } else if (number == 2) {
            builder.setPositiveButton("Попробую еще раз!",

                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {

                        }
                    });
        }

        builder.show();
    }

    private void clearAll(Boolean isConst) {
        TouchEvent.pointsLine = new HashSet<>();
        TouchEvent.pointsCircle = new HashSet<>();
        TouchEvent.whatIsFigure = new ArrayList<>();
        TouchEvent.lengthLine = new ArrayList<>();
        TouchEvent.circles = new ArrayList<>();
        TouchEvent.lines = new ArrayList<>();
        if (isConst) {
            TouchEvent.constantLine = new ArrayList<>();
        }
        TouchEvent.touchX1Circle = -1000;
        TouchEvent.touchY1Circle = -1000;
        TouchEvent.touchX2Circle = -1000;
        TouchEvent.touchY2Circle = -1000;
        TouchEvent.radiusCircle = -1000;
        TouchEvent.indexOfPrevisionLine = 0;
    }

    private void cancel() {
        int m = TouchEvent.whatIsFigure.size();
        if (m != 0) {
            if (TouchEvent.whatIsFigure.get(m - 1) == 0) {
                int n = TouchEvent.lines.size();
                TouchEvent.lines.remove(n - 1);
                TouchEvent.whatIsFigure.remove(m - 1);
                TouchEvent.indexOfPrevisionLine--;
                TouchEvent.isNewPointLine = true;
            } else if (TouchEvent.whatIsFigure.get(m - 1) == 1) {
                int n = TouchEvent.circles.size();
                TouchEvent.touchX1Circle = -1000;
                TouchEvent.touchY1Circle = -1000;
                TouchEvent.touchX2Circle = -1000;
                TouchEvent.touchY2Circle = -1000;
                TouchEvent.radiusCircle = -1000;
                TouchEvent.circles.remove(n - 1);
                TouchEvent.whatIsFigure.remove(m - 1);
                TouchEvent.isNewPointCircle = true;
            }
        }

    }
}