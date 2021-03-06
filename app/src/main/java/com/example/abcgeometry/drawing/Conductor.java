package com.example.abcgeometry.drawing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.abcgeometry.R;

public class Conductor extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mySetting";
    public static final String APP_PREFERENCES_COLOR1 = "colorButton1";
    public static final String APP_PREFERENCES_COLOR2 = "colorButton2";
    public static final String APP_PREFERENCES_COLOR3 = "colorButton3";
    public static final String APP_PREFERENCES_COLOR4 = "colorButton4";
    public static final String APP_PREFERENCES_COLOR5 = "colorButton5";

    private Button conductor1, conductor2, conductor3, conductor4, conductor5;
    private boolean isGreen1 = false, isGreen2 = false, isGreen3 = false, isGreen4 = false, isGreen5 = false;
    static int buttonFlag = -1;
    SharedPreferences mSettings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        isGreen1 = mSettings.getBoolean(APP_PREFERENCES_COLOR1, false);
        isGreen2 = mSettings.getBoolean(APP_PREFERENCES_COLOR2, false);
        isGreen3 = mSettings.getBoolean(APP_PREFERENCES_COLOR3, false);
        isGreen4 = mSettings.getBoolean(APP_PREFERENCES_COLOR4, false);
        isGreen5 = mSettings.getBoolean(APP_PREFERENCES_COLOR5, false);

        if (TabTwo.numberConductor == 1) {
            setContentView(R.layout.tutorial_tab_two);
            conductor1 = (Button) findViewById(R.id.conductor1);
            conductor2 = (Button) findViewById(R.id.conductor2);
            if (isGreen1) {
                conductor1.setBackgroundResource(R.color.green);
            }
            if (isGreen2) {
                conductor2.setBackgroundResource(R.color.green);
            }
            conductor1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isGreen1) {
                        conductor1.setBackgroundResource(R.color.red);
                    }
                    buttonFlag = 1;
                    Intent intent = new Intent(getApplicationContext(), ActivityTabTwo.class);
                    startActivity(intent);
                }
            });
            conductor2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isGreen2) {
                        conductor2.setBackgroundResource(R.color.red);
                    }
                    buttonFlag = 2;
                    Intent intent = new Intent(getApplicationContext(), ActivityTabTwo.class);
                    startActivity(intent);
                }
            });

        }
        else if (TabTwo.numberConductor == 2) {
            setContentView(R.layout.base_tab_two);
            conductor3 = (Button) findViewById(R.id.conductor3);
            conductor4 = (Button) findViewById(R.id.conductor4);
            conductor5 = (Button) findViewById(R.id.conductor5);
            if (isGreen3) {
                conductor3.setBackgroundResource(R.color.green);
            }
            if (isGreen4) {
                conductor4.setBackgroundResource(R.color.green);
            }
            if (isGreen5) {
                conductor5.setBackgroundResource(R.color.green);
            }
            conductor3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isGreen3) {
                        conductor3.setBackgroundResource(R.color.red);
                    }
                    buttonFlag = 3;
                    Intent intent = new Intent(getApplicationContext(), ActivityTabTwo.class);
                    startActivity(intent);
                }
            });
            conductor4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isGreen4) {
                        conductor4.setBackgroundResource(R.color.red);
                    }
                    buttonFlag = 4;
                    Intent intent = new Intent(getApplicationContext(), ActivityTabTwo.class);
                    startActivity(intent);
                }
            });
            conductor5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isGreen5) {
                        conductor5.setBackgroundResource(R.color.red);
                    }
                    buttonFlag = 5;
                    Intent intent = new Intent(getApplicationContext(), ActivityTabTwo.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();

        if (ActivityTabTwo.colorFlag1 && TabTwo.numberConductor == 1) {
            conductor1.setBackgroundResource(R.color.green);
            isGreen1 = true;
            editor.putBoolean(APP_PREFERENCES_COLOR1, true);
        }
        if (ActivityTabTwo.colorFlag2 && TabTwo.numberConductor == 1) {
            conductor2.setBackgroundResource(R.color.green);
            isGreen2 = true;
            editor.putBoolean(APP_PREFERENCES_COLOR2, true);
        }
        if (ActivityTabTwo.colorFlag3 && TabTwo.numberConductor == 2) {
            conductor3.setBackgroundResource(R.color.green);
            isGreen3 = true;
            editor.putBoolean(APP_PREFERENCES_COLOR3, true);
        }
        if (ActivityTabTwo.colorFlag4 && TabTwo.numberConductor == 2) {
            conductor4.setBackgroundResource(R.color.green);
            isGreen4 = true;
            editor.putBoolean(APP_PREFERENCES_COLOR4, true);
        }
        if (ActivityTabTwo.colorFlag5 && TabTwo.numberConductor == 2) {
            conductor5.setBackgroundResource(R.color.green);
            isGreen5 = true;
            editor.putBoolean(APP_PREFERENCES_COLOR5, true);
        }
        editor.apply();
    }
}
