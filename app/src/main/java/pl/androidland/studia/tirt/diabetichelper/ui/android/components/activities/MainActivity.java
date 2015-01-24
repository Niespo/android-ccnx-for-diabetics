package pl.androidland.studia.tirt.diabetichelper.ui.android.components.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.auth.AuthService;


public class MainActivity extends Activity {

    private static final ApplicationState state = ApplicationBus.getState();

    private AuthService auth;
    private EditText etPesel;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            auth = new AuthService(this);
            initComponents();
        }
    }

    private void initComponents() {
        etPesel = (EditText) findViewById(R.id.peselId);
        etPassword = (EditText) findViewById(R.id.user_password);
    }

    public void registerPatient(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void login(View view) {
        String peselId = etPesel.getText().toString();
        String password = etPassword.getText().toString();
        if (auth.login(peselId, password))
            launchProperActivity();

    }

    private void launchProperActivity() {
        if (state.getLoggedInUser().isDoctor()){
            startDoctorActivity();
        }
        else {
            startPatientActivity();
        }
    }

    private void startDoctorActivity() {
        Toast.makeText(this, getString(R.string.TOAST_MESSAGE_LOGGED_AS_DOCTOR), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DoctorActivity.class));
    }

    private void startPatientActivity() {
        Toast.makeText(this, getString(R.string.TOAST_MESSAGE_LOGGED_AS_PATIENT), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, PatientActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
