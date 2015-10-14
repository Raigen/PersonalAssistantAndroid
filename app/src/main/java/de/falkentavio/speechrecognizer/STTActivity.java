package de.falkentavio.speechrecognizer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
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

/**
 * Created by foellerich on 13.10.2015.
 */
public class STTActivity extends Activity implements TextToSpeech.OnInitListener {
    protected static final int RESULT_SPEECH = 1;
    private ImageButton btnSpeak;
    private TextView txtText;
    TextToSpeech engine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_activity);

        engine = new TextToSpeech(this, this);
        txtText = (TextView) findViewById(R.id.txtText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

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
                    txtText.setText(input);
                    if (contains(input, new ArrayList<String>(Arrays.asList("spät", "wie", "es", "ist")))) {
                        String date = new SimpleDateFormat("HH:mm").format(new Date());
                        txtText.setText(input + "\n" + date);
                        speech("Es ist " + date + " Uhr");
                    }
                }
                break;
            }
        }
    }

    private boolean contains(String input, ArrayList<String> needles) {
        boolean returns = true;
        for (String needle : needles) {
            if (!input.contains(needle)) {
                returns = false;
            }
        }
        return returns;
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
//            engine.setPitch(1);
//            engine.setSpeechRate(1);
        engine.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
