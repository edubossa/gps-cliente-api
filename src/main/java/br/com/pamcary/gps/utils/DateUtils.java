package br.com.pamcary.gps.utils;

import br.com.pamcary.gps.handler.DataInvalidaHandlerException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    static {
        sdf.setLenient(false);
    }

    public static Date stringToDate(String data) {
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            throw new DataInvalidaHandlerException();
        }
    }

    public static String dateToString(Date data) {
        return sdf.format(data);
    }

}
