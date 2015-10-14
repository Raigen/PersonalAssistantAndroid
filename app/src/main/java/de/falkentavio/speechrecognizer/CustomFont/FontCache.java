package de.falkentavio.speechrecognizer.CustomFont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

/**
 * Created by Falke on 14.10.2015.
 */
public class FontCache {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                Log.d("FontCache", context.getAssets().toString());
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                Log.d("FontCache", e.toString());
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}
