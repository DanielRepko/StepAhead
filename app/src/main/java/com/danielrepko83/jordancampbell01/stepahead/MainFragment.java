package com.danielrepko83.jordancampbell01.stepahead;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.RunJournal;
import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;
import com.google.android.gms.location.LocationRequest;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private static final int PERMISSIONS_REQUEST = 1;

    public static TextView distance;
    public static TextView duration;
    public static TextView calories;

    public TextView distanceLabel;

    private static final int CAMERA_INTENT_LABEL = 1;
    private String imageLocation;
    //this ArrayList will hold all of image resources to be used inside of CreateRunFragment
    public static ArrayList<String> runPictures;

    FragmentManager fm;

    public static RunJournal runJournal;

    private String weatherString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        MainActivity.fab.hide();
        fm = getActivity().getSupportFragmentManager();

        //stop LocationTracker if it is running
        getActivity().stopService(new Intent(getActivity(), LocationTracker.class));

        distance = view.findViewById(R.id.distance);
        distanceLabel = view.findViewById(R.id.distanceLabel);
        duration = view.findViewById(R.id.duration);
        TextView durationLabel = view.findViewById(R.id.durationLabel);
        calories = view.findViewById(R.id.calories);
        TextView caloriesLabel = view.findViewById(R.id.caloriesLabel);
        final Button startRun = view.findViewById(R.id.startRun);
        final Button cancel = view.findViewById(R.id.cancel);
        final Button pause = view.findViewById(R.id.pause);
        final Button finish = view.findViewById(R.id.finish);
        runPictures = new ArrayList<>();

        //set text of distanceLabel according to the measurement selected in settings
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(Integer.parseInt(sharedPref.getString("distance_preference", "0")) == 0) {
            distanceLabel.setText(R.string.home_page_distance_label_km_text);
        } else {
            distanceLabel.setText(R.string.home_page_distance_label_mi_text);
        }


        //Start Run click listener
        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //show the fab button
                MainActivity.fab.show();
                MainActivity.fab.setImageResource(R.drawable.ic_photo_camera_black_24dp);

                //asking permission for location
                int permission = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if(permission == PackageManager.PERMISSION_GRANTED){
                    //hide the startRun button and show the pause, finish, and cancel buttons
                    startRun.setVisibility(View.GONE);
                    cancel.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    finish.setVisibility(View.VISIBLE);

                    runJournal = new RunJournal();
                    runJournal.setStartTime(Calendar.getInstance().getTime()+"");
                    getActivity().startService(new Intent(getActivity(), LocationTracker.class));
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST);
                }

            }
        });



        //Cancel click listener
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create an alert asking the user if they want to cancel the run
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.home_page_alert_title)
                        .setMessage(R.string.home_page_alert_message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if yes, get rid of the pause, finish cancel buttons and show the startRun button
                                startRun.setVisibility(View.VISIBLE);
                                cancel.setVisibility(View.GONE);
                                pause.setText(R.string.home_page_pause_button_text);
                                pause.setVisibility(View.GONE);
                                finish.setVisibility(View.GONE);

                                //hide the fab button
                                //user should not be able to take
                                //a picture when a run is not active
                                MainActivity.fab.hide();


                                //stop tracking location
                                getActivity().stopService(new Intent(getActivity(), LocationTracker.class));

                                runJournal = null;

                            }
                        })
                        //if no, do nothing
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        //Pause click listener
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if the run is already paused
                if(pause.getText().toString().equals("Pause")){
                    //if not, pause recording
                    pause.setText(R.string.home_page_resume_button_text);
                    LocationTracker.pause();

                } else {
                    //if it is paused, then resume recording
                    pause.setText(R.string.home_page_pause_button_text);
                    LocationTracker.pause();

                }
            }
        });

        //Finish click listener
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add data to run journal
                runJournal.setDistanceKM(Double.parseDouble(distance.getText().toString()));
                runJournal.setDuration(duration.getText().toString());
                runJournal.setCalories(Integer.parseInt(calories.getText().toString()));

                //check to see if LocationTracker has had time to pull an initial location
                if(LocationTracker.lastLocation != null) {
                    //pull weather information
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    String url = "http://api.openweathermap.org/data/2.5/weather?lat="
                            + LocationTracker.lastLocation.getLatitude() + "&lon="
                            + LocationTracker.lastLocation.getLongitude() +
                            "&units=metric&appid=e4fe52a6d27f0a63571bfc00fe71d629";
                    weatherString = "";
                    //request the weather
                    JsonObjectRequest weatherRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Grab the first object out of the weather array
                            try {
                                JSONObject object = response.getJSONArray("weather").getJSONObject(0);
                                String text = object.getString("main") + " ";
                                weatherString = text;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.getLocalizedMessage());
                        }
                    });
                    //request the tempature
                    JsonObjectRequest tempRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Grab the main object out of the response object
                            try {
                                JSONObject object = response.getJSONObject("main");
                                //unicode at end makes it display as Celsius
                                String text = object.getDouble("temp") + "\u2103";
                                weatherString += text;
                                runJournal.setWeather(weatherString);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.getLocalizedMessage());
                        }
                    });

                    requestQueue.add(weatherRequest);
                    requestQueue.add(tempRequest);
                }


                getActivity().stopService(new Intent(getActivity(), LocationTracker.class));

                FragmentTransaction trans = fm.beginTransaction();
                trans.addToBackStack(null);
                trans.replace(R.id.content, new CreateJournalFragment(), "CreateJournal");
                trans.commit();

            }
        });

        MainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cameraPermission = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA);
                //check to see if camera permission is granted
                if(cameraPermission != PackageManager.PERMISSION_GRANTED){
                    //has the user already denied this permission previously
                    if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA)){

                        //if so, tell the user why the app needs this permission
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.permission_title_camera)
                                .setMessage(R.string.permission_description_camera)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.CAMERA},
                                                PERMISSIONS_REQUEST);
                                    }
                                }).show();

                    } else {
                        //if not, just ask for the permission
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                PERMISSIONS_REQUEST);
                    }
                } else {
                    //if so, then check to see if write permission is granted
                    int writePermission = ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(writePermission != PackageManager.PERMISSION_GRANTED){
                        //has the user already denied this permission previously
                        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            //if so, tell the user why the app needs this permission
                            new AlertDialog.Builder(getContext())
                                    .setTitle(R.string.permission_title_write_storage)
                                    .setMessage(R.string.permission_description_write_storage)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ActivityCompat.requestPermissions(getActivity(),
                                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                    PERMISSIONS_REQUEST);
                                        }
                                    }).show();

                        } else {
                            //if not, just ask for the permission
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_REQUEST);
                        }
                    } else {
                        //if all permissions are granted
                        File picture = null;
                        try{
                            picture = createTempImageFile();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri photoUri = FileProvider.getUriForFile(getContext(), "com.danielrepko83.jordancampbell01.stepahead", picture);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        if(intent.resolveActivity(getActivity().getPackageManager())!= null) {
                            startActivityForResult(intent, CAMERA_INTENT_LABEL);
                        }
                    }
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check to see if it is the camera and if is responding with an OK status
        if(requestCode == CAMERA_INTENT_LABEL && resultCode == RESULT_OK){
            //add the image location to runPictures
            runPictures.add(imageLocation);
            Toast.makeText(getContext(),
                    "Photo added to run",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),
                    "Photo not added to run",
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * this creates a temp file to be used
     * by the camera to save a photo
     */
    File createTempImageFile() throws IOException {
        //Create the name of the image
        String fileName = "run_journal_pic_2018_" + System.currentTimeMillis();
        //Grab the directory we want to save the image in
        File directory = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File picture  = File.createTempFile(fileName, ".jpg", directory);
        imageLocation = picture.getAbsolutePath();
        return picture;
    }

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(Integer.parseInt(sharedPref.getString("distance_preference", "0")) == 0) {
            distanceLabel.setText(R.string.home_page_distance_label_km_text);
        } else {
            distanceLabel.setText(R.string.home_page_distance_label_mi_text);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
