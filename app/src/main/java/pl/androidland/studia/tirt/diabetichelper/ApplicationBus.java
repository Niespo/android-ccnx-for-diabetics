package pl.androidland.studia.tirt.diabetichelper;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmResults;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;
import pl.androidland.studia.tirt.diabetichelper.utils.data.DummyDataMocker;

public class ApplicationBus extends Application {

    private static ApplicationState state;
    private static Realm realmIo ;

    @Override
    public void onCreate() {
        super.onCreate();
        state = new ApplicationState();
        realmIo = Realm.getInstance(getApplicationContext());
        checkDummyDataNeeded();
    }

    private void checkDummyDataNeeded() {
        RealmResults<User> users = DatabaseService.getResults();
        if(users.isEmpty()) {
            DummyDataMocker.fillWithDummyDoctors();
            DummyDataMocker.fillWithDummyPatients();
            DummyDataMocker.fillWithDummyMeasurements();
            DummyDataMocker.fillWithDummyMeasurements();
            DummyDataMocker.assingDummyPatientsToDoctors();
        }
    }

    public static ApplicationState getState() {
        return state;
    }

    public static Realm getRealmInstance() {
        return realmIo;
    }
}
