package com.example.surajmishra.numberandfacts;

import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Suraj Mishra on 29-10-2017.
 */
// TODO: REPLACE THIS STACK TRACE WITH THE TOAST
public class HttpHandler {
    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl){
        String response=null;
        try{
            // this is the url passed from the calling method
            URL url = new URL(reqUrl);
            // http url connection is used to connect to the given url in the above stage
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //this sets the request method
            httpURLConnection.setRequestMethod("GET");
            // this gets the returned input stream from the api
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            response = convertStreamToString(inputStream);





        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try{
            while ((line = bufferedReader.readLine())!= null){
                stringBuilder.append(line).append('\n');

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();

    }
}
