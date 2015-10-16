package de.falkentavio.speechrecognizer.Actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by foellerich on 16.10.2015.
 */
public class TimeAction {
    public static ArrayList<String> getRecognizer() {
        return new ArrayList<>(Arrays.asList(
                "spät",
                "wie",
                "es",
                "ist"
        ));
    }
    public String TimeAction() {
        String date = new SimpleDateFormat("HH:mm").format(new Date());
        return "Es ist " + date + " Uhr";
    }
}
