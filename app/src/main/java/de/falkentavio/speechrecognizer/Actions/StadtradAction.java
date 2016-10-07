package de.falkentavio.speechrecognizer.Actions;

import android.annotation.SuppressLint;
import android.location.Location;
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
 *
 */
class StadtradAction implements Action {

    private static final String TAG = "StadtradAction";
    private static final String BASE_URL = "https://www.callabike-interaktiv.de/kundenbuchung/hal2ajax_process.php?callee=getMarker&mapstadt_id=75&requester=bikesuche&ajxmod=hal2map&bereich=2&buchungsanfrage=N&webfirma_id=500&searchmode=default";
    private JSONArray stations;
    private OkHttpClient httpClient;

    @NonNull
    private String runFetch() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();
        Response response = this.httpClient.newCall(request).execute();
        return response.body().string();
    }

    private void fetchStations() throws IOException, JSONException {
        String result = this.runFetch();
        JSONObject resultObject = new JSONObject(result);
        this.stations = resultObject.getJSONArray("marker");
    }

    @NonNull
    @SuppressLint("DefaultLocale")
    private Integer getFreeBikes(JSONObject station) throws JSONException {
        JSONObject hal2option = station.getJSONObject("hal2option");
        JSONArray bikes = hal2option.getJSONArray("bikelist");
        if (bikes.length() > 0) {
//            Log.d(TAG, String.format("%d bikes found", bikes.length()));
            return bikes.length();
        }
//        Log.d(TAG, "no bikes found");
        return 0;
    }

    private float distanceToStation(JSONObject station) throws JSONException {
        Location stationLocation = new Location("station");
        stationLocation.setLatitude(station.getDouble("lat"));
        stationLocation.setLongitude(station.getDouble("lng"));
        Location myLocation = new Location("self");
        myLocation.setLatitude(53.5542332);
        myLocation.setLongitude(9.9786355);
        return myLocation.distanceTo(stationLocation);
    }

    private static String parseStationName(String source) {
        return source.replace("&nbsp;", " ").replaceFirst("\\d{4,}\\s", "");
    }

    private String t() throws JSONException {
        ArrayList<JSONObject> nearbyStations = new ArrayList<>();
        String nearestStation = "Keine Räder in der Nähe gefunden";
        float nearestDistance = 10000;
        for(int i = 0; i < this.stations.length(); i++) {
            JSONObject station = this.stations.getJSONObject(i);
            JSONObject hal2option = station.getJSONObject("hal2option");
            String stationName = parseStationName(hal2option.getString("tooltip"));
            Integer freeBikes = getFreeBikes(station);
            float distance = distanceToStation(station);
//            Log.d(TAG, "Distance: " + distance);
            if (distance < 800) {
                if (freeBikes > 0) {
                    Log.d(TAG, stationName + " hat " + freeBikes + " freie Bikes");
                    nearbyStations.add(station);
                    if (distance < nearestDistance) {
                        nearestStation = stationName + " hat " + freeBikes + " freie Bikes";
                        nearestDistance = distance;
                    }
                } else {
                    Log.d(TAG, stationName + " hat keine freien Bikes");
                }
            }
        }
        Log.d(TAG, "Nearby stations: " + nearbyStations.size());
        return nearestStation;
    }

    StadtradAction() {
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
                "StadtRad",
                "citiybike",
                "Call A Bike",
                "CallABike"
        ));
    }

    @Override
    public boolean isActionFitting(String input) {
        boolean isFitting = false;
        for (String s : getRecognizer()) {
            if (input.toLowerCase().contains(s.toLowerCase())) {
                isFitting = true;
                break;
            }
        }
        return isFitting;
    }

    @Override
    public String execute() {
        try {
            try {
                this.fetchStations();
                return this.t();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
