package com.example.test_connection;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PatientInfo  extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger("LOGGER");
    private int bpm_id;
    private int temp_id;
    private int id;
    JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            id = (Integer) extras.get("ID");
            bpm_id = (Integer) extras.get("BPM_ID");
            temp_id = (Integer) extras.get("TEMP_ID");
        }

        String url = "http://192.168.160.216:9090/add/lattestdata?id="+id+"&bpm="+bpm_id+"&temperature="+temp_id;
        System.out.println(url);

        new JsonTask().execute(url);


    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                data = parseJson(result);

                TextView name = findViewById(R.id.PatientName);
                TextView age = findViewById(R.id.PatientAge);
                TextView bpm = findViewById(R.id.PatientBPM);
                TextView temperature = findViewById(R.id.PatientTemperature);
                TextView latitude = findViewById(R.id.PatientLatitude);
                TextView longitude = findViewById(R.id.PatientLongitude);

                name.setText(data.getString("firstName")+" "+data.getString("lastName"));
                age.setText("Idade: "+data.getInt("age"));
                bpm.setText("BPM: "+data.getDouble("heartBeat"));
                temperature.setText("Temperatura: "+data.getDouble("temperature"));
                latitude.setText(("Latitude: "+data.getDouble("latitude")));
                longitude.setText("Longitude: "+data.getDouble("longitude"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public JSONObject parseJson(String json) throws JSONException {
            System.out.println(json);

            JSONObject jObj = new JSONObject(json);

            return jObj;
        }

    }
}
