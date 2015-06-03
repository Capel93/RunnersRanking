package com.example.joanmarc.runnersranking;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by joanmarc on 27/05/15.
 */
public class HttpDELETEUserAsyncTask extends AsyncTask<String, Void, String> {

    private User user;
    private Context context;

    public HttpDELETEUserAsyncTask(User user,Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        return DELETE(urls[0], user);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

    }

    public static String DELETE(String url, User user){
        InputStream inputStream = null;
        String result = "";
        JSONArray dataJsonArr = null;

        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();


            HttpDelete httpDelete = new HttpDelete(url);



            HttpResponse httpResponse = httpclient.execute(httpDelete);

            Log.d("Hello: ",httpResponse.toString());

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }




}


