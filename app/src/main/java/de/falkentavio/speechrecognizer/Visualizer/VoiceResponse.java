package de.falkentavio.speechrecognizer.Visualizer;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by foellerich on 16.10.2015.
 */
public class VoiceResponse implements TextToSpeech.OnInitListener {
    TextToSpeech engine;

    public VoiceResponse(Context context) {
        engine = new TextToSpeech(context, this);
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
    public void speech(String text) {
        engine.setPitch(1);
        engine.setSpeechRate(1);
        engine.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
