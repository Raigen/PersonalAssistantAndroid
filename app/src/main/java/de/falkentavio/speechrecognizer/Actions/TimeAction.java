package de.falkentavio.speechrecognizer.Actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Created by foellerich on 16.10.2015.
 *
 */
class TimeAction implements Action {
    public ArrayList<String> getRecognizer() {
        return new ArrayList<>(Arrays.asList(
                "spät",
                "wie",
                "es",
                "ist"
        ));
    }

    @Override
    public boolean isActionFitting(String input) {
        boolean isFitting = true;
        for (String s : getRecognizer()) {
            if (!input.toLowerCase().contains(s.toLowerCase())) {
                isFitting = false;
                break;
            }
        }
        return isFitting;
    }

    public String execute() {
        String date = new SimpleDateFormat("HH:mm", Locale.GERMANY).format(new Date());
        return "Es ist " + date + " Uhr";
    }
}
