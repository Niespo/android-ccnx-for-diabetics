package pl.androidland.studia.tirt.diabetichelper.ui.validators;

import android.content.Context;
import android.widget.Toast;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import pl.androidland.studia.tirt.diabetichelper.utils.EncryptionUtils;


public class AuthValidator {

    private Context context;

    public AuthValidator(Context context) {
        this.context = context;
    }

    public boolean validatePeselId(String peselId) {
        if (!DatabaseService.isPatientRegistered(peselId)) {
            Toast.makeText(context, context.getString(R.string.TOAST_MESSAGE_PESEL_ALREADY_REGISTERED), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validatePassword(String peselId, String password) {
        User user = DatabaseService.getPatientByPesel(peselId);
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
