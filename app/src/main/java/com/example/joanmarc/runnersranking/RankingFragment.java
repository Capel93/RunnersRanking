package com.example.joanmarc.runnersranking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.joanmarc.myapplication.backend.routeApi.RouteApi;
import com.example.joanmarc.myapplication.backend.routeApi.model.CollectionResponseRoute;
import com.example.joanmarc.myapplication.backend.routeApi.model.Route;
import com.example.joanmarc.myapplication.backend.usersApi.UsersApi;
import com.example.joanmarc.myapplication.backend.usersApi.model.Users;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.joanmarc.runnersranking.RankingFragment.OnRankingFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner filter;
    ArrayList<Route> Friendslist;
    ArrayList<Route> Globallist;

    private List<String> items_Globallist;
    private List<String> items_Friendslist;
    private View rootView;
    private ListView list_global;
    private ListView list_friends;


    private OnRankingFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_ranking, container, false);

        //new ListRoutesAsyncTask(getActivity()).execute();
        //new ListFriendRoutesAsyncTask(getActivity()).execute();
        new HttpGETRoutesAsyncTask(getActivity()).execute("http://192.168.1.34:3000/routes/");
        SharedPreferences userDetails = getActivity().getSharedPreferences("userdetails", getActivity().MODE_PRIVATE);

        new HttpGETFriendsRoutesAsyncTask(getActivity()).execute("http://192.168.1.34:3000/friends/"+userDetails.getString("email",""));

        //Spinners
        ArrayList spinner_array = new ArrayList<String>();
        spinner_array.add("Distance");
        spinner_array.add("Rate");
        spinner_array.add("Time");
         filter = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.select_filter,R.layout.spinner_layout);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        filter.setAdapter(arrayAdapter);

        Button buttonFilter = (Button) rootView.findViewById(R.id.buttonFilter);
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (filter.getSelectedItem().toString()){
                    case "Distance":
                        Collections.sort(Friendslist, new Comparator<Route>() {
                            @Override
                            public int compare(Route lhs, Route rhs) {
                                return lhs.getDistance().compareTo(rhs.getDistance());
                            }
                        }
                        );
                        Collections.sort(Globallist, new Comparator<Route>() {
                                    @Override
                                    public int compare(Route lhs, Route rhs) {
                                        return lhs.getDistance().compareTo(rhs.getDistance());
                                    }
                                }
                        );
                        break;
                    case "Rate":
                        /*Collections.sort(Friendslist, new Comparator<Route>() {
                                    @Override
                                    public int compare(Route lhs, Route rhs) {
                                        return lhs.getDistance().compareTo(rhs.getDistance());
                                    }
                                }
                        );
                        Collections.sort(Globallist, new Comparator<Route>() {
                                    @Override
                                    public int compare(Route lhs, Route rhs) {
                                        return lhs.getDistance().compareTo(rhs.getDistance());
                                    }
                                }
                        );*/
                        break;
                    case "Time":
                        Collections.sort(Friendslist, new Comparator<Route>() {
                                    @Override
                                    public int compare(Route lhs, Route rhs) {
                                        return lhs.getTime().compareTo(rhs.getTime());
                                    }
                                }
                        );
                        Collections.sort(Globallist, new Comparator<Route>() {
                                    @Override
                                    public int compare(Route lhs, Route rhs) {
                                        return lhs.getTime().compareTo(rhs.getTime());
                                    }
                                }
                        );
                        break;
                }
            }
        });



        //ArrayAdapter array_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, spinner_array);
        //array_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);



        //TabHost
        TabHost tabHost = (TabHost) rootView.findViewById(R.id.tabHostRank);
        list_global = (ListView) rootView.findViewById(R.id.listViewGlobal);
        list_friends = (ListView) rootView.findViewById(R.id.listViewFriends);
        tabHost.setup();



        TabHost.TabSpec global, friends;

        friends = tabHost.newTabSpec("Friends");
        friends.setContent(R.id.friends);
        friends.setIndicator("friends");
        tabHost.addTab(friends);

        global = tabHost.newTabSpec("global");
        global.setContent(R.id.global);
        global.setIndicator("global");
        tabHost.addTab(global);

        //ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items_Globallist);
        //list_global.setAdapter(adapter);



        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onRankingFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRankingFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRankingFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onRankingFragmentInteraction(Uri uri);
    }

    public class ItemAdapter extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater=null;
        private ArrayList<Route> routes;

        public ItemAdapter(Activity a,ArrayList<Route> routes) {
            activity = a;
            this.routes=routes;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public int getCount() {
            return routes.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View vi=convertView;
            if(convertView==null)
                vi = inflater.inflate(R.layout.item_ranking, null);

            TextView title = (TextView)vi.findViewById(R.id.title); // title
            TextView date = (TextView)vi.findViewById(R.id.date); // date route
            TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
            ImageButton profile_image=(ImageButton)vi.findViewById(R.id.list_image); // profile image
            Route r = routes.get(position);
            date.setText(r.getDate().toString());

            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(),DetailRouteActivity.class);
                    startActivity(i);
                }
            });

            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return vi;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        @Override
        public boolean isEnabled(int arg0)
        {
            return true;
        }
    }

    public class ListRoutesAsyncTask extends AsyncTask<Void, Void, List<Route>> {

        private RouteApi regRoute = null;
        private GoogleCloudMessaging gcm;
        private Context context;

        private static final String SENDER_ID = "564533837615";

        @Override
        protected List<Route> doInBackground(Void... params) {
            if (regRoute==null){
                RouteApi.Builder builder = new RouteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://probable-analog-92915.appspot.com/_ah/api/");
                regRoute = builder.build();
            }

            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                String regId = gcm.register(SENDER_ID);
                msg = "Device registered, registration ID=" + regId;





                //return regUser.listUserRoutes(10,regId).execute().getItems();





                return  regRoute.listRoutes(10,"dev").execute().getItems();






            } catch (IOException ex) {
                ex.printStackTrace();
                msg = "Error: " + ex.getMessage();

            }

            return null;
        }

        public ListRoutesAsyncTask(Context context) {
            this.context=context;
        }

        @Override
        protected void onPostExecute(List<Route> rts) {


            if (rts==null){
                 Globallist = new ArrayList<>();
            }else {
                Globallist = new ArrayList<>(rts);

            }
            list_global.setAdapter(new ItemAdapter(getActivity(),Globallist));

            list_global.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });


        }
    }

    public class ListFriendRoutesAsyncTask extends AsyncTask<Void, Void, List<Route>> {

        private RouteApi regRoute = null;
        private GoogleCloudMessaging gcm;
        private Context context;
        private UsersApi regUser = null;

        private static final String SENDER_ID = "564533837615";

        @Override
        protected List<Route> doInBackground(Void... params) {
            if (regRoute==null){
                RouteApi.Builder builder = new RouteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://probable-analog-92915.appspot.com/_ah/api/");
                regRoute = builder.build();
            }
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


                SharedPreferences userDetails = context.getSharedPreferences("userdetails", context.MODE_PRIVATE);


                //return regUser.listUserRoutes(10,regId).execute().getItems();
                String userName;
                if (userDetails.contains("userName")){
                    userName = userDetails.getString("userName","");
                }else{
                    userName  ="dev";
                }

                Users user = regUser.getUsers(userDetails.getString("userName","")).execute();
                List<Route> allFriendsRoutes = new ArrayList<>();
                if(user.getFriends()!=null){
                    for (String friendUserName:user.getFriends()){
                        List<Route> friendRoutes = regRoute.listRouteByUser(20, userName).execute().getItems();
                        if (friendRoutes!=null){
                            allFriendsRoutes.addAll(friendRoutes);
                        }
                    }
                }



                return  allFriendsRoutes;






            } catch (IOException ex) {
                ex.printStackTrace();
                msg = "Error: " + ex.getMessage();

            }

            return null;
        }

        public ListFriendRoutesAsyncTask(Context context) {
            this.context=context;
        }

        @Override
        protected void onPostExecute(List<Route> rts) {


            if (rts==null){
                Friendslist = new ArrayList<>();
            }else {
                Friendslist = new ArrayList<>(rts);


            }
            list_friends.setAdapter(new ItemAdapter(getActivity(),Friendslist));


            list_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
    }

    public class HttpGETRoutesAsyncTask extends AsyncTask<String, Void, ArrayList<Route>> {


        private Context context;
        private JSONObject jObj;
        private String msg;

        private String email;
        private String mPassword;
        public HttpGETRoutesAsyncTask(Context context) {

            this.context = context;
            //this.mPassword = password;
            //this.email = email;
        }

        @Override
        protected ArrayList<Route> doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<Route> routes) {

            if (routes==null){
                Globallist = new ArrayList<>();
            }else {
                Globallist = routes;

            }
            list_global.setAdapter(new ItemAdapter(getActivity(),Globallist));

            list_global.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

        }

        @Override
        protected void onCancelled() {}


        public ArrayList<Route> GET(String url){
            InputStream inputStream = null;
            String result = "";
            JSONArray dataJsonArr = null;

            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpGet httpGet = new HttpGet(url);

                String json = "";


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
                    JSONArray array = new JSONArray(result);
                    ArrayList<Route> routes = new ArrayList();

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject obj = array.getJSONObject(i);

                        Route route = new Route();
                        route.setDistance(new Double(obj.getInt("distance")));
                        route.setTime(new Long(obj.getInt("time")));

                        ArrayList<Double> lat = new ArrayList<>();
                        for (int j = 0; j < obj.getJSONArray("latitudes").length(); j++) {
                            JSONArray a = obj.getJSONArray("latitudes");
                            lat.add(a.getDouble(i));
                        }
                        route.setLatitudes(lat);

                        ArrayList<Double> lng = new ArrayList<>();
                        for (int j = 0; j < obj.getJSONArray("longitudes").length(); j++) {
                            lng.add(obj.getJSONArray("longitudes").getDouble(i));
                        }
                        route.setLongitudes(lng);

                        routes.add(route);

                    }


                    return routes;


                }else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return null;

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

    public class HttpGETFriendsRoutesAsyncTask extends AsyncTask<String, Void, ArrayList<Route>> {


        private Context context;
        private JSONObject jObj;
        private String msg;

        private String email;
        private String mPassword;
        public HttpGETFriendsRoutesAsyncTask(Context context) {

            this.context = context;
            //this.mPassword = password;
            //this.email = email;
        }

        @Override
        protected ArrayList<Route> doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<Route> routes) {

            if (routes==null){
                Friendslist = new ArrayList<>();
            }else {
                Friendslist = new ArrayList<>(routes);


            }
            list_friends.setAdapter(new ItemAdapter(getActivity(),Friendslist));


            list_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

        }

        @Override
        protected void onCancelled() {}


        public ArrayList<Route> GET(String url){
            InputStream inputStream = null;
            String result = "";
            JSONArray dataJsonArr = null;

            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpGet httpGet = new HttpGet(url);

                String json = "";


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
                    JSONArray array = new JSONArray(result);
                    ArrayList<Route> routes = new ArrayList();

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject obj = array.getJSONObject(i);

                        String friend = obj.getString("email");

                        ArrayList<Route> friendRoutes = GETFriendRoutes("http://192.168.1.34:3000/routes/"+friend);

                        routes.addAll(friendRoutes);

                    }


                    return routes;


                }else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return null;

        }

        public ArrayList<Route> GETFriendRoutes(String url){
            InputStream inputStream = null;
            String result = "";
            JSONArray dataJsonArr = null;

            try {

                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // 2. make POST request to the given URL
                HttpGet httpGet = new HttpGet(url);

                String json = "";


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
                    JSONArray array = new JSONArray(result);
                    ArrayList<Route> routes = new ArrayList();

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject obj = array.getJSONObject(i);

                        Route route = new Route();
                        route.setDistance(new Double(obj.getInt("distance")));
                        route.setTime(new Long(obj.getInt("time")));

                        ArrayList<Double> lat = new ArrayList<>();
                        for (int j = 0; j < obj.getJSONArray("latitudes").length(); j++) {
                            lat.add(obj.getJSONArray("latitudes").getDouble(i));
                        }
                        route.setLatitudes(lat);

                        ArrayList<Double> lng = new ArrayList<>();
                        for (int j = 0; j < obj.getJSONArray("longitudes").length(); j++) {
                            lng.add(obj.getJSONArray("longitudes").getDouble(i));
                        }
                        route.setLongitudes(lng);

                        routes.add(route);

                    }


                    return routes;


                }else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            // 11. return result
            return null;

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
