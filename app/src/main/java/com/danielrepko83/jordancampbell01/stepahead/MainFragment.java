package com.danielrepko83.jordancampbell01.stepahead;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.android.gms.location.LocationRequest;


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

    Intent trackerIntent;
    public static TextView distance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        distance = view.findViewById(R.id.distance);
        TextView distanceLabel = view.findViewById(R.id.distanceLabel);
        TextView duration = view.findViewById(R.id.duration);
        TextView durationLabel = view.findViewById(R.id.durationLabel);
        TextView calories = view.findViewById(R.id.calories);
        TextView caloriesLabel = view.findViewById(R.id.caloriesLabel);
        final Button startRun = view.findViewById(R.id.startRun);
        final Button cancel = view.findViewById(R.id.cancel);
        final Button pause = view.findViewById(R.id.pause);
        final Button finish = view.findViewById(R.id.finish);

        final LocationTracker tracker = new LocationTracker();
        trackerIntent = new Intent(getActivity(), tracker.getClass());

        //Start Run click listener
        startRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide the startRun button and show the pause, finish, and cancel buttons
                startRun.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
                finish.setVisibility(View.VISIBLE);

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
                                //stop tracking location
                                //getActivity().stopService(trackerIntent);

                                tracker.stopTracking();
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

                } else {
                    //if it is paused, then resume recording
                    pause.setText(R.string.home_page_pause_button_text);

                }
            }
        });

        return view;
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
