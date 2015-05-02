package com.example.joanmarc.runnersranking;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.joanmarc.myapplication.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by JoanMarc on 29/04/2015.
 */
public class GcmValidationAsyncTask extends AsyncTask<Void, Void, String> {


    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private String email;
    private String password;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "564533837615";

    @Override
    protected String doInBackground(Void... params) {

        Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://probable-analog-92915.appspot.com/_ah/api/");
        // end of optional local run code

        regService = builder.build();

        

        return null;
    }

    @Override
    protected void onPostExecute(String msg) {

        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}
