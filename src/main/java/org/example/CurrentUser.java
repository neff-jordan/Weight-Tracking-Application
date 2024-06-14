/*
*
* Author: Jordan Neff
* Purpose: Keep track of the current logged-in user
*
*/


package org.example;

public class CurrentUser {

    // figure out how this differs from my solution and why it is better

    private static CurrentUser instance;
    private String loggedInUser;

    private CurrentUser(String user) {
        this.loggedInUser = user;
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CurrentUser is not set. Login required.");
        }
        return instance;
    }

    public static void setInstance(String user) {
        instance = new CurrentUser(user);
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String user) {
        this.loggedInUser = user;
    }
}
