package de.falkentavio.speechrecognizer.Actions;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by foellerich on 16.10.2015.
 */
public class GreetingAction implements Action {
    @Override
    public ArrayList<String> getRecognizer() {
        return new ArrayList<>(Arrays.asList(
                "hallo",
                "hi",
                "grüße",
                "moin",
                "hey"
        ));
    }

    @Override
    public boolean isActionFitting(String input) {
        boolean isFitting = false;
        for (String s : getRecognizer()) {
            if (input.toLowerCase().contains(s.toLowerCase())) {
                isFitting = true;
                break;
            }
        }
        return isFitting;
    }

    @Override
    public String execute() {
        return "Hi";
    }
}
