package com.example.test_connection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnHit;
    TextView txtJson;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void goToPatients(View view){
        Log.d("ENTRAR", "entrei em patients");
        Toast.makeText(MainActivity.this, "This is my Toast message!",
                Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, PatientsActivity.class);
        startActivity(myIntent);
    }


}
