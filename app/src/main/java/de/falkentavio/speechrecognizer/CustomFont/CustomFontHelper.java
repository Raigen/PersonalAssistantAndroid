package de.falkentavio.speechrecognizer.CustomFont;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Falke on 14.10.2015.
 */
public class CustomFontHelper {

    /**
     * Sets a font on a textview
     * @param textview
     * @param font
     * @param context
     */
    public static void setCustomFont(TextView textview, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            textview.setTypeface(tf);
        }
    }
}
