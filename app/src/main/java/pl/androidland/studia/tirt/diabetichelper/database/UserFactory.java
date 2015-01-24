package pl.androidland.studia.tirt.diabetichelper.database;

import io.realm.Realm;
import pl.androidland.studia.tirt.diabetichelper.ApplicationBus;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;

import java.util.Date;


public class UserFactory {

    private static final Realm REALM_IO = ApplicationBus.getRealmInstance();

    public static User createUser(String peselId, String firstname, String surename, Date birthDate,
            String password, boolean isDoctor) {
        User user = REALM_IO.createObject(User.class);
        if (isDoctor)
            decorateAsDoctor(user, peselId, firstname, surename, birthDate, password);
        else
            decorateAsPatient(user, peselId, firstname, surename, birthDate, password);

        return user;
    }

    private static void decorateAsDoctor(User user, String peselId, String firstname, String surename,
            Date birthDate, String password) {
        user.setPeselId(peselId);
        user.setFirstname(firstname);
        user.setSurname(surename);
        user.setBirthDate(birthDate);
        user.setPassword(password);
        user.setDoctor(true);
    }

    private static void decorateAsPatient(User user, String peselId, String firstname, String surename,
            Date birthDate, String password) {
        user.setPeselId(peselId);
        user.setFirstname(firstname);
        user.setSurname(surename);
        user.setBirthDate(birthDate);
        user.setPassword(password);
        user.setDoctor(false);
    }
}
