package com.example.abcgeometry.additional;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.example.abcgeometry.R;

public class TabOne extends ListFragment {

    //private GeometryAlertDialog informationDialog;
/*    protected static LinkedList<String> topic = new LinkedList<>();
    protected static LinkedList<String> information_for_topic = new LinkedList<>();*/

    String information[] = new String[3];
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_one, container, false);
        //information = {"ABCGeometry - ...", "В программе есть...", "В стадии разработки нахидяться: ..."};

        information[0] = getString(R.string.about);
        information[1] = getString(R.string.rules);
        information[2] = getString(R.string.develop);
        String topic[] = {"О программе", "Как пользоваться программой?", "В стадии разработки"};
        @SuppressLint("ResourceType")
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.xml.information_about_programm, R.id.text, topic);
        setListAdapter(arrayAdapter);
        setRetainInstance(true);
        return root;
    }

   public void onListItemClick(ListView l, View v, int position, long id) {

        ViewGroup viewGroup = (ViewGroup) v;
        TextView textView = (TextView) viewGroup.findViewById(R.id.text);
        createAlertDialog(textView.getText().toString(), information[position]);

    }

    private void createAlertDialog(String topic, String information) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(topic);
        builder.setMessage(information);
        builder.setPositiveButton("Ясно",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.show();
    }

}
