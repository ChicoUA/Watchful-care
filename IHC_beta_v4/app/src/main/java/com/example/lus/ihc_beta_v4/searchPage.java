package com.example.lus.ihc_beta_v4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class searchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        LinearLayout mainLayout = findViewById(R.id.parent_linear_layout);
        LayoutInflater inflater = getLayoutInflater();
        String searchText = getIntent().getStringExtra("TEXT");


        //Cenas do chico
        try{
            DataInputStream textFileStream = new DataInputStream(getAssets().open(String.format("products.txt")));
            Scanner is = new Scanner(textFileStream);
            int index = 0;

            while(is.hasNext()){
                String line = is.nextLine();
                String[] product_info = line.split("_");
                if(product_info[1].toLowerCase().contains(searchText.toLowerCase())) {//if product name contains string that was search then show product
                    View myLayout = inflater.inflate(R.layout.list_elements, mainLayout, false);

                    RelativeLayout product_item = myLayout.findViewById(R.id.product_item);
                    ImageView product_image = myLayout.findViewById(R.id.product_image);
                    TextView product_name = myLayout.findViewById(R.id.product_name);
                    TextView product_location_time = myLayout.findViewById(R.id.product_location_time);
                    TextView product_price = myLayout.findViewById(R.id.product_price);

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
                    product_item.setId(Integer.parseInt(product_info[4]));

                    mainLayout.addView(myLayout);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //fim das cenas do chico
    }

    public void goToProduct(View view){
        Log.d("ENTRAR", "entrei na página do produto");
        Toast.makeText(searchPage.this, "This is my PRODUCT message!",
                Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, Product.class);
        int id = view.getId();
        myIntent.putExtra("PRODUCT_ID", id);

        startActivity(myIntent);
    }

}
