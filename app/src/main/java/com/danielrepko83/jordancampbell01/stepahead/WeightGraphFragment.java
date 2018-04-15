package com.danielrepko83.jordancampbell01.stepahead;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.ui.widget.TextLabelWidget;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeightGraphFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeightGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightGraphFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    FragmentManager fm;

    XYPlot weightGraph;

    public WeightGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeightGraphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeightGraphFragment newInstance(String param1, String param2) {
        WeightGraphFragment fragment = new WeightGraphFragment();
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
        View view = inflater.inflate(R.layout.fragment_weight_graph, container, false);

        fm = getFragmentManager();

        WeightFragment.swapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WeightFragment.swapButton.setText("View in Graph Mode");
                FragmentTransaction trans = fm.beginTransaction();
                trans.replace(R.id.fragmentStorage, new WeightListFragment());
                trans.commit();
            }
        });

        weightGraph = view.findViewById(R.id.weightGraph);
        DatabaseHandler db = new DatabaseHandler(getContext());

        //Grab all the Weight objects from the database
        ArrayList<Weight> weights = db.getAllWeights();

        //Convert the Weight ArrayList into two ArrayLists, one for the actual weight and one for the id
        ArrayList<Double> weightValues = new ArrayList<>();
        ArrayList<Integer> weightIds = new ArrayList<>();
        for(int i = 0; i < weights.size(); i++) {
            weightValues.add(weights.get(i).getPounds());
            weightIds.add(weights.get(i).getId());
        }

        //Create an XYSeries that uses the values and ids. An XYSeries is a series of points on the graph
        XYSeries weightSeries = new SimpleXYSeries(weightIds, weightValues, "Weight Entries");

        //Create a LineAndPointFormatter to provide to the graph so it knows how to format the information
        LineAndPointFormatter formatter = new LineAndPointFormatter(Color.RED, Color.RED, Color.TRANSPARENT, null);

        //Add the weightSeries to the graph
        weightGraph.addSeries(weightSeries, formatter);
        weightGraph.setDomainStep(StepMode.SUBDIVIDE, 10);
        weightGraph.setRangeStep(StepMode.SUBDIVIDE, 10);

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
