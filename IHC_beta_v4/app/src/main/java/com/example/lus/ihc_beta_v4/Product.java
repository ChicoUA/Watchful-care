package com.example.lus.ihc_beta_v4;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Product extends AppCompatActivity {
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            id = (Integer) extras.get("PRODUCT_ID");
        }

        ImageView product_image = findViewById(R.id.ProductImage);
        TextView product_name = findViewById(R.id.ProductName);
        TextView product_location_time = findViewById(R.id.ProductLocationTime);
        TextView product_price = findViewById(R.id.ProductPrice);
        TextView product_description = findViewById(R.id.ProductDescription);
        TextView product_Owner = findViewById(R.id.ProductOwner);

        try {
            DataInputStream textFileStream = new DataInputStream(getAssets().open(String.format("products.txt")));
            Scanner is = new Scanner(textFileStream);

            while (is.hasNext()) {
                String line = is.nextLine();
                String[] product_info = line.split("_");

                if (Integer.parseInt(product_info[4]) == id) {
                    //(MINI adiçao do luis 123)
                    int imageID = getResources().getIdentifier(product_info[0], "drawable", getPackageName());
                    Log.d("UNO", "o imageID antes é: " + imageID);
                    if (imageID == 0) {  // se por 0 é porque nao encontrou a resource, por isso, há que tratar disso
                        imageID = getResources().getIdentifier("product", "drawable", getPackageName());
                        Log.d("UNO", "o imageID depois é: " + imageID);
                    }
                    //(FIM DA MINI ADIÇAO 123)
                    product_image.setImageResource(imageID);
                    product_name.setText(product_info[1]);
                    product_location_time.setText(product_info[2]);
                    product_price.setText(product_info[3]);
                    product_description.setText(product_info[5]);
                    product_Owner.setText(product_info[6]);

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
}

