package pl.androidland.studia.tirt.diabetichelper.database.models;

import io.realm.RealmObject;

import java.util.Date;


public class GlucoseMeasurement extends RealmObject {
    private double measurement;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    private Date date;

}
