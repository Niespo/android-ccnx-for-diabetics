package pl.androidland.studia.tirt.diabetichelper.auth;

import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.utils.EncryptionUtils;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import android.content.Context;
import android.widget.Toast;


public class AuthService {

    private static ApplicationState state = ApplicationBus.getState();
    private Context context;
    private User user;

    public AuthService(Context context) {
        this.context = context;
    }

    public boolean login(String peselId, String password) {
        user = null;
        if (!validatePeselId(peselId) || !validatePassword(peselId, password))
            return false;
        state.setLoggedInUser(user);
        return true;
    }


    private boolean validatePeselId(String peselId) {
        if (!DatabaseService.isPatientRegistered(peselId)) {
            Toast.makeText(context, context.getString(R.string.TOAST_MESSAGE_PESEL_ALREADY_REGISTERED), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword(String peselId, String password) {
        user = DatabaseService.getPatientByPesel(peselId);
        if (user == null)
            return false;
        String encryptedPassword = EncryptionUtils.generateMd5Hash(password);
        String storedPassword = user.getPassword();
        if (!storedPassword.equals(encryptedPassword)) {
            Toast.makeText(context, context.getString(R.string.TOAST_MESSAGE_INCORRECT_PASSWORD), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
