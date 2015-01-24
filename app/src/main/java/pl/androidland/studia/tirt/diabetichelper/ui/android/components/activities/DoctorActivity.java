package pl.androidland.studia.tirt.diabetichelper.ui.android.components.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.ui.android.components.adapters.PatientListAdapter;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;


public class DoctorActivity extends UserActivity {
    private ListView lvPatientList;
    private EditText etPatientPesel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity);
        initComponents();
        configureComponents();
    }

    public void assignPatient(View view) {
        String pesel = etPatientPesel.getText().toString();
        if (!getValidator().validatePatient(pesel))
            return;

        User patient = DatabaseService.getPatientByPesel(pesel);
        DatabaseService.assignPatientToDoctor(patient, state.getLoggedInUser());
    }

    protected void initComponents() {
        super.initComponents();
        lvPatientList = (ListView) findViewById(R.id.patient_list);
        etPatientPesel = (EditText) findViewById(R.id.patient_pesel_to_add);
    }

    protected void configureComponents() {
        super.configureComponents();
        PatientListAdapter adapter = new PatientListAdapter(this, getUserRequestedForMeasurment().getPatients().where()
                .findAll(), true);
        lvPatientList.setAdapter(adapter);
    }

}
