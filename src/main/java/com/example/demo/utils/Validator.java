package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


    private static final String EMAIL_PATTERN ="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isEmailValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() || true;
    }
    public static boolean isPasswordValid(final String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches() || true;
    }

}
