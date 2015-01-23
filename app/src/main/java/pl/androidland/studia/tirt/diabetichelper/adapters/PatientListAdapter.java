package pl.androidland.studia.tirt.diabetichelper.adapters;

import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.ApplicationState;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.activities.MeasurementActivity;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;


public class PatientListAdapter extends RealmBaseAdapter<User> implements ListAdapter {

    private static final ApplicationState state = ApplicationBus.getState();
    private final Context context;

    private static class ViewHolder {
        private TextView tvFirstname;
        private TextView tvSurename;
        private TextView tvPesel;
        private Button bShowMeasurements;
        private Button bUnassignPatient;
    }

    public PatientListAdapter(Context context, RealmResults<User> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.patient_item, parent, false);
            viewHolder = initViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        realmResults.sort("surname");
        final User user = realmResults.get(position);
        viewHolder.tvFirstname.setText(user.getFirstname());
        viewHolder.tvSurename.setText(user.getSurname());
        viewHolder.tvPesel.setText(user.getPeselId());
        viewHolder.bShowMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MeasurementActivity.class);
                intent.putExtra("peselId", user.getPeselId());
                context.startActivity(intent);
            }
        });
        viewHolder.bUnassignPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User doctor = state.getLoggedInUser();
                DatabaseService.unassignPatientToDoctor(user, doctor);
            }
        });
        return convertView;
    }

    private ViewHolder initViewHolder(View convertView) {
        ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        viewHolder.tvFirstname = (TextView) convertView.findViewById(R.id.patient_item_firstname);
        viewHolder.tvSurename = (TextView) convertView.findViewById(R.id.patient_item_surename);
        viewHolder.tvPesel = (TextView) convertView.findViewById(R.id.patient_item_pesel);
        viewHolder.bShowMeasurements = (Button) convertView.findViewById(R.id.show_measurements_button);
        viewHolder.bUnassignPatient = (Button) convertView.findViewById(R.id.remove_patient_button);
        return viewHolder;
    }

}
