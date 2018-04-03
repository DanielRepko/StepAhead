package com.danielrepko83.jordancampbell01.stepahead;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReminderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReminderFragment newInstance(String param1, String param2) {
        ReminderFragment fragment = new ReminderFragment();
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
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        //Grab the current elements on the page
        final Spinner typeSpinner = view.findViewById(R.id.typeSpinner);
        final EditText dateEditText = view.findViewById(R.id.dateEditText);
        final EditText timeEditText = view.findViewById(R.id.timeEditText);
        final EditText descriptionEditText = view.findViewById(R.id.descriptionEditText);
        Button submitButton = view.findViewById(R.id.submitButton);

        //Create an ArrayList of Run types, and fill it with the values "Run" and "Weight Check"
        final ArrayList<String> runTypeArrayList = new ArrayList<>();
        runTypeArrayList.add("Run");
        runTypeArrayList.add("Weight Check");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, runTypeArrayList);
        typeSpinner.setAdapter(adapter);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String formattedDate = "";
                                if(month < 9) {
                                    formattedDate += "0" + (month + 1) + "/" + dayOfMonth + "/" + year;
                                } else {
                                    formattedDate += (month + 1) + "/" + dayOfMonth + "/" + year;
                                }
                                dateEditText.setText(formattedDate);
                            }
                        },
                        year,
                        month,
                        day);
                dialog.show();
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String formattedTime = "";
                                if(hourOfDay > 12) {
                                    formattedTime += hourOfDay-12 + ":";
                                } else if(hourOfDay == 0) {
                                    formattedTime += hourOfDay+12 + ":";
                                } else {
                                    formattedTime += hourOfDay + ":";
                                }

                                if(minute < 10) {
                                    formattedTime += "0" + minute;
                                } else {
                                    formattedTime += minute;
                                }

                                if(hourOfDay >= 12) {
                                    formattedTime += " PM";
                                } else {
                                    formattedTime += " AM";
                                }

                                timeEditText.setText(formattedTime);
                            }
                        },
                        hour,
                        minute,
                        false);
                dialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Grab the selected run type
                int runTypeId = typeSpinner.getSelectedItemPosition();
                String selectedRunType = runTypeArrayList.get(runTypeId);

                //Grab the date the user selected, and split it into an array
                //0 is month, 1 is day, 2 is year
                String selectedDate = dateEditText.getText().toString();

                //Make sure the user actually set a date. Otherwise, display an alert and abort
                if(selectedDate.isEmpty()) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Error")
                            .setMessage("Please select a date for the reminder.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNeutralButton("Ok", null)
                            .show();
                    return;
                }

                String[] dateArray = selectedDate.split("/");

                //Convert the dateArray to ints
                ArrayList<Integer> dateArrayInt = new ArrayList<>();
                dateArrayInt.add(Integer.parseInt(dateArray[0]));
                dateArrayInt.add(Integer.parseInt(dateArray[1]));
                dateArrayInt.add(Integer.parseInt(dateArray[2]));

                //Grab the time, and spllit it into an array
                //0 is hour, 1 is minute, 2 is AM/PM
                String selectedTime = timeEditText.getText().toString();
                String[] timeArray;
                ArrayList<Integer> timeArrayInt = new ArrayList<>();

                //If the user didn't set a time, skip all the time based code.
                //It's an optional field that the user doesn't have to enter
                if(!selectedTime.isEmpty()) {
                    timeArray = selectedTime.split(":| ");

                    //Convert the timeArray to ints
                    timeArrayInt.add(Integer.parseInt(timeArray[0]));
                    timeArrayInt.add(Integer.parseInt(timeArray[1]));

                    //If the AM/PM value is PM, add 12 to the hour value, unless the value is already 12.
                    if((timeArray[2].equals("PM") && timeArrayInt.get(0) < 12)) {
                        timeArrayInt.set(0, (timeArrayInt.get(0) + 12));
                    }

                    if((timeArray[2].equals("AM") && timeArrayInt.get(0) == 12)) {
                        timeArrayInt.set(0, (timeArrayInt.get(0) - 12));
                    }

                    //If the AM/PM value is AM and the hour is 12, subtract 12
                }

                //Grab the description the user set
                //We don't need to format this one, but it's not an issue if the user didn't set it.
                String selectedDescription = descriptionEditText.getText().toString();

                //We have everything we need, let's make the intent!
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, selectedRunType);

                //We need to set the date/time using milliseconds from epoch.
                //Convert the date to epoch, and if set, convert the time to epoch and add it
                //If the time is not set, set this as an all day event
                /*
                    NOTICE:
                    LEAVE THE -1900. FOR SOME REASON THE YEAR PROPERTY OF DATE ISN'T THE YEAR YOU WANT. IT'S NUMBER OF YEARS AFTER EPOCH.
                    IF YOU WANT IT TO BE THE DATE YOU WANT, YOU HAVE TO SUBTRACT THE YEAR OF EPOCH, AKA 1900.
                    IT TOOK ME AN HOUR TO REALIZE THIS.
                 */
                Date date;
                if(selectedTime.isEmpty()) {
                    date = new Date(dateArrayInt.get(2) - 1900, dateArrayInt.get(0) - 1, dateArrayInt.get(1));
                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                } else {
                    date = new Date(dateArrayInt.get(2) - 1900, dateArrayInt.get(0) - 1, dateArrayInt.get(1), timeArrayInt.get(0), timeArrayInt.get(1));
                }
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTime());

                //If a description was set, add it to the intent
                if(!selectedDescription.isEmpty()) {
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, selectedDescription);
                }

                getContext().startActivity(intent);
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
