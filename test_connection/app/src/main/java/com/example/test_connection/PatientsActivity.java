package com.example.test_connection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class PatientsActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger("LOGGER");
    ArrayList<Patient> patients= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);


        new JsonTask().execute("http://192.168.160.216:9090/add/patients");



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
                patients = parseJson(result);
                LinearLayout mainLayout = findViewById(R.id.parent_linear_layout);
                LayoutInflater inflater = getLayoutInflater();
                try{

                    LOGGER.info("Logger Name: "+LOGGER.getName());

                    for(Patient p : patients){
                        LOGGER.warning("Patient: "+p);
                        View myLayout = inflater.inflate(R.layout.list_elements, mainLayout, false);

                        RelativeLayout product_item = myLayout.findViewById(R.id.product_item);
                        ImageView product_image = myLayout.findViewById(R.id.product_image);
                        TextView product_name = myLayout.findViewById(R.id.product_name);
                        TextView product_location_time = myLayout.findViewById(R.id.product_location_time);

                        product_image.setImageResource( getResources().getIdentifier("person", "drawable",getPackageName()));
                        product_name.setText(p.getFirstName() + " " + p.getLastName());
                        product_location_time.setText("Idade: "+p.getAge());
                        product_item.setId(p.getBpm_id());


                        mainLayout.addView(myLayout);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<Patient> parseJson(String json) throws JSONException {
            System.out.println(json);

            JSONArray jArr = new JSONArray(json);
            ArrayList<Patient> patients = new ArrayList<>();

            for (int count = 0; count < jArr.length(); count++) {
                JSONObject obj = jArr.getJSONObject(count);
                int bpm_id= obj.getInt("bpm_id");
                int temp_id= obj.getInt("temp_id");
                int age= obj.getInt("age");
                String firstName= obj.getString("firstName");
                String lastName = obj.getString("lastName");
                Patient p = new Patient(firstName, lastName, age, bpm_id, temp_id);
                System.out.println(p);
                patients.add(p);
            }
            System.out.println(patients);
            return patients;
        }

    }

    public void goToPatientInfo(View view){
        Log.d("ENTRAR", "entrei na página do produto");
        Toast.makeText(PatientsActivity.this, "This is my PATIENT message!",
                Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, PatientInfo.class);
        int id = view.getId();
        myIntent.putExtra("PRODUCT_ID", id);

        startActivity(myIntent);
    }
}
