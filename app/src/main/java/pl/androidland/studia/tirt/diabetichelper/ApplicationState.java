package pl.androidland.studia.tirt.diabetichelper;

import pl.androidland.studia.tirt.diabetichelper.database.models.User;

public class ApplicationState {
    private User loggedInUser;


    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setLoggedOut() {
        loggedInUser = null;
    }

    public boolean isLoggedUser() {
        return loggedInUser != null;
    }
}
