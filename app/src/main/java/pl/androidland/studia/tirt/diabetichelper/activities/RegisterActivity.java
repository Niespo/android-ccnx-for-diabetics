package pl.androidland.studia.tirt.diabetichelper.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.commons.lang.StringUtils;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.utils.DateUtils;
import pl.androidland.studia.tirt.diabetichelper.utils.EncryptionUtils;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends Activity {

    private final static Pattern PESEL_PATTERN = Pattern.compile("[0-9]{11}");

    private EditText etFirstname;
    private EditText etSurname;
    private EditText etPesel;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private EditText etBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initComponents();
    }

    private void initComponents() {
        etFirstname = (EditText) findViewById(R.id.register_firstname);
        etSurname = (EditText) findViewById(R.id.register_surename);
        etBirthDate = (EditText) findViewById(R.id.register_birthdate);
        etPesel = (EditText) findViewById(R.id.register_pesel);
        etPassword = (EditText) findViewById(R.id.register_password);
        etPasswordAgain = (EditText) findViewById(R.id.register_password_again);
    }

    public void registerNewUser(View view) {
        if (validateInputIsNotEmpty() && validatePesel() && validatePatientRegistration()
                && validatePasswords() && validateBirthDate())
            addPatient();
    }

    private void addPatient() {
        DatabaseService.addUser(getPesel(), getFirstname(), getSurename(), getBirthDate(),
                getPasswordAsHash(), false);
        Toast.makeText(this, getString(R.string.TOAST_MESSAGE_REGISTRATION_SUCCESS),
                Toast.LENGTH_SHORT).show();
        cleanForm();
        this.finish();
    }

    private void cleanForm() {
        etFirstname.setText(StringUtils.EMPTY);
        etSurname.setText(StringUtils.EMPTY);
        etBirthDate.setText(StringUtils.EMPTY);
        etPesel.setText(StringUtils.EMPTY);
        etPassword.setText(StringUtils.EMPTY);
        etPasswordAgain.setText(StringUtils.EMPTY);
    }

    private boolean validatePatientRegistration() {
        if (DatabaseService.isPatientRegistered(etPesel.getText().toString())) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_USER_ALREADY_HAS_REGISTERED),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateInputIsNotEmpty() {
        if (isFirstnameEmpty() || isSurenameEmpty() || isPeselEmpty() || isBirthDateEmpty()
                || isPasswordEmpty() || isPasswordAgainEmpty())
            return false;
        return true;
    }

    private boolean isPasswordAgainEmpty() {
        String passwordAgain = etPassword.getText().toString();
        if (passwordAgain.matches("")) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_PASSWORD_AGAIN), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isPasswordEmpty() {
        String password = etPassword.getText().toString();

        if (password.matches("")) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_PASSWORD), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isBirthDateEmpty() {
        String date = etBirthDate.getText().toString();
        if (date.matches("")) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_BIRTH_DATE), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isPeselEmpty() {
        String pesel = etPesel.getText().toString();

        if (pesel.matches("")) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_PESEL_ID), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isSurenameEmpty() {
        String surename = etSurname.getText().toString();
        if (surename.matches("")) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_SURNAME), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isFirstnameEmpty() {
        String firstname = etFirstname.getText().toString();
        if (firstname.matches("")) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_HAVE_TO_TYPE_FIRSTNAME), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean validatePesel() {
        CharSequence pesel = etPesel.getText();
        Matcher matcher = PESEL_PATTERN.matcher(pesel);
        if (!matcher.matches()) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_INCORRECT_PESEL_FORMAT), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePasswords() {
        String password = etPassword.getText().toString();
        String passwordAgain = etPasswordAgain.getText().toString();
        if (!password.equals(passwordAgain)) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_TYPED_PASSWORD_NOT_EQUAL), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateBirthDate() {
        String birthDate = etBirthDate.getText().toString();
        if (!DateUtils.isCorrectBirthDate(birthDate)) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_INCORRECT_BIRTH_DATE_FORMAT), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String getPesel() {
        return etPesel.getText().toString();
    }

    private String getFirstname() {
        return etFirstname.getText().toString();
    }

    private String getSurename() {
        return etSurname.getText().toString();
    }

    private Date getBirthDate() {
        return DateUtils.parseBirthDate(etBirthDate.getText().toString());
    }

    private String getPasswordAsHash() {
        String encryptedPassword = etPassword.getText().toString();
        return EncryptionUtils.generateMd5Hash(encryptedPassword);
    }

}
