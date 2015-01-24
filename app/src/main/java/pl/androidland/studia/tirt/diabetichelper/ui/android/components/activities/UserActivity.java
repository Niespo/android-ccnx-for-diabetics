package pl.androidland.studia.tirt.diabetichelper.ui.android.components.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.ui.validators.UserValidator;
import pl.androidland.studia.tirt.diabetichelper.utils.DateUtils;


public class UserActivity extends Activity {
    protected static final ApplicationState state = ApplicationBus.getState();

    private TextView tvFirstname;
    private TextView tvSurname;
    private TextView tvPesel;
    private TextView tvBirthDate;
    private User userRequestedForMeasurment;

    private UserValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new UserValidator(this);
    }

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

    protected UserValidator getValidator() {
        return validator;
    }

    protected User getUserRequestedForMeasurment() {
        return userRequestedForMeasurment;
    }

    protected User setUserRequestedForMeasurment(User user) {
        return userRequestedForMeasurment = user;
    }

}
