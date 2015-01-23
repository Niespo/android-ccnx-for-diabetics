package pl.androidland.studia.tirt.diabetichelper.utils.dummydata;

import pl.androidland.studia.tirt.diabetichelper.utils.DateUtils;
import pl.androidland.studia.tirt.diabetichelper.utils.EncryptionUtils;
import pl.androidland.studia.tirt.diabetichelper.database.models.User;
import pl.androidland.studia.tirt.diabetichelper.database.services.DatabaseService;

import java.util.Random;


public class DummyDataMocker {

    private static final String FIRST_DOCTOR_PESEL_ID = "60010100000";
    private static final String SECOND_DOCTOR_PESEL_ID = "60010100001";

    private static final String[] PATIENTS_PESEL_IDS = { "40010100000", "40010100001", "50010100000",
            "50010100001", "70010100000", "70010100001", "80010100000", "80010100001" };

    public static void fillWithDummyDoctors() {
        DatabaseService.addUser(FIRST_DOCTOR_PESEL_ID, "Jan", "Lekarski",
                DateUtils.parseBirthDate("01-01-1960"), EncryptionUtils.generateMd5Hash("password60"), true);
        DatabaseService.addUser(SECOND_DOCTOR_PESEL_ID, "Piotr", "Doktorski",
                DateUtils.parseBirthDate("01-01-1960"), EncryptionUtils.generateMd5Hash("password60"), true);
    }

    public static void fillWithDummyPatients() {
        DatabaseService.addUser(PATIENTS_PESEL_IDS[0], "Jan", "Kowal",
                DateUtils.parseBirthDate("01-01-1940"),
                EncryptionUtils.generateMd5Hash("password40"), false);
        DatabaseService.addUser(PATIENTS_PESEL_IDS[1], "Barbara", "Nowak",
                DateUtils.parseBirthDate("01-01-1940"),
                EncryptionUtils.generateMd5Hash("password40"), false);
        DatabaseService.addUser(PATIENTS_PESEL_IDS[2], "Tomasz", "Nowicki",
                DateUtils.parseBirthDate("01-01-1950"),
                EncryptionUtils.generateMd5Hash("password50"), false);
        DatabaseService.addUser(PATIENTS_PESEL_IDS[3], "Zofia", "Kowalska",
                DateUtils.parseBirthDate("01-01-1950"),
                EncryptionUtils.generateMd5Hash("password50"), false);
        DatabaseService.addUser(PATIENTS_PESEL_IDS[4], "Zbigniew", "Polski",
                DateUtils.parseBirthDate("01-01-1970"),
                EncryptionUtils.generateMd5Hash("password70"), false);
        DatabaseService.addUser(PATIENTS_PESEL_IDS[5], "Teresa", "Abacka",
                DateUtils.parseBirthDate("01-01-1970"),
                EncryptionUtils.generateMd5Hash("password70"), false);
        DatabaseService.addUser(PATIENTS_PESEL_IDS[6], "Marcin", "Radzki",
                DateUtils.parseBirthDate("01-01-1980"),
                EncryptionUtils.generateMd5Hash("password80"), false);
        DatabaseService.addUser(PATIENTS_PESEL_IDS[7], "Anna", "Wanna",
                DateUtils.parseBirthDate("01-01-1980"),
                EncryptionUtils.generateMd5Hash("password80"), false);
    }

    public static void assingDummyPatientsToDoctors() {
        User firstDoctor = DatabaseService.getPatientByPesel(FIRST_DOCTOR_PESEL_ID);
        assignDummyPatientToDoctor(firstDoctor, PATIENTS_PESEL_IDS[0], PATIENTS_PESEL_IDS[2],
                PATIENTS_PESEL_IDS[3], PATIENTS_PESEL_IDS[7]);
        User secondDoctor = DatabaseService.getPatientByPesel(SECOND_DOCTOR_PESEL_ID);
        assignDummyPatientToDoctor(secondDoctor, PATIENTS_PESEL_IDS[1], PATIENTS_PESEL_IDS[4],
                PATIENTS_PESEL_IDS[5], PATIENTS_PESEL_IDS[6]);

    }

    public static void fillWithDummyMeasurements() {
        for (String pesel : PATIENTS_PESEL_IDS)
            addRandomMeasurements(pesel);

    }

    private static void assignDummyPatientToDoctor(User doctor, String... peselIds) {
        for (String pesel : peselIds) {
            User patient = DatabaseService.getPatientByPesel(pesel);
            DatabaseService.assignPatientToDoctor(patient, doctor);
        }
    }

    private static void addRandomMeasurements(String peselId) {
        User patient = DatabaseService.getPatientByPesel(peselId);
        Random randomProvider = new Random();
        int countOfMeasurement = randomProvider.nextInt(10) + 7;
        for (int i = 0; i < countOfMeasurement; i++) {
            DatabaseService.addMeasurement(patient, createPseudoMeasurement(randomProvider, i));
        }
    }

    private static double createPseudoMeasurement(Random randomProvider, int seed) {
        double multiplier = randomProvider.nextDouble();
        return 60 + seed * randomProvider.nextInt(2) + multiplier * 10;
    }
}
