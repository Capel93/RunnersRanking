package com.example.joanmarc.runnersranking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joanmarc.myapplication.backend.usersApi.UsersApi;
import com.example.joanmarc.myapplication.backend.usersApi.model.Users;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;


public class ActionBarTabActivity extends ActionBarActivity implements ActionBar.TabListener, ItemFragment.OnFragmentInteractionListener,
        NewRouteFragment.OnNewRouteFragmentInteractionListener,RankingFragment.OnRankingFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private ArrayList array;
    private ArrayAdapter arrayAdapter;
    private String userAdd;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_tab);

        mContext = getApplicationContext();
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_action_bar_tab, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar_tab, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,ProfileActivity.class);
            startActivity(i);
            return true;
        }
        final EditText input = new EditText(this);

        if(id == R.id.add_user){
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(ActionBarTabActivity.this);
            builderSingle.setIcon(R.drawable.ic_launcher);
            builderSingle.setView(input);
            builderSingle.setTitle("Insert Username :-");
            /*final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    ActionBarTabActivity.this,
                    android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Joan");
            arrayAdapter.add("Marc");
            arrayAdapter.add("Joel");
            arrayAdapter.add("Guillem");
            arrayAdapter.add("Sergi");*/

            builderSingle.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = input.getText().toString();
                    new UserAddTask(mContext,value).execute();
                }
            });
            builderSingle.setNegativeButton("cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            /*builderSingle.setAdapter(arrayAdapter,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName = arrayAdapter.getItem(which);
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                    ActionBarTabActivity.this);
                            builderInner.setMessage(strName);
                            builderInner.setTitle("User is added as your friend");
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
                    });*/
            builderSingle.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final Intent relogin = new Intent(this,LoginActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivity(relogin);
                        finish();
                    }
                }).create().show();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onFragmentInteraction(String id) {
        Intent i = new Intent(this,DetailRouteActivity.class);
        startActivity(i);
    }


    @Override
    public void onNewRouteFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRankingFragmentInteraction(Uri uri) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            switch (position) {
                case 0:
                    return NewRouteFragment.newInstance();
                case 1:
                    return ItemFragment.newInstance("hello0", "hello2");
                case 2:
                    return RankingFragment.newInstance("ww","ee");
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();

            switch (position) {
                case 0:
                    return getString(R.string.iniciar).toUpperCase(l);
                case 1:
                    return getString(R.string.historial).toUpperCase(l);
                case 2:
                    return getString(R.string.ranking).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);


            return rootView;
        }
    }

    public class UserAddTask extends AsyncTask<Void, Void, Boolean> {



        private String msg;
        private String regId;
        private String userName;

        private UsersApi regUser = null;
        private GoogleCloudMessaging gcm;
        private Context mcontext;

        private static final String SENDER_ID = "564533837615";


        public UserAddTask(Context context,String userName) {
            mcontext=context;
            this.userName = userName;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (regUser==null){
                UsersApi.Builder builder = new UsersApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://probable-analog-92915.appspot.com/_ah/api/");
                regUser = builder.build();

            }


            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(mcontext);
                }
                regId = gcm.register(SENDER_ID);
                msg = "Device registered, registration ID=" + regId;

                SharedPreferences userDetails = mcontext.getSharedPreferences("userdetails", mcontext.MODE_PRIVATE);
                Users u;

                if((u=regUser.insertFriend(userDetails.getString("userName",""),userName).execute())==null){
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
        protected void onPostExecute(final Boolean success) {


            if (success) {


            } else {
                Toast.makeText(mcontext,msg,Toast.LENGTH_LONG);
            }


        }

        @Override
        protected void onCancelled() {

        }
    }

}
