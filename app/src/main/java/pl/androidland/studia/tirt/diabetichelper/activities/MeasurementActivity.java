package pl.androidland.studia.tirt.diabetichelper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import org.apache.commons.lang.StringUtils;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.adapters.MeasurementsAdapter;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;

public class MeasurementActivity extends UserActivity {

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

    protected void initComponents() {
        super.initComponents();
        lvPatientMeasurements = (ListView) findViewById(R.id.patient_measurements);
    }

    private void setComponents() {
        if (!state.isLoggedUser() || state.getLoggedInUser() == null)
            finishOnLogout();

        User user = DatabaseService.getPatientByPesel(peselId);
        if(!setUser(user))
            return;

        MeasurementsAdapter adapter = new MeasurementsAdapter(this, user.getMeasurements().where().findAll(), true);
        lvPatientMeasurements.setAdapter(adapter);
    }

    public void closeMeasurements(View view){
        finish();
    }


}
