package com.shangwa.auth.lib;

import java.util.regex.Pattern;

// import ch.qos.logback.core.boolex.Matcher;

/** Validation utility */
public class Validator {
    
    /** Returns true if an email is a valid password*/
    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return Pattern.compile(emailRegex)
        .matcher(email)
        .matches();
    }

}
