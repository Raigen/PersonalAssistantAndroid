package de.falkentavio.speechrecognizer.Actions;

import java.util.ArrayList;

/**
 * Created by foellerich on 16.10.2015.
 */
public interface Action {
    public ArrayList<String> getRecognizer();
    public boolean isActionFitting(String input);
    public String execute();
}
