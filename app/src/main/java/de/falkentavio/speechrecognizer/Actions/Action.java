package de.falkentavio.speechrecognizer.Actions;

import java.util.ArrayList;

/**
 * Created by foellerich on 16.10.2015.
 *
 */
interface Action {
    ArrayList<String> getRecognizer();
    boolean isActionFitting(String input);
    String execute();
}
