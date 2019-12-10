package com.example.lus.ihc_beta_v4;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.DataInputStream;
import java.util.logging.Logger;
import java.util.Scanner;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private static final Logger LOGGER = Logger.getLogger("LOGGER");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout mainLayout = findViewById(R.id.parent_linear_layout);
        LayoutInflater inflater = getLayoutInflater();

        //BEGIN(4) - Para a Imagem de Perfil também ser Clicavel / @ https://stackoverflow.com/questions/31716034/onclick-for-navigation-drawer-header-not-working
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        ImageView profilepic = (ImageView) headerview.findViewById(R.id.profile_pic);

        /* So a titulo de exempo, para no futuro servir como forma de por o Nome dinamicamente...*/
        TextView profilename = (TextView) headerview.findViewById(R.id.user_name);
        profilename.setText("your name");
        //END(4)

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPerfil();
            }
        });


        //para o side menu(1)
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //(1) fim

        //(2)
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(nvDrawer);
        //fim de (2)

        //Cenas do chico
        try{
            DataInputStream textFileStream = new DataInputStream(getAssets().open(String.format("products.txt")));
            LOGGER.info("Logger Name: "+LOGGER.getName());
            Scanner is = new Scanner(textFileStream);
            int index = 0;

            while(is.hasNext()){
                String line = is.nextLine();
                String[] product_info = line.split("_");
                LOGGER.warning("Reading file lines: "+line);
                View myLayout = inflater.inflate(R.layout.list_elements, mainLayout, false);

                RelativeLayout product_item = myLayout.findViewById(R.id.product_item);
                ImageView product_image = myLayout.findViewById(R.id.product_image);
                TextView product_name = myLayout.findViewById(R.id.product_name);
                TextView product_location_time = myLayout.findViewById(R.id.product_location_time);
                TextView product_price = myLayout.findViewById(R.id.product_price);

                //(MINI adiçao do luis 123)
                int imageID = getResources().getIdentifier(product_info[0], "drawable",getPackageName());
                Log.d("UNO", "o imageID antes é: " + imageID);
                if (imageID == 0){  // se por 0 é porque nao encontrou a resource, por isso, há que tratar disso
                    imageID = getResources().getIdentifier("product", "drawable",getPackageName());
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
        catch(IOException e){
            e.printStackTrace();
        }
        //fim das cenas do chico
    }
	
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
        searchView.setMaxWidth((int)(width/1.7));   // para ocupar 1 tamanho razoavel e dinamico

//        View searchplate = (View) searchView.findViewById(R.id.searchBar);        #nao da
//        searchplate.getBackground().setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);



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
	
    //para o (1)
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
//        Log.d("UNO", "ola meu lol");
        return super.onOptionsItemSelected(item);
    }

    //para darem os clicks no menu (2)
    public void selectItemDrawer(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.activities:
                Log.d("UNO", "[MAIN] PERFIL Selecionado");
                goToPerfil();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.db:
                Log.d("UNO","[MAIN] VENDER Selecionado");
                goToVender();
                mDrawerLayout.closeDrawers();
                break;
            default:
                Log.d("UNO", "[MAIN] DEFAULT Selecionado");
                mDrawerLayout.closeDrawers();       //assim so fecha quando uma das opçoes é clicada, e nao quando se clica num sitio qualquer do cenas
        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem);
                return true;
            }
        });
    }



    public void goToPerfil(){
        Log.d("ENTRAR", "entrei nesta merda");
        Toast.makeText(MainActivity.this, "This is my Toast message!",
                Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, Perfil.class);
        startActivity(myIntent);
    }

    public void goToVender(){
        Log.d("ENTRAR", "entrei nesta merda -VENDER-");
        Toast.makeText(MainActivity.this, "This is my VENDER message!",
                Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, VenderActivity.class);
        startActivity(myIntent);
    }

    public void goToProduct(View view){
        Log.d("ENTRAR", "entrei na página do produto");
        Toast.makeText(MainActivity.this, "This is my PRODUCT message!",
                Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, Product.class);
        int id = view.getId();
        myIntent.putExtra("PRODUCT_ID", id);

        startActivity(myIntent);
    }
	
	//Used to go the activity with the results from the search
    public void getInput(String searchText)
    {
        Intent in = new Intent(this, searchPage.class);
        in.putExtra("TEXT", searchText);
        startActivity(in);
    }


}
