package com.example.abcgeometry.drawing;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DrawFragment extends Fragment {

    private static final String TAG = "DRAW";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return new TouchEvent(super.getContext());
        //return inflater.inflate( R.layout.life_fragment, container, false );
    }

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    //private OnListFragmentInteractionListener mListener;

    public DrawFragment() {
    }

    @SuppressWarnings("unused")
    public static DrawFragment newInstance(int columnCount) {
        Log.d(TAG, "NewInstance");
        DrawFragment fragment = new DrawFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
}


/*    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }}*/
