package com.example.school;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;


public class SignupTabFragment extends Fragment {
     FirebaseAuth auth;
     EditText first_name,family_name,nemail,password,confirme;
     Button button;
     TextView loginRedirectText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment_signup_tab, container, false);


    return rootView;}
}