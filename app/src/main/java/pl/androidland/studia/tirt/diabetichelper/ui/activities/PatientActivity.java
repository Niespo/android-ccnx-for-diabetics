package pl.androidland.studia.tirt.diabetichelper.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.commons.lang.StringUtils;
import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.ui.adapters.MeasurementsAdapter;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;


public class PatientActivity extends UserActivity {
    private static final ApplicationState state = ApplicationBus.getState();

    private ListView lvPatientMeasurements;
    private EditText etCurrentMeasurement;
    private double measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_activity);
        initComponents();
        configureComponents();
    }

    protected void initComponents() {
        super.initComponents();
        lvPatientMeasurements = (ListView) findViewById(R.id.patient_measurements);
        etCurrentMeasurement = (EditText) findViewById(R.id.patient_current_measurement);
    }

    protected void configureComponents() {
        super.configureComponents();
        MeasurementsAdapter adapter = new MeasurementsAdapter(this, getUserRequestedForMeasurment().getMeasurements()
                .where().findAll(),
                true);
        lvPatientMeasurements.setAdapter(adapter);
    }

    public void addMeasurement(View view) {
        User user = state.getLoggedInUser();
        if (validateMeasurement()) {
            DatabaseService.addMeasurement(user, measurement);
            etCurrentMeasurement.setText(StringUtils.EMPTY);
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_ADD_MEASUREMENT), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private boolean validateMeasurement() {
        measurement = 0.0d;
        try {
            measurement = Double.parseDouble(etCurrentMeasurement.getText().toString());
            return true;
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.TOAST_MESSAGE_UNSUPPORTED_MEASUREMENT_FORMAT),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
