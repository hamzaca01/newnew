package com.example.school;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class LoginTabFragment extends Fragment {
EditText username;
EditText password;
Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment_tab, container, false);
        username=rootView.findViewById(R.id.username);
        password= rootView.findViewById(R.id.password);
        loginButton= rootView.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}