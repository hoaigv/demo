package com.example.bookshop.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {


    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public String getUsername() {
        return getAuthentication().getName();
    }

    public static String getUserCurrent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() ) {
            return authentication.getName();
        }


        return null;
    }


}
