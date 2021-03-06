package com.example.abcgeometry.additional;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.abcgeometry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TabZero extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText ETemail;
    private EditText ETpassword;
    public static boolean isRegistration = false;
    public static boolean isEnter = false;
    public static boolean isGuest = false;
    String MY_LOG = "Жизненый цикл фрагмента";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(MY_LOG, "onCreateView");
        View root = inflater.inflate(R.layout.login_activity, container, false);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                            .replace(R.id.container, new TabZero());
                    fragmentTransaction.commit();
                } else {
                    // User is signed out

                }
            }
        };

        ETemail = (EditText) root.findViewById(R.id.ed_email);
        ETpassword = (EditText) root.findViewById(R.id.ed_password);

        root.findViewById(R.id.b_sign_in).setOnClickListener(this);
        root.findViewById(R.id.b_registration).setOnClickListener(this);
        //startActivity(new Intent(getActivity(), TEMPActivity.class));
        return root;

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.b_sign_in) {
            if (ETemail.getText().toString().equals("") || ETpassword.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Заполните пустые поля!", Toast.LENGTH_SHORT).show();
            } else {
                signin(ETemail.getText().toString(), ETpassword.getText().toString());
            }
        }
        else if (view.getId() == R.id.b_registration) {
            if (ETemail.getText().toString().equals("") || ETpassword.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Заполните пустые поля!", Toast.LENGTH_SHORT).show();
            } else {
                registration(ETemail.getText().toString(), ETpassword.getText().toString());
            }
        }
    }

    public void signin(String email , String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    isEnter = true;
                    Toast.makeText(getActivity(), "Aвторизация успешна", Toast.LENGTH_SHORT).show();

                } else {
                    isEnter = false;
                    Toast.makeText(getActivity(), "Aвторизация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    isRegistration = true;
                    Toast.makeText(getActivity(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
                }
                else {
                    isRegistration = false;
                    Toast.makeText(getActivity(), "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
