package pl.androidland.studia.tirt.diabetichelper.ui.validators;

import android.widget.Toast;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import pl.androidland.studia.tirt.diabetichelper.ui.android.components.activities.RegisterActivity;
import pl.androidland.studia.tirt.diabetichelper.utils.DateUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterValidator {
    private final static Pattern PESEL_PATTERN = Pattern.compile("[0-9]{11}");

    private RegisterActivity callback;

    public RegisterValidator(RegisterActivity callback) {
        this.callback = callback;
    }

    public boolean validatePatientRegistration() {
        if (DatabaseService.isPatientRegistered(callback.getPesel())) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_USER_ALREADY_HAS_REGISTERED),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validateInputIsNotEmpty() {
        if (isFirstnameEmpty() || isSurenameEmpty() || isPeselEmpty() || isBirthDateEmpty()
                || isPasswordEmpty() || isPasswordAgainEmpty())
            return false;
        return true;
    }

    public boolean isPasswordAgainEmpty() {
        String passwordAgain = callback.getPassword();
        if (passwordAgain.matches("")) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_PASSWORD_AGAIN),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean isPasswordEmpty() {
        String password = callback.getPassword();

        if (password.matches("")) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_PASSWORD),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean isBirthDateEmpty() {
        String date = callback.getBirthDate();
        if (date.matches("")) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_BIRTH_DATE),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean isPeselEmpty() {
        String pesel = callback.getPesel();

        if (pesel.matches("")) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_PESEL_ID),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean isSurenameEmpty() {
        String surename = callback.getSurname();
        if (surename.matches("")) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_SURNAME),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean isFirstnameEmpty() {
        String firstname = callback.getFirstname();
        if (firstname.matches("")) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_FIRSTNAME),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean validatePesel() {
        String pesel = callback.getPesel();
        Matcher matcher = PESEL_PATTERN.matcher(pesel);
        if (!matcher.matches()) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_INCORRECT_PESEL_FORMAT),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validatePasswords() {
        String password = callback.getPassword();
        String passwordAgain = callback.getPasswordAgain();
        if (!password.equals(passwordAgain)) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_TYPED_PASSWORD_NOT_EQUAL),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validateBirthDate() {
        String birthDate = callback.getBirthDate();
        if (!DateUtils.isCorrectBirthDate(birthDate)) {
            Toast.makeText(callback, callback.getString(R.string.TOAST_MESSAGE_INCORRECT_BIRTH_DATE_FORMAT),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
