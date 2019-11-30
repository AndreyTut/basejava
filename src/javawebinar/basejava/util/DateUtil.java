package javawebinar.basejava.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = of(3000, Month.of(1));

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String getShortDate(LocalDate localDate) {
        if (localDate.equals(NOW)){
            return "сейчас";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/yyyy");
        return formatter.format(localDate);
    }
}