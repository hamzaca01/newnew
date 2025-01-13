package com.example.school.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.school.R;
import com.example.school.helper.StringHelper;

import java.util.HashMap;
import java.util.Map;


public class AdministrationActivity extends Fragment {
    EditText first_name,family_name,email,identifiant,phone,password,confirm;
    RadioButton teacher,student;
    Button sign_up_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_administration, container, false);

        first_name = rootView.findViewById(R.id.firstName);
        family_name = rootView.findViewById(R.id.familyName);
        email = rootView.findViewById(R.id.email);
        identifiant =rootView.findViewById(R.id.identifiant);
        phone = rootView.findViewById(R.id.phone);
        password = rootView.findViewById(R.id.password);
        confirm =rootView.findViewById(R.id.confirm);
        teacher = rootView.findViewById(R.id.radioTeachers);
        student = rootView.findViewById(R.id.radioStudents);
        sign_up_btn = rootView.findViewById(R.id.signup_button);
        // Add listeners to ensure only one is selected
        teacher.setOnClickListener(v -> {
            if (teacher.isChecked()) {
                student.setChecked(false);
            }
        });

        student.setOnClickListener(v -> {
            if (student.isChecked()) {
                teacher.setChecked(false);
            }
        });
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processFormFields();
            }
        });


        return rootView;
    }
    public void processFormFields(){
        // Check For Errors:
        if(!validateAll()||!validateFirstName() || !validateFamilyName() || !validateEmail() || !validateIdentifiant() || !validatePhone() || !validatePasswordAndConfirm() || !validateRadioButton() ){
            return;
        }
        Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_LONG).show();

    }
    public boolean validateAll(){
        String firstName = first_name.getText().toString();
        String familyName = family_name.getText().toString();
        String Email = email.getText().toString();
        String Identifiant = identifiant.getText().toString();
        String Phone = phone.getText().toString();
        String Password = password.getText().toString();
        String Confirm = confirm.getText().toString();
        if(firstName.isEmpty() && familyName.isEmpty() && Email.isEmpty() && Identifiant.isEmpty() && Phone.isEmpty()&& Password.isEmpty() && Confirm.isEmpty() &&!teacher.isChecked() && !student.isChecked()){
            Toast.makeText(getContext(),"All Field are empty",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true ;
        }
    }
    //first name validation
    public boolean validateFirstName(){
        String firstName = first_name.getText().toString();

        if(firstName.isEmpty()){
            first_name.setError("firstName cannot be empty !");
            return false;
        }
        else {
            first_name.setError(null );
            return true;
        }
    }
    //family name validation
    public boolean validateFamilyName(){
        String familyName = family_name.getText().toString();

        if(familyName.isEmpty()){
            family_name.setError("familyName cannot be empty !");
            return false;
        }
        else {
            family_name.setError(null );
            return true;
        }
    }
    // email validation
    public boolean validateEmail(){
        String Email = email.getText().toString();
        if(Email.isEmpty()){
            email.setError("email cannot be empty !");
            return false;
        } else if (!StringHelper.regexEmailValidationPatern(Email)) {
            email.setError("please enter a valid email!");
            return false;
        } else {
            email.setError(null );
            return true;
        }
    }
    // Identifiant validation
    public boolean validateIdentifiant() {
        String Identifiant = identifiant.getText().toString();

        if (Identifiant.isEmpty()) {
            identifiant.setError("Identifiant cannot be empty!");
            return false;
        } else {
            identifiant.setError(null);
            return true;
        }
    }

    // Phone validation
    public boolean validatePhone() {
        String Phone= phone.getText().toString();

        if (Phone.isEmpty()) {
            phone.setError("Phone number cannot be empty!");
            return false;
        } else if (!Phone.matches("\\d+")) { // Ensures phone number contains only digits
            phone.setError("Phone number must contain only digits!");
            return false;
        } else if (Phone.length() != 8) { // Example: Validating for 10-digit numbers
            phone.setError("Phone number must be 8 digits!");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }
    //password and confirm  validation
    public boolean validatePasswordAndConfirm(){
        String password_p = password.getText().toString();
        String confirm_p = confirm.getText().toString();

        // Check If Password and Confirm Field Is Empty:
        if(password_p.isEmpty()){
            password.setError("Password cannot be empty!");
            return false;
        }else if (!password_p.equals(confirm_p)){
            password.setError("Passwords do not match!");
            return false;
        }else if(confirm_p.isEmpty()){
            confirm.setError("Confirm field cannot be empty!");
            return false;
        }else{
            password.setError(null);
            confirm.setError(null);
            return true;
        }// Check Password and Confirm Field Is Empty.
    }
    public boolean validateRadioButton() {
        if (!teacher.isChecked() && !student.isChecked()) {
            Toast.makeText(getContext(), "Please select either Teacher or Student!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}