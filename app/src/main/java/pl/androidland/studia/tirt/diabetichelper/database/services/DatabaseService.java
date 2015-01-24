package pl.androidland.studia.tirt.diabetichelper.database.services;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.database.models.GlucoseMeasurement;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.UserFactory;

import java.util.Date;


public class DatabaseService {

    private static final Realm REALM_IO = ApplicationBus.getRealmInstance();

    public static void addUser(String peselId, String firstname, String surename, Date birthDate, String password, boolean isDoctor) {
        REALM_IO.beginTransaction();
        UserFactory.createUser(peselId, firstname,surename, birthDate, password, isDoctor);
        REALM_IO.commitTransaction();
    }



    public static void addMeasurement(User user, double measurement) {
        REALM_IO.beginTransaction();
        user.getMeasurements().add(createMeasurment(measurement));
        REALM_IO.commitTransaction();
    }

    private static GlucoseMeasurement createMeasurment(double measurement) {
        GlucoseMeasurement glucoseMeasurement = REALM_IO.createObject(GlucoseMeasurement.class);
        glucoseMeasurement.setDate(new Date());
        glucoseMeasurement.setMeasurement(measurement);
        return glucoseMeasurement;
    }

    public static void assignPatientToDoctor(User patient, User doctor) {
        REALM_IO.beginTransaction();
        doctor.getPatients().add(patient);
        REALM_IO.commitTransaction();
    }

    public static void unassignPatientToDoctor(User patient, User doctor) {
        if(!isPatientAssignedToDoctor(patient, doctor))
            return;
        REALM_IO.beginTransaction();
        doctor.getPatients().remove(patient);
        REALM_IO.commitTransaction();
    }

    private static boolean isPatientAssignedToDoctor(User patient, User doctor) {
        return doctor.getPatients().contains(patient);
    }


    public static User getPatientByPesel(String id) {
        RealmQuery<User> results = REALM_IO.where(User.class);
        return results.equalTo("peselId", id).findFirst();
    }

    public static boolean isPatientRegistered(String id) {
        RealmQuery<User> results = REALM_IO.where(User.class).equalTo("peselId", id);
        return !results.findAll().isEmpty();
    }



    public static RealmResults<User> getResults() {
        return REALM_IO.where(User.class).findAll();
    }
}
