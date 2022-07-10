package Service;

import org.openqa.selenium.WebElement;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Operations {

    public static Calendar stringToCalendar(String date) {
        //В данный момент используется обработка символа /, можно доработать регуляьркой при необходимости.
        Calendar calendar = new GregorianCalendar();
        int month = Integer.parseInt(date.substring(0, date.indexOf("/")));
        int day = Integer.parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));
        int year = Integer.parseInt(date.substring(date.lastIndexOf("/") + 1));;
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        return calendar;
    }

    public static String calendarToString(Calendar calendar) {
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return month + day + year;
    }

    public static String getNameFromFullName(String fullName) {
        return fullName.substring(0, fullName.indexOf(" "));
    }

    public static String getSurNameFromFullName(String fullName) {
        return fullName.substring(fullName.indexOf(" ") + 1);
    }

    public static boolean compareDates(Calendar firstDate, Calendar secondDate) {
        //Сравнение только даты
        if (firstDate.get(Calendar.YEAR) != secondDate.get(Calendar.YEAR)) {
            return false;
        }

        if (firstDate.get(Calendar.MONTH) != secondDate.get(Calendar.MONTH)) {
            return false;
        }

        if (firstDate.get(Calendar.DAY_OF_MONTH) != secondDate.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }

        return true;
    }

}