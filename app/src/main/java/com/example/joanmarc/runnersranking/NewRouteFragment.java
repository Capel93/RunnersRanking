package com.example.joanmarc.runnersranking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.location.LocationListener;
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
                    startRoute = false;
                    start.setText("Iniciar");

                } else {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    startRoute = true;

                    start.setText("Pausa");
                    start.setBackgroundColor(Color.YELLOW);

                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                startRoute=false;
                new AlertDialog.Builder(getActivity())
                        .setTitle("Guardar activitad")
                        .setMessage("Quiere guardar esta nueva actividad?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                NewRouteFragment.newInstance();
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

        crta = new Criteria();
        crta.setAccuracy(Criteria.ACCURACY_FINE);
        crta.setAltitudeRequired(false);
        crta.setBearingRequired(false);
        crta.setCostAllowed(true);
        crta.setPowerRequirement(Criteria.POWER_LOW);


        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locationListener);
        }else{
            new AlertDialog.Builder(getActivity())
                    .setTitle("GPS service")
                    .setMessage("You want to activate the GPS service?")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            provider = locationManager.getBestProvider(crta, true);
                            loc = locationManager.getLastKnownLocation(provider);
                            locationManager.requestLocationUpdates(provider,1000,0,locationListener);
                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    }).create().show();

        }


        if (loc!=null){
            Log.d("Posicioooooooooo",loc.getLatitude()+":"+loc.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(loc.getLatitude(), loc.getLongitude()), 14));
        }

        route = new PolylineOptions().geodesic(true);
        Polyline line = mMap.addPolyline(route);

    }

    private final LocationListener locationListener = new LocationListener() {


        @Override
        public void onLocationChanged(Location location) {
            //mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 14));

            if (startRoute) {
                route.add(new LatLng(location.getLatitude(), location.getLongitude()));

                Polyline line = mMap.addPolyline(route);
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
}
