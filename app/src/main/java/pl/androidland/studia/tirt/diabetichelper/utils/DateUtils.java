package pl.androidland.studia.tirt.diabetichelper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class DateUtils {
    private static final SimpleDateFormat SIMPLE_FORMATTER = new SimpleDateFormat("hh:mm dd-MM-yyyy");
    private static final SimpleDateFormat BIRTH_DATE_FORMATTER = new SimpleDateFormat("dd-mm-yyyy");
    private static final Pattern BIRTH_DATE_PATTERN = Pattern
            .compile("^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)$");

    public static String toSimpleFormat(Date date) {
        return SIMPLE_FORMATTER.format(date);
    }

    public static String toBirthDate(Date date) {
        return BIRTH_DATE_FORMATTER.format(date);
    }

    public static boolean isCorrectBirthDate(String date) {
        return BIRTH_DATE_PATTERN.matcher(date).matches();
    }

    public static Date parseBirthDate(String date) {
        try {
            return BIRTH_DATE_FORMATTER.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
