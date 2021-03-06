package com.example.abcgeometry.drawing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.abcgeometry.R;

public class TabTwo extends Fragment {

    // startActivity(new Intent(getActivity(), NewActivity.class));

    private Button tutorial, radius;
    static int numberConductor = -1;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.conductor, container, false);

        tutorial = (Button) root.findViewById(R.id.tutorial);
        radius = (Button) root.findViewById(R.id.radius);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberConductor = 1;
                startActivity(new Intent(getActivity(), Conductor.class));

            }
        });
        radius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberConductor = 2;
                startActivity(new Intent(getActivity(), Conductor.class));

            }
        });
        return root;
    }
}