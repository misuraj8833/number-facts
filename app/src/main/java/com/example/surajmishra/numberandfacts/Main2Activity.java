package com.example.surajmishra.numberandfacts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    Spinner spinner;
    String cat;
    String num;
    String mon;
    String dat;
    CoordinatorLayout coordinatorLayout;
    ProgressBar progressBarFact2;
    TextView textViewFact2;
    EditText editTextFact2;
    Button btn_submit;
    EditText editTextMonth;
    EditText editTextdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Number Facts Fun");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //-------------------intializing all the views-------------------------------------------
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressBarFact2 = (ProgressBar) findViewById(R.id.progressBarFact2);
        textViewFact2 = (TextView) findViewById(R.id.textViewFacts2);
        editTextFact2 = (EditText) findViewById(R.id.editTextFact2);
        btn_submit = (Button) findViewById(R.id.buttonSubmitFact2);
        editTextMonth = (EditText) findViewById(R.id.editTextMonth);
        editTextdate = (EditText) findViewById(R.id.editTextDate);
        //--------------------------------------------------------------------------------------


        //-------------------------Checking network connection-----------------------------------------------


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo!=null && networkInfo.isConnectedOrConnecting();

        if(!isConnected){
           Toast.makeText(getBaseContext(),"WARNING! NO INTERNET CONNECTION",Toast.LENGTH_LONG).show();
        }


        //-----------------------------------------------------------------------

        spinner = (Spinner) findViewById(R.id.spinnerFact2);
        ArrayAdapter arrayAdapterFacts = ArrayAdapter.createFromResource(this,R.array.facts_name,android.R.layout.simple_expandable_list_item_1);
        spinner.setAdapter(arrayAdapterFacts);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat  =(String) parent.getItemAtPosition(position);

                if(cat.equals("date")){
                    editTextMonth.setVisibility(EditText.VISIBLE);
                    editTextdate.setVisibility(EditText.VISIBLE);
                    editTextFact2.setVisibility(EditText.INVISIBLE);

                    editTextMonth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            try {
                                if (!hasFocus) {
                                    mon = editTextMonth.getText().toString().trim();

                                    int temp_mon = Integer.parseInt(mon);
                                    if (temp_mon > 12) {
                                        Toast.makeText(getBaseContext(), "Please select the value between 1 to 12", Toast.LENGTH_LONG).show();
                                    }


                                }
                            }catch (Exception E){
                                Toast.makeText(getBaseContext(),"Please Enter the Month",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    editTextdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                        try {
                            if (hasFocus) {
                                dat = editTextdate.getText().toString().trim();

                                int temp_dat = Integer.parseInt(dat);
                                if (temp_dat > 31) {
                                    Toast.makeText(getBaseContext(), "Please select the value between 1 to 31", Toast.LENGTH_LONG).show();
                                }



                            }
                        }
                        catch (Exception E){
                            Toast.makeText(getBaseContext(),"Please Enter the Date",Toast.LENGTH_LONG).show();
                            }
                    }
                    });

                }
                else{
                    editTextMonth.setVisibility(EditText.INVISIBLE);
                    editTextdate.setVisibility(EditText.INVISIBLE);
                }

                if(cat.equals("year")){
                    Log.i("#########",cat);
                    editTextFact2.setVisibility(EditText.VISIBLE);
                    editTextFact2.setHint("Enter the year");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = editTextFact2.getText().toString().trim();
                mon = editTextMonth.getText().toString().trim();
                dat = editTextdate.getText().toString().trim();
                new GetJson().execute(cat,num,mon,dat);


            }
        });

    }



    public class GetJson extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            Log.i("######_2","Entered into background execution");
            progressBarFact2.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("NULL")){
                Toast.makeText(getBaseContext(),"Something's Wrong. Please check your network connection. Or Your input",Toast.LENGTH_LONG).show();
            }
            else {
                Log.i("######_2", "Exiting from background execution");
                progressBarFact2.setVisibility(ProgressBar.INVISIBLE);
                textViewFact2.setVisibility(TextView.VISIBLE);
                textViewFact2.setText(s);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            Log.i("######_2", "Executing  background process");
            String passedCat = params[0];
            String passedNumber = params[1];
            String url;
            HttpHandler httpHandler = new HttpHandler();
            String returnString = "null";

            try {

                if (passedCat.equals("trivia")) {
                    url = "http://numbersapi.com/" + passedNumber + "/" + "" + passedCat + "";
                    Log.i("######_2", url);
                    returnString = httpHandler.makeServiceCall(url);
                    Log.i("######_2", returnString);
                    return returnString;
                }

                if (passedCat.equals("math")) {
                    url = "http://numbersapi.com/" + passedNumber + "/" + "" + passedCat + "";
                    Log.i("######_2", url);
                    returnString = httpHandler.makeServiceCall(url);
                    Log.i("######_2", returnString);
                    return returnString;
                }

                if (passedCat.equals("date")) {
                    String passedMon = params[2];
                    String passedDate = params[3];
                    url = "http://numbersapi.com/" + passedMon + "/" + "/" + passedDate + "/" + "" + passedCat + "";
                    Log.i("######_2", url);
                    returnString = httpHandler.makeServiceCall(url);
                    Log.i("######_2", returnString);
                    return returnString;
                }

                if (passedCat.equals("year")) {
                    url = "http://numbersapi.com/" + passedNumber + "/" + "" + passedCat + "";
                    Log.i("######_2", url);
                    returnString = httpHandler.makeServiceCall(url);
                    Log.i("######_2", returnString);
                    return returnString;
                }



            }
            catch (Exception E){
                Log.i("######","exception occured");
                returnString = "NULL";
                Log.i("######",returnString);
                return returnString.toString();
            }

            return returnString;


        }
    }

}
