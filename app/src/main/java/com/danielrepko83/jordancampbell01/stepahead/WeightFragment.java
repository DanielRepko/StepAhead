package com.danielrepko83.jordancampbell01.stepahead;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText weightEditText;
    static Button swapButton;
    FrameLayout fragmentStorage;

    public WeightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeightFragment newInstance(String param1, String param2) {
        WeightFragment fragment = new WeightFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        //stop LocationTracker if it is running
        getActivity().stopService(new Intent(getActivity(), LocationTracker.class));

        weightEditText = view.findViewById(R.id.weightEditText);
        swapButton = view.findViewById(R.id.swapButton);
        fragmentStorage = view.findViewById(R.id.fragmentStorage);

        MainActivity.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Grab the weight the user entered. If the user didn't enter one, abort weight entry.
                Double enteredNumber;
                try {
                    enteredNumber = Double.parseDouble(weightEditText.getText().toString());
                } catch(Exception e) {
                    return;
                }

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                if(Integer.parseInt(sharedPref.getString("weight_preference", "1")) == 0) {
                    //If the entered value is kilograms, convert to pounds for the database
                    enteredNumber = Double.parseDouble(String.format("%.2f", enteredNumber * 2.2046));
                }

                Date date = Calendar.getInstance().getTime();
                Weight weight = new Weight(enteredNumber, date.toString());

                DatabaseHandler db = new DatabaseHandler(getContext());
                db.addWeight(weight);
                db.close();

                weightEditText.setText(null);

                FragmentTransaction trans = getChildFragmentManager().beginTransaction();
                trans.replace(R.id.fragmentStorage, new WeightListFragment());
                trans.commit();
            }
        });
        MainActivity.fab.setImageResource(R.drawable.ic_add_black_24dp);
        MainActivity.fab.show();

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction trans = getChildFragmentManager().beginTransaction();
        trans.replace(R.id.fragmentStorage, new WeightListFragment());
        trans.commit();
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

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(Integer.parseInt(sharedPref.getString("weight_preference", "1")) == 1) {
            //Pounds
            weightEditText.setHint(R.string.weight_hint_lbs);
        } else {
            //Kilograms
            weightEditText.setHint(R.string.weight_hint_kg);
        }
    }
}
