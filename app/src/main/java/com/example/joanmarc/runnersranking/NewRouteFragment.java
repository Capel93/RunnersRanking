package com.example.joanmarc.runnersranking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.joanmarc.myapplication.backend.routeApi.RouteApi;

import com.example.joanmarc.myapplication.backend.routeApi.model.Route;






import com.example.joanmarc.myapplication.backend.usersApi.UsersApi;
import com.example.joanmarc.myapplication.backend.usersApi.model.Users;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.location.LocationListener;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.util.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.joanmarc.runnersranking.NewRouteFragment.OnNewRouteFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewRouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRouteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnNewRouteFragmentInteractionListener mListener;
    private FragmentActivity myContext;
    private GoogleMap mMap;
    private MapView mapView;
    private Bundle mBundle;
    private PolylineOptions route;
    private boolean startRoute = false;
    private Chronometer chronometer;
    private String provider = null;
    private Location loc = null;
    private LocationManager locationManager;
    private Criteria crta;
    private Polyline polyRoute;
    private boolean restart = false;
    private long timeWhenStopped=0;
    private long elapsedTime;

    private String startPoint;
    private String finishPoint;
    private long time;
    private Date date;
    private double distance;
    private double calories;
    private List<Double> rates;
    private List<Double> longitudes;
    private List<Double> latitudes;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment NewRouteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewRouteFragment newInstance() {
        NewRouteFragment fragment = new NewRouteFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public NewRouteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mBundle = savedInstanceState;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflatedView = inflater.inflate(R.layout.fragment_new_route, container, false);

        // Maps
        mapView = (MapView) inflatedView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mMap = mapView.getMap();


        MapsInitializer.initialize(this.getActivity());

        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        if (mMap != null) {
            route=new PolylineOptions().geodesic(true);
            setUpMap();
        }



        //Chronometer
        chronometer = (Chronometer)inflatedView.findViewById(R.id.chronometer);
        final Button start = (Button)inflatedView.findViewById(R.id.iniciar);
        Button stop = (Button)inflatedView.findViewById(R.id.stop);

        stop.setText("STOP");



        // Buttons
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startRoute) {
                    chronometer.stop();
                    restart = true;
                    timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
                    startRoute = false;
                    start.setText("Iniciar");
                    start.setBackgroundColor(0xffa1ccff);
                    //elapsedTime = SystemClock.elapsedRealtime()-elapsedTime;

                } else {

                    if (restart){
                        chronometer.setBase(SystemClock.elapsedRealtime()+timeWhenStopped);
                        elapsedTime = elapsedTime - timeWhenStopped;
                    }else{
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        elapsedTime = SystemClock.elapsedRealtime();
                    }

                    chronometer.start();
                    startRoute = true;

                    date = new Date();



                    start.setText("Pausa");
                    start.setBackgroundColor(0xFFFFFB7A);

                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chronometer.stop();
                //time=SystemClock.elapsedRealtime()-chronometer.getBase();
                time = chronometer.getBase()-SystemClock.elapsedRealtime();
                time=time*-1;
                startRoute=false;
                startPoint="";
                finishPoint="";
                longitudes = polylineToListLongitudes(route.getPoints());
                latitudes = polylineToListLatitudes(route.getPoints());
                distance = calcDistance(route.getPoints());
                new AlertDialog.Builder(getActivity())
                        .setTitle("Guardar activitad")
                        .setMessage("Quiere guardar esta nueva actividad?")
                        .setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                NewRouteFragment.newInstance();

                            }
                        })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                                new RouteAddAsyncTask(myContext,startPoint,finishPoint,time,date,distance,calories,rates,longitudes,latitudes).execute();


                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Reto")
                                        .setMessage("Quiere retar a algun amigo?")
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1) {




                                            }
                                        })
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface arg0, int arg1) {

                                                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                                                builderSingle.setIcon(R.drawable.ic_launcher);
                                                builderSingle.setTitle("Select One user:-");
                                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                                        getActivity(),
                                                        android.R.layout.select_dialog_singlechoice);
                                                arrayAdapter.add("Joan");
                                                arrayAdapter.add("Marc");
                                                arrayAdapter.add("Joel");
                                                arrayAdapter.add("Guillem");
                                                arrayAdapter.add("Sergi");
                                                builderSingle.setNegativeButton("cancel",
                                                        new DialogInterface.OnClickListener() {

                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                                builderSingle.setAdapter(arrayAdapter,
                                                        new DialogInterface.OnClickListener() {

                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                String strName = arrayAdapter.getItem(which);
                                                                AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                                                        getActivity());
                                                                builderInner.setMessage(strName);
                                                                builderInner.setTitle("You have challenged: ");
                                                                builderInner.setPositiveButton("Ok",
                                                                        new DialogInterface.OnClickListener() {

                                                                            @Override
                                                                            public void onClick(
                                                                                    DialogInterface dialog,
                                                                                    int which) {
                                                                                dialog.dismiss();
                                                                            }
                                                                        });
                                                                builderInner.show();
                                                            }
                                                        });
                                                builderSingle.show();
                                            }
                                        }).create().show();
                                NewRouteFragment.newInstance();
                                //Guardar
                            }
                        }).create().show();
            }
        });

        return inflatedView;
        //setUpMapIfNeeded();
        //return inflater.inflate(R.layout.fragment_new_route, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onNewRouteFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
        try {
            mListener = (OnNewRouteFragmentInteractionListener) activity;
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
    public interface OnNewRouteFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onNewRouteFragmentInteraction(Uri uri);
    }




    private void setUpMap() {
        //LocationManager locationManager = (LocationManager) myContext.getSystemService(Context.LOCATION_SERVICE);
        //LocationListener locationListener = new MyLocationListener();
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,((LocationListener) new MyLocationListener()));

        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) myContext.getSystemService(context);

        Location loc;
        crta = new Criteria();
        crta.setAccuracy(Criteria.ACCURACY_FINE);
        crta.setAltitudeRequired(false);
        crta.setBearingRequired(false);
        crta.setCostAllowed(true);
        crta.setPowerRequirement(Criteria.POWER_LOW);

        route = new PolylineOptions().geodesic(true);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            /*loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locationListener);
            if (loc!=null){
                locationListener.onLocationChanged(loc);
                Log.d("Posicioooooooooo",loc.getLatitude()+":"+loc.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(loc.getLatitude(), loc.getLongitude()), 14));
                route.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
                mMap.addPolyline(route);
            }*/

            locationManager =
                    (LocationManager)myContext.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc!=null){
                //locationListener.onLocationChanged(loc);
                Log.d("Posicioooooooooo",loc.getLatitude()+":"+loc.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(loc.getLatitude(), loc.getLongitude()), 14));
                route.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
                mMap.addPolyline(route);
            }

        }else{
            new AlertDialog.Builder(getActivity())
                    .setTitle("GPS service")
                    .setMessage("You want to activate the GPS service?")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                            /*provider = locationManager.getBestProvider(crta, true);
                            loc = locationManager.getLastKnownLocation(provider);
                            locationManager.requestLocationUpdates(provider,1000,0,locationListener);
                            if (loc!=null){
                                Log.d("Posicioooooooooo",loc.getLatitude()+":"+loc.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(loc.getLatitude(), loc.getLongitude()), 14));
                                route.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
                                mMap.addPolyline(route);
                            }*/
                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            /*if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                                loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
                            }
                            if (loc!=null){
                                Log.d("Posicioooooooooo",loc.getLatitude()+":"+loc.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(loc.getLatitude(), loc.getLongitude()), 14));
                                route.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
                                mMap.addPolyline(route);
                            }*/
                            locationManager =
                                    (LocationManager)myContext.getSystemService(Context.LOCATION_SERVICE);
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }).create().show();

        }






    }

    public class RouteAddAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private String startPoint;
        private String finishPoint;
        private long time;
        private Date date;
        private double distance;
        private double calories;
        private List<Double> rates;
        private List<List<Double>> routes;

        private RouteApi regRoute = null;
        private UsersApi regUser =  null;
        private GoogleCloudMessaging gcm;
        private Context context;

        // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
        private static final String SENDER_ID = "564533837615";


        public RouteAddAsyncTask(Context context, String startPoint,
                            String finishPoint, long time, Date date,
                            double distance, double calories, List<Double> rates,
                                 List<Double> longitudes,List<Double> latitudes) {
            this.context=context;
            this.startPoint = startPoint;
            this.finishPoint = finishPoint;
            this.time = time;
            this.date = date;
            this.distance = distance;
            this.calories = calories;
            this.rates = rates;
            this.routes = routes;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

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

                Route route = new Route();
                route.setStartPoint(startPoint);
                route.setFinishPoint(finishPoint);
                route.setUserName(userDetails.getString("userName", ""));
                route.setDistance(distance);
                route.setCalories(calories);
                route.setDate(new DateTime(date));
                route.setTime(time);
                route.setLongitudes(longitudes);
                route.setLatitudes(latitudes);
                //route.setRates(rates);





                Route r;
                if((r=regRoute.insertRoute(route).execute())==null){
                    return false;
                }


                Users u;
                if((u=regUser.insertUserRoute(userDetails.getString("userName",""),r.getId()).execute())==null){
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
                Toast.makeText(context, "Route add succesfully", Toast.LENGTH_LONG);
                NewRouteFragment.newInstance();

            }else{
                Toast.makeText(context,"Error on add route",Toast.LENGTH_LONG);
            }
        }
    }

    private final LocationListener locationListener = new LocationListener() {


        @Override
        public void onLocationChanged(Location location) {
            //mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 14));

            if (startRoute) {
                route.add(new LatLng(location.getLatitude(), location.getLongitude()));


                mMap.addPolyline(route);
            }


        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private List<Double> polylineToListLongitudes(List<LatLng> poly){


            List<Double> longitude = new ArrayList<>();

            for (LatLng latLng : poly) {
                longitude.add(latLng.longitude);
            }

            return longitude;
    }
    private List<Double> polylineToListLatitudes(List<LatLng> poly) {


        List<Double> latitudes = new ArrayList<>();

        for (LatLng latLng : poly) {
            latitudes.add(latLng.latitude);
        }

        return latitudes;

    }
    private double calcDistance(List<LatLng> poly){

        float[] results = new float[1];
        if (poly.size()==0){
            return 0;
        }else{
            LatLng lastPos = poly.get(0);
            double distance=0.0;
            for (LatLng pos: poly){
                Location.distanceBetween(lastPos.latitude, lastPos.longitude,
                        pos.latitude, pos.longitude, results);
                distance = distance + results[0];
                lastPos=pos;
            }

            return distance;
        }

    }



}
