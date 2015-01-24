package pl.androidland.studia.tirt.diabetichelper.auth;

import android.content.Context;
import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import pl.androidland.studia.tirt.diabetichelper.ui.validators.AuthValidator;


public class AuthService {

    private final static ApplicationState state = ApplicationBus.getState();
    private final Context context;
    private User user;
    private final AuthValidator validator;

    public AuthService(Context context) {
        this.context = context;
        validator = new AuthValidator(context);
    }

    public boolean login(String peselId, String password) {
        user = null;
        if (!validator.validatePeselId(peselId) || !validator.validatePassword(peselId, password))
            return false;

        User user = DatabaseService.getPatientByPesel(peselId);
        state.setLoggedInUser(user);
        return true;
    }

}
