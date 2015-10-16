package de.falkentavio.speechrecognizer.Visualizer;

import android.content.Context;
import android.widget.TextView;

import de.falkentavio.speechrecognizer.CustomFont.CustomFontHelper;

import static de.falkentavio.speechrecognizer.R.drawable.textbox;

/**
 * Created by foellerich on 16.10.2015.
 */
public class TextBoxVisualizer {
    private TextView txtText;
    private Context context;

    public TextBoxVisualizer(TextView textBox, Context applicationContext) {
        txtText = textBox;
        context = applicationContext;
        CustomFontHelper.setCustomFont(txtText, "fonts/8bit.ttf", context);
    }

    public void setText(String text) {
        txtText.setText(text);
        txtText.setBackground(context.getResources().getDrawable(textbox));
    }
}
