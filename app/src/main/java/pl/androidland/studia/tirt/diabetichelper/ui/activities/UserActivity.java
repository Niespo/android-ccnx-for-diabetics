package pl.androidland.studia.tirt.diabetichelper.ui.activities;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.utils.DateUtils;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;


public class UserActivity extends Activity {
    protected static final ApplicationState state = ApplicationBus.getState();

    private TextView tvFirstname;
    private TextView tvSurname;
    private TextView tvPesel;
    private TextView tvBirthDate;
    private User userRequestedForMeasurment;

    public void logoutUser(View view) {
        state.setLoggedOut();
        finishOnLogout();
    }

    protected void initComponents() {
        tvFirstname = (TextView) findViewById(R.id.firstname);
        tvSurname = (TextView) findViewById(R.id.surname);
        tvPesel = (TextView) findViewById(R.id.peselId);
        tvBirthDate = (TextView) findViewById(R.id.birthDate);
    }

    protected void configureComponents() {
        if (!state.isLoggedUser() || state.getLoggedInUser() == null)
            finishOnLogout();
        setUser(state.getLoggedInUser());
    }

    protected boolean setUser(User user) {
        if (user != null) {
            setUserRequestedForMeasurment(user);
            setUserData(user);
            return true;
        }
        return false;
    }

    protected void setUserData(User user) {
        tvFirstname.setText(user.getFirstname());
        tvSurname.setText(user.getSurname());
        tvPesel.setText(user.getPeselId());
        tvBirthDate.setText(DateUtils.toBirthDate(user.getBirthDate()));
    }

    protected void finishOnLogout() {
        Toast.makeText(this, getString(R.string.TOAST_MESSAGE_LOGOUT), Toast.LENGTH_SHORT).show();
        finish();
    }

    protected boolean validatePatient(String pesel) {
        if (!DatabaseService.isPatientRegistered(pesel)) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_PATIENT_DOES_NOT_EXIST), Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    protected User getUserRequestedForMeasurment() {
        return userRequestedForMeasurment;
    }

    protected User setUserRequestedForMeasurment(User user) {
        return userRequestedForMeasurment = user;
    }

}
