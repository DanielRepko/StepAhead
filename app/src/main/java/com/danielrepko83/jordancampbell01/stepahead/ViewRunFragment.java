package com.danielrepko83.jordancampbell01.stepahead;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
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

    public TextView distance;
    public TextView distanceLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_run, container, false);
        MainActivity.fab.hide();

        TextView duration = view.findViewById(R.id.duration);
        distance = view.findViewById(R.id.distance);
        distanceLabel = view.findViewById(R.id.distanceLabel);
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
            photoPager.setPageTransformer(true, new DepthPageTransformer());
        } else {
            photoPager.setVisibility(View.GONE);
        }

        //set the text in the fields to the values of the run journal selected
        duration.setText(mParam1.getDuration());
        calories.setText(mParam1.getCalories()+"");

        //set text of distanceLabel and distance according to the measurement selected in settings
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(Integer.parseInt(sharedPref.getString("distance_preference", "0")) == 0) {
            distanceLabel.setText(R.string.home_page_distance_label_km_text);
            distance.setText(mParam1.getDistanceKM()+"");
        } else {
            distanceLabel.setText(R.string.home_page_distance_label_mi_text);
            distance.setText(mParam1.getDistanceMI()+"");
        }


        if(mParam1.getHeartRate() != 0) {
            heartRate.setText(mParam1.getHeartRate() + "");
        } else {
            heartRate.setText("N/A");
        }
        if(mParam1.getFeeling() != null){
            feeling.setText(mParam1.getFeeling());
        } else {
            feeling.setText("N/A");
        }

        if(mParam1.getArea() != null){
            area.setText(mParam1.getArea());
        } else {
            area.setText("N/A");
        }

        if(mParam1.getWeather() != null){
            weather.setText(mParam1.getWeather());
        } else {
            weather.setText("N/A");
        }

        if(mParam1.getStartTime() != null){
            startTime.setText(mParam1.getStartTime());
        } else {
            startTime.setText("N/A");
        }

        if(mParam1.getNote() != null){
            note.setText(mParam1.getNote());
        } else {
            note.setText("N/A");
        }






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

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(Integer.parseInt(sharedPref.getString("distance_preference", "0")) == 0) {
            distanceLabel.setText(R.string.home_page_distance_label_km_text);
            distance.setText(mParam1.getDistanceKM()+"");
        } else {
            distanceLabel.setText(R.string.home_page_distance_label_mi_text);
            distance.setText(mParam1.getDistanceMI()+"");
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
