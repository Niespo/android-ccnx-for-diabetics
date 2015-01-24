package pl.androidland.studia.tirt.diabetichelper.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.commons.lang.StringUtils;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import pl.androidland.studia.tirt.diabetichelper.ui.validators.RegisterValidator;
import pl.androidland.studia.tirt.diabetichelper.utils.DateUtils;
import pl.androidland.studia.tirt.diabetichelper.utils.EncryptionUtils;

import java.util.Date;


public class RegisterActivity extends Activity {

    private EditText etFirstname;
    private EditText etSurname;
    private EditText etPesel;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private EditText etBirthDate;
    private RegisterValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        validator = new RegisterValidator(this);
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
        if (validator.validateInputIsNotEmpty() && validator.validatePesel()
                && validator.validatePatientRegistration()
                && validator.validatePasswords() && validator.validateBirthDate())
            addPatient();
    }

    private void addPatient() {
        DatabaseService.addUser(getPesel(), getFirstname(), getSurname(), getFormattedBirthDate(),
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

    public String getPesel() {
        return etPesel.getText().toString();
    }

    public String getFirstname() {
        return etFirstname.getText().toString();
    }

    public String getSurname() {
        return etSurname.getText().toString();
    }

    public String getBirthDate() {
        return etBirthDate.getText().toString();
    }

    public String getPassword() {
        return etPassword.getText().toString();
    }

    public String getPasswordAgain() {
        return etPasswordAgain.getText().toString();
    }

    private Date getFormattedBirthDate() {
        return DateUtils.parseBirthDate(etBirthDate.getText().toString());
    }

    private String getPasswordAsHash() {
        String encryptedPassword = etPassword.getText().toString();
        return EncryptionUtils.generateMd5Hash(encryptedPassword);
    }

}
