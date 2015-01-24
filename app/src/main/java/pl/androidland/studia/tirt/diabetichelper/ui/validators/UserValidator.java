package pl.androidland.studia.tirt.diabetichelper.ui.validators;

import android.widget.Toast;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import pl.androidland.studia.tirt.diabetichelper.ui.android.components.activities.UserActivity;

import java.util.regex.Pattern;


public class UserValidator {
    private static final Pattern DECIMAL_NUMBER_PATERN = Pattern.compile("\\d+\\.\\d+");
    private UserActivity callback;

    public UserValidator(UserActivity callback) {
        this.callback = callback;
    }

    public boolean validatePatient(String pesel) {
        if (!DatabaseService.isPatientRegistered(pesel)) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_PATIENT_DOES_NOT_EXIST),
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    public boolean validateMeasurement(String measurement) {
        if (!DECIMAL_NUMBER_PATERN.matcher(measurement).matches()) {
            Toast.makeText(callback,
                    callback.getString(R.string.TOAST_MESSAGE_UNSUPPORTED_MEASUREMENT_FORMAT),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}
