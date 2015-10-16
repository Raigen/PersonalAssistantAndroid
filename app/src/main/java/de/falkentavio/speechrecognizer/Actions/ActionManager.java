package de.falkentavio.speechrecognizer.Actions;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by foellerich on 16.10.2015.
 */

public class ActionManager {
//    private Context context;
    ArrayList<Action> actionList = new ArrayList<Action>();
    public ActionManager() {
//        context = applicationContext;
        actionList.add(new TimeAction());
        actionList.add(new GreetingAction());
    }

    public String execute(String input) {
        Action action = findAction(input);
        return action.execute();
    }

    private Action findAction(String inputString) {
        for (Action action : actionList) {
            if (action.isActionFitting(inputString)) {
                return action;
            }
        }
        return new DefaultAction();
    }
//
//    private boolean contains(String input, ArrayList<String> needles) {
//        boolean returns = true;
//        for (String needle : needles) {
//            if (!input.contains(needle)) {
//                returns = false;
//            }
//        }
//        return returns;
//    }

    private class DefaultAction implements Action {

        @Override
        public ArrayList<String> getRecognizer() {
            return null;
        }

        @Override
        public boolean isActionFitting(String input) {
            return true;
        }

        @Override
        public String execute() {
            return "Das verstehe ich nicht";
        }
    }
}
