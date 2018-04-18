package com.danielrepko83.jordancampbell01.stepahead;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Picture;
import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.RunJournal;
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

    // TODO: Rename and change types of parameters
    private RunJournal mParam1;

    private OnFragmentInteractionListener mListener;

    public CreateJournalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CreateJournalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateJournalFragment newInstance(Parcelable param1) {
        CreateJournalFragment fragment = new CreateJournalFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }
    RunJournal run;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_journal, container, false);
        MainActivity.fab.hide();

        run = MainFragment.runJournal;

        /*
            Feeling
         */
        ImageButton awesome = (ImageButton) view.findViewById(R.id.awesomeButton);
        ImageButton good = (ImageButton) view.findViewById(R.id.goodButton);
        ImageButton soso = (ImageButton) view.findViewById(R.id.sosoButton);
        ImageButton bad = (ImageButton) view.findViewById(R.id.badButton);
        ImageButton awful = (ImageButton) view.findViewById(R.id.awfulButton);
        final ArrayList<ImageButton> feelingList = new ArrayList<>();
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

        for(int i = 0; i < feelingList.size(); i++){
            final ImageButton button = feelingList.get(i);
            button.setColorFilter(R.color.unselected);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j = 0; j < feelingList.size(); j++){
                        if(feelingList.get(j).equals(button)){
                            feelingList.get(j).setColorFilter(null);
                        } else {
                            feelingList.get(j).setColorFilter(R.color.unselected);
                        }
                    }
                }
            });
        }

        /*
            Area
         */
        final Spinner area = view.findViewById(R.id.areaSpinner);
        String[] areaList = getResources().getStringArray(R.array.create_journal_area_array);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, areaList);
        area.setAdapter(adapter);

        /*
            Heart Rate
         */
        final EditText heartRate = view.findViewById(R.id.heartRate);
        ImageView heartRateHelp = view.findViewById(R.id.help);
        heartRateHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.create_journal_heart_rate_alert_title)
                        .setMessage(R.string.create_journal_heart_rate_message)
                        .setPositiveButton("Ok", null)
                        .show();
            }
        });

        /*
            Note
         */
        final TextView note = view.findViewById(R.id.note);

        /*
            Submit
         */
        Button submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check which image button is "selected"
                for(int i = 0; i < feelingList.size(); i++){
                    if(feelingList.get(i).getColorFilter() == null){
                        switch(i){
                            case 0:
                                run.setFeeling("Awesome");
                                break;
                            case 1:
                                run.setFeeling("Good");
                                break;
                            case 2:
                                run.setFeeling("So so");
                                break;
                            case 3:
                                run.setFeeling("Bad");
                                break;
                            case 4:
                                run.setFeeling("Awful");
                                break;
                            default:
                                break;
                        }
                    }
                }
                //set the area of the run
                run.setArea(area.getSelectedItem().toString());

                //check if the user entered a heart rate
                if(!heartRate.getText().toString().equals("")){
                    //if so, add it to the run
                    run.setHeartRate(Integer.parseInt(heartRate.getText().toString()));
                }

                //check if the user entered a note
                if(!note.getText().toString().equals("")){
                    //if so, add it to the run
                    run.setNote(note.getText().toString());
                }

                DatabaseHandler db = new DatabaseHandler(getContext());
                int runId = db.addRun(run);

                if(runId != -1){
                    ArrayList<String> runPictures = MainFragment.runPictures;
                    if(runPictures.size() != 0) {
                        for (int i = 0; i < runPictures.size(); i++) {
                            Picture pic = new Picture(runPictures.get(i));
                            int picId = db.addPicture(pic);
                            db.addRunPicture(runId, picId);
                        }
                    }
                    db.close();

                    Toast.makeText(getContext(),
                            "Run Journal Created",
                            Toast.LENGTH_SHORT).show();

                    //Bring user to the View Run Page
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction trans = fm.beginTransaction();
                    trans.replace(R.id.content, new MainFragment());
                    trans.commit();

                } else {
                    Toast.makeText(getContext(),
                            "Error Occurred: No Journal Created",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        /*
            Cancel
         */
        Button cancel = view.findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.create_journal_cancel_alert_title)
                        .setMessage(R.string.create_journal_cancel_alert_message)
                        .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction trans = fm.beginTransaction();
                                trans.replace(R.id.content, new MainFragment());
                                trans.commit();
                            }
                        })
                        .setNegativeButton("Stay",null)
                        .show();
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
