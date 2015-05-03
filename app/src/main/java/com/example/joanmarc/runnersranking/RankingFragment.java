package com.example.joanmarc.runnersranking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.joanmarc.myapplication.backend.routeApi.model.Route;

import java.util.ArrayList;
import java.util.Date;
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

    private List<String> items_Globallist;
    private List<String> items_Friendslist;
    private View rootView;


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

        //Spinners
        ArrayList spinner_array = new ArrayList<String>();
        spinner_array.add("Distance");
        spinner_array.add("Rate");
        spinner_array.add("Time");
        Spinner filter = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.select_filter,R.layout.spinner_layout);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        filter.setAdapter(arrayAdapter);
        //ArrayAdapter array_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, spinner_array);
        //array_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);



        //TabHost
        TabHost tabHost = (TabHost) rootView.findViewById(R.id.tabHostRank);
        ListView list_global = (ListView) rootView.findViewById(R.id.listViewGlobal);
        ListView list_friends = (ListView) rootView.findViewById(R.id.listViewFriends);
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

        ArrayList<Route> r = new ArrayList<>();
        /*r.add(new Route(new Date(2015,2,12)));
        r.add(new Route(new Date(2015,5,12)));
        r.add(new Route(new Date(2015,4,12)));*/


        list_global.setAdapter(new ItemAdapter(getActivity(),r));

        list_global.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        list_friends.setFocusable(true);
        list_friends.setAdapter(new ItemAdapter(getActivity(),r));


        list_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("EEEEEEEEEEEEEOOOOOOOOO","OOOOOOOOOOOOOOO");
                Intent i = new Intent(getActivity(),DetailRouteActivity.class);
                startActivity(i);
            }
        });

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
}
