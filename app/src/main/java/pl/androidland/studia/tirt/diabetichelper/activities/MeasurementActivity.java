package pl.androidland.studia.tirt.diabetichelper.activities;

import org.apache.commons.lang.StringUtils;

import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.adapters.MeasurementsAdapter;
import pl.androidland.studia.tirt.diabetichelper.android.DateUtils;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MeasurementActivity extends Activity {

    private static final ApplicationState state = ApplicationBus.getState();

    private TextView tvFirstname;
    private TextView tvSurename;
    private TextView tvPesel;
    private TextView tvBirthDate;
    private ListView lvPatientMeasurements;
    private String peselId = StringUtils.EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurement_activity);
        getBundles();
        initComponents();
        setComponents();
    }

    private void getBundles() {
        Bundle bundle = getIntent().getExtras();
        peselId = bundle.getString("peselId");
    }

    private void initComponents() {
        tvFirstname = (TextView) findViewById(R.id.patient_firstname);
        tvSurename = (TextView) findViewById(R.id.patient_surename);
        tvPesel = (TextView) findViewById(R.id.patient_pesel);
        tvBirthDate = (TextView) findViewById(R.id.patient_age);
        lvPatientMeasurements = (ListView) findViewById(R.id.patient_measurements);
    }

    private void setComponents() {
        if (!state.isLoggedUser() || state.getLoggedInUser() == null)
            finishOnLogout();

        User user = DatabaseService.getPatientByPesel(peselId);
        if(user == null)
            return;

        tvFirstname.setText(user.getFirstname());
        tvSurename.setText(user.getSurname());
        tvPesel.setText(user.getPeselId());
        tvBirthDate.setText(DateUtils.toBirthDate(user.getBirthDate()));
        MeasurementsAdapter adapter = new MeasurementsAdapter(this, user.getMeasurements().where().findAll(), true);
        lvPatientMeasurements.setAdapter(adapter);
    }

    public void closeMeasurements(View view){
        finish();
    }

    private void finishOnLogout() {

        Toast.makeText(this, "Wylogowano!", Toast.LENGTH_SHORT).show();
        finish();

    }
}
