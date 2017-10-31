package com.example.surajmishra.numberandfacts;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    String cat;
    CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //-------------------intializing all the views-------------------------------------------
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textViewResult);
        //--------------------------------------------------------------------------------------

        //--------------------- Checking Network Connection---------------------------------------------------


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (!isConnected) {
            Toast.makeText(getBaseContext(), "WARNING! NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
        }


        //-----------------------------------------------------------------------

        setSupportActionBar(toolbar);
        toolbar.setTitle("Number Facts Fun");

        spinner = (Spinner) findViewById(R.id.spinner_facts);

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.facts_name, android.R.layout.simple_expandable_list_item_1);

        spinner.setAdapter(arrayAdapter);
        // TODO: check for the network connectivity
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat = (String) parent.getItemAtPosition(position);
                new GetJson().execute(cat);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_facts2) {
            startActivity(new Intent(this, Main2Activity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    public class GetJson extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            Log.i("######", "Entered into background execution");
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }


        @Override
        protected void onPostExecute(String s) {
            if(s.equals("NULL")){
                Toast.makeText(getBaseContext(),"Something's Wrong. Please check your network connection. Or Your input",Toast.LENGTH_LONG).show();
            }
            else {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                textView.setVisibility(TextView.VISIBLE);
                textView.setText(s);
                Log.i("######", "Exited from background moode");
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getBaseContext(), "You Missed the fun..Please go back to NumbersAndFun", Toast.LENGTH_SHORT).show();
            Log.i("######", "Exited from background mode");
        }

        @Override
        protected String doInBackground(String... params) {
            String returnString = "null";
            String url;
            String passedCat;
            try {
                HttpHandler httpHandler = new HttpHandler();
                Log.i("######", "Entered into doInBackground");
                passedCat = params[0];
                url = "http://numbersapi.com/random/" + passedCat + "";
                Log.i("####", url);
                returnString = httpHandler.makeServiceCall(url);
                Log.i("####", returnString);
            }
            catch (Exception e){
                Log.i("######","exception occured");
                returnString = "NULL";
                Log.i("######",returnString);
                return returnString.toString();
            }
            return returnString.toString();
        }
    }
}

