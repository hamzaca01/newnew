package com.example.school.helper;

public class StringHelper {
    //regular Expression patteern for email;
    public static boolean regexEmailValidationPatern(String email){
        //pattern
        String regex="([a-zA-z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})";
        if(email.matches(regex)){
            return true;
        }
        return false;
    }
}
