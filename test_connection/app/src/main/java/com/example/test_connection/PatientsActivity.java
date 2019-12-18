package com.example.test_connection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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
    /*
    //Used for SearchBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);
        MenuItem item = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) item.getActionView();

        //luissss, para a barra ficar sempre expandida
        searchView.setIconifiedByDefault(false);
        searchView.setBackgroundColor(0xFFffd595);  //os 2 1os FF sao a transparencia
        searchView.setQueryHint("Pesquisar...");


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        searchView.setMaxWidth((int) (width / 1.7));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getInput(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    */

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

                        RelativeLayout product_item = myLayout.findViewById(R.id.paciente);

                        TextView product_name = myLayout.findViewById(R.id.name);
                        TextView product_location_time = myLayout.findViewById(R.id.idade);


                        product_name.setText(p.getFirstName() + " " + p.getLastName());
                        product_location_time.setText("Idade: "+p.getAge());
                        product_item.setId(p.getId());
                        ArrayList<Integer> ids = new ArrayList<>();
                        ids.add(p.getBpm_id());
                        ids.add(p.getTemp_id());
                        product_item.setTag(ids);


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
                int id = obj.getInt("id");
                int bpm_id= obj.getInt("bpm_id");
                int temp_id= obj.getInt("temp_id");
                int age= obj.getInt("age");
                String firstName= obj.getString("firstName");
                String lastName = obj.getString("lastName");
                Patient p = new Patient(firstName, lastName, age, bpm_id, temp_id, id);
                System.out.println(p);
                patients.add(p);
            }
            System.out.println(patients);
            return patients;
        }

    }

    public void goToPatientInfo(View view){
        Log.d("ENTRAR", "entrei na página do produto");

        Intent myIntent = new Intent(this, PatientInfo.class);
        int id = view.getId();
        myIntent.putExtra("ID", id);
        ArrayList<Integer> ids = (ArrayList<Integer>) view.getTag();
        myIntent.putExtra("TEMP_ID", ids.get(1));
        myIntent.putExtra("BPM_ID", ids.get(0));

        startActivity(myIntent);
    }
    /*
    //Used to go the activity with the results from the search
    public void getInput(String searchText)
    {
        Intent in = new Intent(this, searchPage.class);
        in.putExtra("TEXT", searchText);
        startActivity(in);
    }
    */
}
