package com.danielrepko83.jordancampbell01.stepahead;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Picture;
import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.RunJournal;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewRunFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewRunFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewRunFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private RunJournal mParam1;

    private OnFragmentInteractionListener mListener;

    public ViewRunFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ViewRunFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewRunFragment newInstance(Parcelable param1) {
        ViewRunFragment fragment = new ViewRunFragment();
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

    ArrayList<Picture> picList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_run, container, false);
        MainActivity.fab.hide();

        TextView duration = view.findViewById(R.id.duration);
        TextView distance = view.findViewById(R.id.distance);
        TextView calories = view.findViewById(R.id.calories);
        TextView heartRate = view.findViewById(R.id.heartRate);
        TextView feeling = view.findViewById(R.id.feeling);
        TextView area = view.findViewById(R.id.area);
        TextView weather = view.findViewById(R.id.weather);
        TextView startTime = view.findViewById(R.id.startTime);
        TextView note = view.findViewById(R.id.note);
        ViewPager photoPager = view.findViewById(R.id.photoPager);


        DatabaseHandler db = new DatabaseHandler(getContext());
        picList = db.getRunPictures(mParam1.getId());
        db.close();
        if(picList.size() != 0) {
            final CustomPagerAdapter adapter = new CustomPagerAdapter(getChildFragmentManager());
            photoPager.setAdapter(adapter);
        }

        //set the text in the fields to the values of the run journal selected
        duration.setText(mParam1.getDuration());
        distance.setText(mParam1.getDistanceKM()+"");
        calories.setText(mParam1.getCalories()+"");
        heartRate.setText(mParam1.getHeartRate()+"");
        feeling.setText(mParam1.getFeeling());
        area.setText(mParam1.getArea());
        weather.setText(mParam1.getWeather());
        startTime.setText(mParam1.getStartTime());
        note.setText(mParam1.getNote());

        return view;
    }

    public class CustomPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Picture> photoList = picList;

        public CustomPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /**
             * Strings are not only stored in arrays, but are also stored in
             * the order that they are to appear.
             * This makes it possible to do away with the usual switch statement
             * and simply pull the values from the array by using the position in the
             * ViewPager as the desired index location in the array
             */
            try {
                return RunPhotoFragment.newInstance(picList.get(position).getResource());
            } catch(NullPointerException e){
                e.printStackTrace();
                return RunPhotoFragment.newInstance("");
            }
        }

        @Override
        public int getCount() {
            return picList.size();
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
