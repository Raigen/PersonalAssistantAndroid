package de.falkentavio.speechrecognizer.VoiceRecognizer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import de.falkentavio.speechrecognizer.R;

/**
 * Created by foellerich on 16.10.2015.
 */
public class VoiceActivity extends Activity implements TextToSpeech.OnInitListener {
    protected static final int RESULT_SPEECH = 1;
    private ImageButton btnSpeak;
    private TextView debugTxt;
    TextToSpeech engine;

    public void onCreate() {
        engine = new TextToSpeech(this, this);
        debugTxt = (TextView) findViewById(R.id.debugTxt);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(), "Speech not supported", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }
    @Override
    public void onInit(int status) {
        Log.d("Speech", "OnInit - Status [" + status + "]");
        if (status == TextToSpeech.SUCCESS) {
            Log.d("Speech", "Success!");
            engine.setLanguage(Locale.GERMAN);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void speech(String text) {
        engine.setPitch(1);
//            engine.setSpeechRate(1);
        engine.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
