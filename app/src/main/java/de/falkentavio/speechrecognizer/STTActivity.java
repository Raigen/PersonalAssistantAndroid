package de.falkentavio.speechrecognizer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import de.falkentavio.speechrecognizer.Actions.ActionManager;
import de.falkentavio.speechrecognizer.CustomFont.CustomFontHelper;
import de.falkentavio.speechrecognizer.Visualizer.TextBoxVisualizer;
import de.falkentavio.speechrecognizer.Visualizer.VoiceResponse;

import static de.falkentavio.speechrecognizer.R.drawable.textbox;

/**
 * Created by foellerich on 13.10.2015.
 */
public class STTActivity extends Activity {
    protected static final int RESULT_SPEECH = 1;
    private TextView txtText;
    private TextView debugTxt;
    private TextBoxVisualizer textBoxVisualizer;
    private VoiceResponse voice;
    private ActionManager actionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ImageButton btnSpeak;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_activity);

        debugTxt = (TextView) findViewById(R.id.debugTxt);
        txtText = (TextView) findViewById(R.id.txtText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        voice = new VoiceResponse(getApplicationContext());
        textBoxVisualizer = new TextBoxVisualizer(txtText, getApplicationContext());
        actionManager = new ActionManager();

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(), "Speech not supported", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String input = text.get(0);
                    String output = actionManager.execute(input);
                    debugTxt.setText(input);
                    textBoxVisualizer.setText(output);
                    voice.speech(output);
                }
                break;
            }
        }
    }
}
