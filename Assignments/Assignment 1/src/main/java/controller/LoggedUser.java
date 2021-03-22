package controller;

import model.User;

public class LoggedUser {

    private static User loggedUser;

    public static void logUser(User user) {
        loggedUser = user;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }
}
