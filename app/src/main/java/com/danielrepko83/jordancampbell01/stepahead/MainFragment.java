package com.danielrepko83.jordancampbell01.stepahead;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

    private static final int CAMERA_INTENT_LABEL = 1;
    private String imageLocation;
    //this ArrayList will hold all of image resources to be used inside of CreateRunFragment
    public static ArrayList<String> runPictures;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        distance = view.findViewById(R.id.distance);
        TextView distanceLabel = view.findViewById(R.id.distanceLabel);
        duration = view.findViewById(R.id.duration);
        TextView durationLabel = view.findViewById(R.id.durationLabel);
        calories = view.findViewById(R.id.calories);
        TextView caloriesLabel = view.findViewById(R.id.caloriesLabel);
        final Button startRun = view.findViewById(R.id.startRun);
        final Button cancel = view.findViewById(R.id.cancel);
        final Button pause = view.findViewById(R.id.pause);
        final Button finish = view.findViewById(R.id.finish);
        runPictures = new ArrayList<>();

        final Intent trackerIntent = new Intent(getActivity(), LocationTracker.class);

        //Start Run click listener
        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide the startRun button and show the pause, finish, and cancel buttons
                startRun.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
                finish.setVisibility(View.VISIBLE);

                //show the fab button
                MainActivity.fab.show();
                MainActivity.fab.setImageResource(R.drawable.ic_photo_camera_black_24dp);

                //asking permission for location
                int permission = ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if(permission == PackageManager.PERMISSION_GRANTED){
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST);
                }



                getActivity().startService(trackerIntent);

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
                                pause.setVisibility(View.GONE);
                                finish.setVisibility(View.GONE);

                                //hide the fab button
                                //user should not be able to take
                                //a picture when a run is not active
                                MainActivity.fab.hide();

                                if(pause.getText().equals("Resume")){
                                    pause.setText("Pause");
                                    LocationTracker.pause();
                                }

                                //stop tracking location
                                getActivity().stopService(trackerIntent);

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
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
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
        String fileName = "run_journal_pic_2018" + System.currentTimeMillis();
        //Grab the directory we want to save the image in
        File directory =
                Environment.
                        getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES);
        File picture  = File.createTempFile(fileName, ".jpg", directory);
        imageLocation = picture.getAbsolutePath();
        return picture;
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
