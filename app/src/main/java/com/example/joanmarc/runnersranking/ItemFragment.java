package com.example.joanmarc.runnersranking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.joanmarc.myapplication.backend.routeApi.RouteApi;
import com.example.joanmarc.myapplication.backend.routeApi.model.CollectionResponseRoute;
import com.example.joanmarc.myapplication.backend.routeApi.model.Route;

import com.example.joanmarc.runnersranking.dummy.DummyContent;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Route> routes = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private ItemAdapter adapter;
    private ItemFragment itemFragment;
    private Context mContext;


    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
        //new ListRoutesAsyncTask(getActivity()).execute();
        SharedPreferences userDetails = getActivity().getSharedPreferences("userdetails", getActivity().MODE_PRIVATE);
        new HttpGETRoutesAsyncTask(getActivity()).execute("http://192.168.1.34:3000/routes/"+userDetails.getString("email",""));

        itemFragment = this;

        mContext=getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);


        mListView = (AbsListView) view.findViewById(android.R.id.list);



        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }


    public class ItemAdapter extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater=null;
        private ArrayList<Route> routes;
        private int pos;


        public ItemAdapter(Activity a,ArrayList<Route> routes) {
            activity = a;
            this.routes=routes;


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
            //inflater = LayoutInflater.from()

            pos = position;
            if(convertView==null)

                inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.item_history, null);

            TextView title = (TextView)vi.findViewById(R.id.title); // title
            TextView date = (TextView)vi.findViewById(R.id.date); // date route
            TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
            ImageButton profile_image=(ImageButton)vi.findViewById(R.id.list_image); // profile image
            Route r = routes.get(position);
            //title.setText(r.getDate().toString());
            Time t = new Time(r.getTime());
            String sTime = t.toString();
            date.setText("Time: "+sTime.substring(3));
            duration.setText(new Long(Math.round(r.getDistance())).toString()+"m");
            duration.setTextSize(12);

            //duration.setText(r.getTime().getMinutes()+":"+r.getTime().getSeconds());

            View.OnClickListener list = new onClickRoute(r);
            /*vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(mContext,DetailRouteActivity.class));
                }
            });*/
            vi.setOnClickListener(list);



            profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Goes to friend profile
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

    public class onClickRoute implements View.OnClickListener{

        private Route route;

        public onClickRoute(Route r){
            this.route=r;
        }

        @Override
        public void onClick(View v) {

            Intent i = new Intent(mContext,DetailRouteActivity.class);

            RouteClass routeClass = new RouteClass(route);
            i.putExtra("route",routeClass);

            startActivity(i);
        }
    }

    public class ListRoutesAsyncTask extends AsyncTask<Void, Void, CollectionResponseRoute> {

        private RouteApi regRoute = null;
        private GoogleCloudMessaging gcm;
        private Context context;

        private static final String SENDER_ID = "564533837615";

        @Override
        protected CollectionResponseRoute doInBackground(Void... params) {
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


                SharedPreferences userDetails = context.getSharedPreferences("userdetails", context.MODE_PRIVATE);


                //return regUser.listUserRoutes(10,regId).execute().getItems();
                String userName;
                if (userDetails.contains("userName")){
                    userName = userDetails.getString("userName","");
                }else{
                    userName  ="dev";
                }




                return  regRoute.listRouteByUser(5,userName).execute();






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
        protected void onPostExecute(CollectionResponseRoute rts) {

            if (rts==null){

            }else {

                try {
                    routes = new ArrayList<>(rts.getItems());
                }catch (NullPointerException n){
                    routes = new ArrayList<>();
                }

                Collections.sort(routes, new Comparator<Route>() {
                            @Override
                            public int compare(Route lhs, Route rhs) {
                                return new Long(lhs.getDate().getValue()).compareTo(new Long(rhs.getDate().getValue()));
                            }
                        }
                );
                Collections.sort(routes, new Comparator<Route>() {
                            @Override
                            public int compare(Route lhs, Route rhs) {
                                return new Long(lhs.getDate().getValue()).compareTo(new Long(rhs.getDate().getValue()));
                            }
                        }
                );
                adapter = new ItemAdapter(getActivity(), routes);


                // Set the adapter
                mListView.setAdapter(adapter);


                // Set OnItemClickListener so we can be notified on item clicks
                mListView.setFocusable(true);
                //mListView.setOnItemClickListener(this);

                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Really Delete?")
                                .setMessage("Are you sure you want to delete this route?")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                routes.remove(position);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                ).create().show();

                        return false;
                    }
                });
            }

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

            if (routes!=null){
                /*Collections.sort(routes, new Comparator<RouteClass>() {
                            @Override
                            public int compare(RouteClass lhs, RouteClass rhs) {
                                return new Long(lhs.getDate().getValue()).compareTo(new Long(rhs.getDate().getValue()));
                            }
                        }
                );
                Collections.sort(routes, new Comparator<RouteClass>() {
                            @Override
                            public int compare(RouteClass lhs, RouteClass rhs) {
                                return new Long(lhs.getDate().getValue()).compareTo(new Long(rhs.getDate().getValue()));
                            }
                        }
                );*/



                adapter = new ItemAdapter(getActivity(), routes);


                // Set the adapter
                mListView.setAdapter(adapter);


                // Set OnItemClickListener so we can be notified on item clicks
                mListView.setFocusable(true);
                //mListView.setOnItemClickListener(this);

                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Really Delete?")
                                .setMessage("Are you sure you want to delete this route?")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //routes.remove(position);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                ).create().show();

                        return false;
                    }
                });
            }

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
