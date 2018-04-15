package com.danielrepko83.jordancampbell01.stepahead;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateJournalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateJournalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateJournalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateJournalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateJournalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateJournalFragment newInstance(String param1, String param2) {
        CreateJournalFragment fragment = new CreateJournalFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_journal, container, false);
        MainActivity.fab.hide();

        /*
            Feeling
         */
        ImageButton awesome = (ImageButton) view.findViewById(R.id.awesomeButton);
        ImageButton good = (ImageButton) view.findViewById(R.id.goodButton);
        ImageButton soso = (ImageButton) view.findViewById(R.id.sosoButton);
        ImageButton bad = (ImageButton) view.findViewById(R.id.badButton);
        ImageButton awful = (ImageButton) view.findViewById(R.id.awfulButton);
        ArrayList<ImageButton> feelingList = new ArrayList<>();
        feelingList.add(awesome);
        feelingList.add(good);
        feelingList.add(soso);
        feelingList.add(bad);
        feelingList.add(awful);

        //setting image for awesome button
        Picasso.with(getContext()).load(R.drawable.awesome_face).resize(175,
                175).centerCrop().into(awesome);
        //setting image for good button
        Picasso.with(getContext()).load(R.drawable.happy_face).resize(175,
                175).centerCrop().into(good);
        //setting image for soso button
        Picasso.with(getContext()).load(R.drawable.soso_face).resize(175,
                175).centerCrop().into(soso);
        //setting image for bad button
        Picasso.with(getContext()).load(R.drawable.sad_face).resize(175,
                175).centerCrop().into(bad);
        //setting image for awful button
        Picasso.with(getContext()).load(R.drawable.hurt_face).resize(175,
                175).centerCrop().into(awful);

        /*
            Area
         */
        Spinner area = view.findViewById(R.id.areaSpinner);

        /*
            Heart Rate
         */
        EditText heartRate = view.findViewById(R.id.heartRate);
        ImageView heartRateHelp = view.findViewById(R.id.help);

        /*
            Note
         */
        TextView note = view.findViewById(R.id.note);

        /*
            Submit
         */
        Button submit = view.findViewById(R.id.submitButton);

        /*
            Cancel
         */
        Button cancel = view.findViewById(R.id.cancelButton);

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
