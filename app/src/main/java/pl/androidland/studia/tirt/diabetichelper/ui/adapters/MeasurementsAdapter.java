package pl.androidland.studia.tirt.diabetichelper.ui.adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import pl.androidland.studia.tirt.diabetichelper.R;
import pl.androidland.studia.tirt.diabetichelper.utils.DateUtils;
import pl.androidland.studia.tirt.diabetichelper.database.models.GlucoseMeasurement;


public class MeasurementsAdapter extends RealmBaseAdapter<GlucoseMeasurement> implements ListAdapter {

    private static class ViewHolder {
        private TextView measurement;
        private TextView date;
    }

    public MeasurementsAdapter(Context context, RealmResults<GlucoseMeasurement> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.measurement_item,parent, false);
            viewHolder = initViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        realmResults.sort("date", false);
        GlucoseMeasurement measurement = realmResults.get(position);
        viewHolder.measurement.setText(String.format("%.2f", measurement.getMeasurement()));
        viewHolder.date.setText(DateUtils.toSimpleFormat(measurement.getDate()));
        return convertView;
    }



    private ViewHolder initViewHolder(View convertView) {
        ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        viewHolder.measurement = (TextView) convertView.findViewById(R.id.measurement_level);
        viewHolder.date = (TextView) convertView.findViewById(R.id.measurement_date);
        return viewHolder;
    }

}
