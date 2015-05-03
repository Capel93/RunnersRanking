package com.example.joanmarc.runnersranking;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class DetailRouteActivity extends ActionBarActivity {


    public RouteClass route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_route2);
        route = (RouteClass) getIntent().getSerializableExtra("route");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(route))
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_route, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment{


        private MapView mapView;
        private GoogleMap mMap;
        //private FragmentTabHost mTabHost;

        private static final String TAG = "FragmentTabs";
        public static final String TAB_CALORIES = "calories";
        public static final String TAB_DISTANCE = "distace";
        public static final String TAB_RATE = "rate";

        private View mRoot;
        private TabHost mTabHost;
        private int mCurrentTab;
        private List<String> items_list;
        private ArrayAdapter<String> adapter;
        private RouteClass route;

        public PlaceholderFragment(RouteClass route) {
            this.route=route;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail_route, container, false);


            // Maps
            mapView = (MapView) rootView.findViewById(R.id.map_detail_route);
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            mMap = mapView.getMap();
            mMap.setMyLocationEnabled(true);

            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            LatLng point = new LatLng(0,0);
            for (int i = 0; i < route.getLongitudes().size(); i++) {
                point = new LatLng(route.getLatitudes().get(i),route.getLongitudes().get(i));

                options.add(point);
                mMap.addMarker(new MarkerOptions().position(point));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
            mMap.addPolyline(options);

            MapsInitializer.initialize(this.getActivity());




            //TabHost
            TabHost tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
            ListView list_rate = (ListView) rootView.findViewById(R.id.listView);
            TextView tv_calories = (TextView) rootView.findViewById(R.id.textView3);
            TextView tv_calories2 = (TextView) rootView.findViewById(R.id.textView4);
            TextView dist = (TextView) rootView.findViewById(R.id.textView2);
            TextView time = (TextView) rootView.findViewById(R.id.textView);
            Time t = new Time(route.getTime());
            time.setText(t.toString());
            tabHost.setup();

            items_list = new ArrayList<String>();
            items_list.add("Joan Marc.");
            items_list.add("Guillem.");
            items_list.add("Runking.");

            TabHost.TabSpec calories, distance, rate;

            distance = tabHost.newTabSpec("distance");
            distance.setContent(R.id.distance);
            distance.setIndicator("distance");
            tabHost.addTab(distance);

            rate = tabHost.newTabSpec("rate");
            rate.setContent(R.id.rate);
            rate.setIndicator("rate");
            tabHost.addTab(rate);

            calories = tabHost.newTabSpec("calories");
            calories.setContent(R.id.calories);
            calories.setIndicator("calories");
            tabHost.addTab(calories);

            dist.setText(new Double(route.getDistance()).toString());

            tv_calories.setText("Gran treball!! Has cremat .... ");
            tv_calories2.setText("Has de correr més. GORDAKOO");

            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items_list);
            list_rate.setAdapter(adapter);

            list_rate.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            return rootView;
        }
    }
}
