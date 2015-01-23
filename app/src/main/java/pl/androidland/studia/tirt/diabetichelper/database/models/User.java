package pl.androidland.studia.tirt.diabetichelper.database.models;

import io.realm.RealmList;
import io.realm.RealmObject;

import java.util.Date;


public class User extends RealmObject {
    private String peselId;
    private String firstname;
    private String surname;
    private Date birthDate;
    private String password;
    private RealmList<GlucoseMeasurement> measurements;
    private boolean isDoctor;
    private RealmList<User> patients;

    public RealmList<User> getPatients() {
        return patients;
    }

    public void setPatients(RealmList<User> patients) {
        this.patients = patients;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean isDoctor) {
        this.isDoctor = isDoctor;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RealmList<GlucoseMeasurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(RealmList<GlucoseMeasurement> measurements) {
        this.measurements = measurements;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPeselId() {
        return peselId;
    }

    public void setPeselId(String peselId) {
        this.peselId = peselId;
    }

}
