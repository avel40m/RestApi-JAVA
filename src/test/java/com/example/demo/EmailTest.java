package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailTest {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        String mail = "@gmail.com";
        Matcher matcher = pattern.matcher(mail);

        if (matcher.find() == true)
            System.out.println("Email validate");
        else
            System.out.println("Email not validate");
    }
}
