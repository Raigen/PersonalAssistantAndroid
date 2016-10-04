package de.falkentavio.speechrecognizer.Actions;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by foellerich on 04.10.2016.
 */
public class StadtradAction implements Action {

    private static final String TAG = "StadtradAction";
    private static final String BASE_URL = "https://www.callabike-interaktiv.de/kundenbuchung/hal2ajax_process.php?callee=getMarker&mapstadt_id=75&requester=bikesuche&ajxmod=hal2map&bereich=2&buchungsanfrage=N&webfirma_id=500&searchmode=default";
    private JSONArray stations;
    private OkHttpClient httpClient;

    @NonNull
    private String runFetch() throws IOException {
        Request request = new Request.Builder()
                .url(this.BASE_URL)
                .build();
        Response response = this.httpClient.newCall(request).execute();
        return response.body().string();
    }

    private void fetchStations() throws IOException, JSONException {
        String result = this.runFetch();
        JSONObject resultObject = new JSONObject(result);
        this.stations = resultObject.getJSONArray("marker");
    }

    @SuppressLint("DefaultLocale")
    private Boolean hasFreeBikes(JSONObject station) throws JSONException {
        JSONObject hal2option = station.getJSONObject("hal2option");
        JSONArray bikes = hal2option.getJSONArray("bikelist");
        if (bikes.length() > 0) {
            Log.d(TAG, String.format("%d bikes found", bikes.length()));
            return true;
        }
        Log.d(TAG, "no bikes found");
        return false;
    }

    private void t() throws JSONException {
        for(int i = 0; i < this.stations.length(); i++) {
            JSONObject station = this.stations.getJSONObject(i);
            hasFreeBikes(station);
        }
    }

    public StadtradAction() {
        this.httpClient = new OkHttpClient();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public ArrayList<String> getRecognizer() {
        return new ArrayList<>(Arrays.asList(
                "Fahrrad",
                "Bike",
                "Rad",
                "nächste"
        ));
    }

    @Override
    public boolean isActionFitting(String input) {
        return true;
    }

    @Override
    public String execute() {
        try {
            try {
                this.fetchStations();
                this.t();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
