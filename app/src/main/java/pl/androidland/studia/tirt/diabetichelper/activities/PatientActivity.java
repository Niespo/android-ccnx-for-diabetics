package pl.androidland.studia.tirt.diabetichelper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.lang.StringUtils;
import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.adapters.MeasurementsAdapter;
import pl.androidland.studia.tirt.diabetichelper.android.DateUtils;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;


public class PatientActivity extends UserActivity {

    private static final ApplicationState state = ApplicationBus.getState();

    private TextView tvFirstname;
    private TextView tvSurename;
    private TextView tvPesel;
    private TextView tvBirthDate;
    private ListView lvPatientMeasurements;
    private EditText etCurrentMeasurement;
    private double measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity);
        initComponents();
        setComponents();
    }

    private void initComponents() {
        tvFirstname = (TextView) findViewById(R.id.patient_firstname);
        tvSurename = (TextView) findViewById(R.id.patient_surename);
        tvPesel = (TextView) findViewById(R.id.patient_pesel);
        tvBirthDate = (TextView) findViewById(R.id.patient_age);
        lvPatientMeasurements = (ListView) findViewById(R.id.patient_measurements);
        etCurrentMeasurement = (EditText) findViewById(R.id.patient_current_measurement);
    }

    private void setComponents() {
        if (!state.isLoggedUser() || state.getLoggedInUser() == null)
            finishOnLogout();

        User user = state.getLoggedInUser();
        tvFirstname.setText(user.getFirstname());
        tvSurename.setText(user.getSurname());
        tvPesel.setText(user.getPeselId());
        tvBirthDate.setText(DateUtils.toBirthDate(user.getBirthDate()));
        MeasurementsAdapter adapter = new MeasurementsAdapter(this, user.getMeasurements().where().findAll(),
                true);
        lvPatientMeasurements.setAdapter(adapter);
    }

    public void addMeasurement(View view) {
        User user = state.getLoggedInUser();
        if (validateMeasurement()) {
            DatabaseService.addMeasurement(user, measurement);
            etCurrentMeasurement.setText(StringUtils.EMPTY);
            Toast.makeText(this, "Dodano pomiar!", Toast.LENGTH_SHORT).show();
        }
    }



    private boolean validateMeasurement() {
        measurement = 0.0d;
        try {
            measurement = Double.parseDouble(etCurrentMeasurement.getText().toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(this, "Nieprawidłowy format pomiaru", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
