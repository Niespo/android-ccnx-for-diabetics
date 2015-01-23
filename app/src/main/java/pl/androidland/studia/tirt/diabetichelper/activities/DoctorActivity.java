package pl.androidland.studia.tirt.diabetichelper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.adapters.PatientListAdapter;
import pl.androidland.studia.tirt.diabetichelper.android.DateUtils;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;


public class DoctorActivity extends UserActivity {



    private TextView tvFirstname;
    private TextView tvSurname;
    private TextView tvPesel;
    private TextView tvBirtdate;
    private ListView lvPatientList;
    private EditText etPatientPesel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity);
        initComponents();
        setComponents();
    }

    public void addPatient(View view) {
        String pesel = etPatientPesel.getText().toString();
        if (!validatePatient(pesel))
            return;

        User patient = DatabaseService.getPatientByPesel(pesel);
        DatabaseService.assignPatientToDoctor(patient, state.getLoggedInUser());
    }

    private boolean validatePatient(String pesel) {
        if(!DatabaseService.isPatientRegistered(pesel)){
            Toast.makeText(this, "Taki pacjent nie istnieje", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initComponents() {
        tvFirstname = (TextView) findViewById(R.id.user_firstname);
        tvSurname = (TextView) findViewById(R.id.user_surename);
        tvPesel = (TextView) findViewById(R.id.user_pesel);
        tvBirtdate = (TextView) findViewById(R.id.user_birtdate);
        lvPatientList = (ListView) findViewById(R.id.patient_list);
        etPatientPesel = (EditText) findViewById(R.id.patient_pesel_to_add);
    }

    private void setComponents() {
        if (!state.isLoggedUser() || state.getLoggedInUser() == null)
            finishOnLogout();

        User user = state.getLoggedInUser();
        tvFirstname.setText(user.getFirstname());
        tvSurname.setText(user.getSurname());
        tvBirtdate.setText(DateUtils.toBirthDate(user.getBirthDate()));
        tvPesel.setText(user.getPeselId());

        PatientListAdapter adapter = new PatientListAdapter(this, user.getPatients().where().findAll(), true);
        lvPatientList.setAdapter(adapter);
    }

}
