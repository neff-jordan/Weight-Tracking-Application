/**
 * The CurrentUser class uses the Singleton pattern to manage and store
 * information about the current logged-in user. This ensures that only one
 * instance of a logged-in user exists at any given time.
 */

package org.example;

public class CurrentUser {

    // single instance of CurrentUser
    private static CurrentUser instance;
    private String loggedInUser;

    /**
     * Private constructor to prevent direct instantiation (singleton pattern).
     * Ensures that the class can only be instantiated through setInstance().
     *
     * @param user The username of the logged-in user.
     */
    private CurrentUser(String user) {
        this.loggedInUser = user;
    }

    /**
     * Returns the single instance of CurrentUser. If no user is currently set,
     * it throws an IllegalStateException.
     *
     * @return The current instance of CurrentUser.
     * @throws IllegalStateException if no user is currently logged in.
     */
    public static CurrentUser getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CurrentUser is not set. Login required.");
        }
        return instance;
    }

    /**
     * Sets the instance of CurrentUser with the provided username.
     * If a user is already logged in, this will overwrite the previous instance.
     *
     * @param user The username to set as the current logged-in user.
     */
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