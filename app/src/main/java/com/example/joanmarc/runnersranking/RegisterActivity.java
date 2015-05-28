package com.example.joanmarc.runnersranking;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joanmarc.myapplication.backend.registration.Registration;
import com.example.joanmarc.myapplication.backend.routeApi.RouteApi;
import com.example.joanmarc.myapplication.backend.usersApi.UsersApi;
import com.example.joanmarc.myapplication.backend.usersApi.model.Users;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class RegisterActivity extends ActionBarActivity {

    private EditText user;
    private EditText email;
    private EditText pass1;
    private EditText pass2;

    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = (EditText) findViewById(R.id.user);
        email = (EditText) findViewById(R.id.email);
        pass1 = (EditText) findViewById(R.id.password1);
        pass2 = (EditText) findViewById(R.id.password2);

        context = this;

        Button save = (Button) findViewById(R.id.save);
        Button cancel = (Button) findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pass1.getText().toString().equals(pass2.getText().toString())){
                    User u = new User(user.getText().toString());
                    u.setName("");
                    u.setEmail(email.getText().toString());
                    u.setPassword(pass1.getText().toString());
                    new HttpPOSTUserAsyncTask(u,context).execute("http://192.168.1.34:3000/users");

                    //new RegisterAsyncTask(context,user.getText().toString(),email.getText().toString(),pass1.getText().toString()).execute();
                }else{
                    Toast.makeText(context,"Passwords are different",Toast.LENGTH_LONG);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class RegisterAsyncTask extends AsyncTask<Void,Void,Boolean>{

        private String user;
        private String email;
        private String password;

        private UsersApi regUser = null;
        private GoogleCloudMessaging gcm;
        private Context context;

        // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
        private static final String SENDER_ID = "564533837615";

        private RegisterAsyncTask(Context context, String user, String email, String password) {
            this.context = context;
            this.user = user;
            this.email = email;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            if (regUser==null){
                UsersApi.Builder builder = new UsersApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://probable-analog-92915.appspot.com/_ah/api/");
                regUser = builder.build();
            }

            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                String regId = gcm.register(SENDER_ID);
                msg = "Device registered, registration ID=" + regId;


                Users users = new Users();
                users.setRegId(regId);
                users.setUserName(user);
                users.setEmail(email);
                users.setPassword(password);




                if(regUser.insertUsers(users).execute()==null){
                    return false;
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                msg = "Error: " + ex.getMessage();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean){
                Toast.makeText(context,"User created succesfully",Toast.LENGTH_LONG);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();

            }else{
                Toast.makeText(context,"User with same UserName",Toast.LENGTH_LONG);
            }


        }
    }

    public class HttpPOSTUserAsyncTask extends AsyncTask<String, Void, String> {

        private User user;
        private Context context;

        public HttpPOSTUserAsyncTask(User user,Context context) {
            this.user = user;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0],user);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            finish();

        }

        public String POST(String url, User person){
            InputStream inputStream = null;
            String result = "";
            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(url);

                String json = "";

                // 3. build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", person.getName());
                jsonObject.accumulate("userName", person.getUserName());
                jsonObject.accumulate("email",person.getEmail());
                jsonObject.accumulate("password",person.getPassword());
                jsonObject.accumulate("friends", person.getFriends());
                jsonObject.accumulate("routes", person.getRoutes());


                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();

                // ** Alternative way to convert Person object to JSON string usin Jackson Lib
                // ObjectMapper mapper = new ObjectMapper();
                // json = mapper.writeValueAsString(person);

                // 5. set json to StringEntity
                StringEntity se = new StringEntity(json);

                // 6. set httpPost Entity
                httpPost.setEntity(se);

                // 7. Set some headers to inform server about the type of the content
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);

                Log.d("Hello: ", httpResponse.toString());

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


        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }




    }
}
