package com.example.test_connection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {


    TextView txtJson;
    ProgressDialog pd;
    private FirebaseAnalytics mFirebaseAnalytics;
    private NotificationCompat.Builder notification_builder;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("common").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        });

        _emailText = findViewById(R.id.editText);
        _passwordText = findViewById(R.id.editText2);
        _loginButton = findViewById(R.id.btnLogin);


    }

    public void goToPatients(View view){

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Falha na autenticação", Toast.LENGTH_LONG).show();

            _loginButton.setEnabled(true);

        }
        else {
            Log.d("ENTRAR", "entrei em patients");
            Toast.makeText(MainActivity.this, "Autenticado como médico/funcionário!",
                    Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(this, PatientsActivity.class);
            startActivity(myIntent);
        }
    }


    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Digite um e-mail válido!");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Digite uma password válida!");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void goToHelpPatient(View view){

        Toast.makeText(MainActivity.this, "Autenticado como paciente",
                Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, HelpPatient.class);
        startActivity(myIntent);
    }


}
