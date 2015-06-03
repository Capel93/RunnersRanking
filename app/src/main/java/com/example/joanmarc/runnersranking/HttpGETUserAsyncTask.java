package com.example.joanmarc.runnersranking;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
public class HttpGETUserAsyncTask extends AsyncTask<String, Void, String> {


    private Context context;
    private static JSONObject jObj;

    public HttpGETUserAsyncTask(Context context) {

        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        return GET(urls[0]);
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        JSONArray dataJsonArr = null;

        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpGet httpGet = new HttpGet(url);

            String json ="";

            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpGet);

            Log.d("Hello: ", httpResponse.toString());

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null){
                result = convertInputStreamToString(inputStream);
                jObj = new JSONObject(json);
                jObj.getJSONArray("Users");
                for (int i = 0; i < dataJsonArr.length(); i++) {

                    JSONObject c = dataJsonArr.getJSONObject(i);

                    // Storing each json item in variable
                    String name = c.getString("name");
                    String userName = c.getString("userName");
                    String email = c.getString("email");
                    String password = c.getString("password");

                    // show the values in our logcat
                    Log.e("TAG", "name: " + name
                            + ", userName: " + userName
                            + ", email: " + email
                            + ", password: " + password);

                }
            }else
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

