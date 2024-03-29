package com.danielrepko83.jordancampbell01.stepahead;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    ReminderFragment.OnFragmentInteractionListener,
                    MainFragment.OnFragmentInteractionListener,
                    WeightFragment.OnFragmentInteractionListener,
                    WeightListFragment.OnFragmentInteractionListener,
                    WeightGraphFragment.OnFragmentInteractionListener,
                    TwitterFragment.OnFragmentInteractionListener,
                    CreditsFragment.OnFragmentInteractionListener,
                    RunHistoryFragment.OnFragmentInteractionListener,
                    CreateJournalFragment.OnFragmentInteractionListener,
                    ViewRunFragment.OnFragmentInteractionListener,
                    RunPhotoFragment.OnFragmentInteractionListener{

    FragmentManager fm;
    static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Twitter.initialize(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        fm = getSupportFragmentManager();
        if(savedInstanceState == null){
            FragmentTransaction trans = fm.beginTransaction();
            trans.replace(R.id.content, new MainFragment());
            trans.commit();


            //pull the hasRun sharedPref
            SharedPreferences sharedPref = getSharedPreferences("FileName",MODE_PRIVATE);
            Boolean hasRun = sharedPref.getBoolean("hasRun", false);
            //check if the app has been launched before
            if(!hasRun){
                //if this is first launch after download
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("hasRun",true);
                editor.commit();

                CustomDialog dialog = new CustomDialog(this);
                dialog.show();
            }


        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();

        new UserTimeline.Builder().screenName("StepAheadApp").build();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction trans = fm.beginTransaction();
        trans.setCustomAnimations(R.anim.enter_from_right_medium, R.anim.exit_to_left_medium, R.anim.enter_from_left_medium, R.anim.exit_to_right_medium);

        if (id == R.id.nav_home) {
            if(fm.findFragmentByTag("CreateJournal") != null){
                new AlertDialog.Builder(this)
                        .setTitle("Leave Page?")
                        .setMessage("Are you sure you want to leave the page? Changes will not be saved.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                fm.popBackStack();
                                FragmentTransaction trans = fm.beginTransaction();
                                trans.setCustomAnimations(R.anim.enter_from_right_medium, R.anim.exit_to_left_medium, R.anim.enter_from_left_medium, R.anim.exit_to_right_medium);
                                trans.replace(R.id.content, new MainFragment());
                                trans.addToBackStack(null);
                                trans.commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                trans.replace(R.id.content, new MainFragment());
                trans.addToBackStack(null);
                trans.commit();
            }
        } else if (id == R.id.nav_journals) {
            if(fm.findFragmentByTag("CreateJournal") != null){
                new AlertDialog.Builder(this)
                        .setTitle("Leave Page?")
                        .setMessage("Are you sure you want to leave the page? Changes will not be saved.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                fm.popBackStack();
                                FragmentTransaction trans = fm.beginTransaction();
                                trans.setCustomAnimations(R.anim.enter_from_right_medium, R.anim.exit_to_left_medium, R.anim.enter_from_left_medium, R.anim.exit_to_right_medium);
                                trans.replace(R.id.content, new RunHistoryFragment());
                                trans.addToBackStack(null);
                                trans.commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                trans.replace(R.id.content, new RunHistoryFragment());
                trans.addToBackStack(null);
                trans.commit();
            }
        } else if (id == R.id.nav_reminder) {
            if(fm.findFragmentByTag("CreateJournal") != null){
                new AlertDialog.Builder(this)
                        .setTitle("Leave Page?")
                        .setMessage("Are you sure you want to leave the page? Changes will not be saved.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                fm.popBackStack();
                                FragmentTransaction trans = fm.beginTransaction();
                                trans.setCustomAnimations(R.anim.enter_from_right_medium, R.anim.exit_to_left_medium, R.anim.enter_from_left_medium, R.anim.exit_to_right_medium);
                                trans.replace(R.id.content, new ReminderFragment());
                                trans.addToBackStack(null);
                                trans.commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                trans.replace(R.id.content, new ReminderFragment());
                trans.addToBackStack(null);
                trans.commit();
            }
        } else if (id == R.id.nav_weight) {
            if(fm.findFragmentByTag("CreateJournal") != null){
                new AlertDialog.Builder(this)
                        .setTitle("Leave Page?")
                        .setMessage("Are you sure you want to leave the page? Changes will not be saved.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                fm.popBackStack();
                                FragmentTransaction trans = fm.beginTransaction();
                                trans.setCustomAnimations(R.anim.enter_from_right_medium, R.anim.exit_to_left_medium, R.anim.enter_from_left_medium, R.anim.exit_to_right_medium);
                                trans.replace(R.id.content, new WeightFragment());
                                trans.addToBackStack(null);
                                trans.commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                trans.replace(R.id.content, new WeightFragment());
                trans.addToBackStack(null);
                trans.commit();
            }
        } else if (id == R.id.nav_credits) {
            if(fm.findFragmentByTag("CreateJournal") != null){
                new AlertDialog.Builder(this)
                        .setTitle("Leave Page?")
                        .setMessage("Are you sure you want to leave the page? Changes will not be saved.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                fm.popBackStack();
                                FragmentTransaction trans = fm.beginTransaction();
                                trans.setCustomAnimations(R.anim.enter_from_right_medium, R.anim.exit_to_left_medium, R.anim.enter_from_left_medium, R.anim.exit_to_right_medium);
                                trans.replace(R.id.content, new CreditsFragment());
                                trans.addToBackStack(null);
                                trans.commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                trans.replace(R.id.content, new CreditsFragment());
                trans.addToBackStack(null);
                trans.commit();
            }
        } else if (id == R.id.nav_twitter) {
            if(fm.findFragmentByTag("CreateJournal") != null){
                new AlertDialog.Builder(this)
                        .setTitle("Leave Page?")
                        .setMessage("Are you sure you want to leave the page? Changes will not be saved.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                fm.popBackStack();
                                FragmentTransaction trans = fm.beginTransaction();
                                trans.setCustomAnimations(R.anim.enter_from_right_medium, R.anim.exit_to_left_medium, R.anim.enter_from_left_medium, R.anim.exit_to_right_medium);
                                trans.replace(R.id.content, new TwitterFragment());
                                trans.addToBackStack(null);
                                trans.commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                trans.replace(R.id.content, new TwitterFragment());
                trans.addToBackStack(null);
                trans.commit();
            }
        } else if (id == R.id.nav_email) {
            String[] email = {"support@stepaheadapp.ca"};
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, email);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Issue with Step Ahead App");
            intent.putExtra(Intent.EXTRA_TEXT, "I would like to report an issue with the Step Ahead app...");
            if(intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else if (id == R.id.nav_sms) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:5196687325"));
            intent.putExtra("sms_body", "I have encountered an issue with the Step Ahead app...");
            if(intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
