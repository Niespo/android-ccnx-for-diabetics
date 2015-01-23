package pl.androidland.studia.tirt.diabetichelper.database.models;

import io.realm.RealmObject;

import java.util.Date;


public class GlucoseMeasurement extends RealmObject {
    private double measurement;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    private Date data;

}
