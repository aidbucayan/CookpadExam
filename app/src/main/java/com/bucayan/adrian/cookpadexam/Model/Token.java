package com.bucayan.adrian.cookpadexam.Model;

/**
 * @author Adrian Bucayan on 12/15/2016.
 */
public class Token {

    private String access_token;
    private User user;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
