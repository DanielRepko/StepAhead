package com.danielrepko83.jordancampbell01.stepahead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel Repko on 4/20/2018.
 */

public class CustomDialog extends Dialog {

    private Activity activity;
    private Button submit, noThanks;
    private RadioButton kg, lbs;
    private EditText weight;

    public CustomDialog(Activity activity){
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_weight_check_dialog);

        kg = findViewById(R.id.kgOption);
        lbs = findViewById(R.id.poundsOption);
        weight = findViewById(R.id.weight);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check to see if a value has been entered
                if(weight.getText().toString().equals("")){
                    Toast.makeText(getContext(),
                            "Please enter a weight.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //if kg is checked
                    if(kg.isChecked()){
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        Weight newWeight = new Weight(Double.parseDouble(String.format("%.2f",Double.parseDouble(weight.getText().toString()) * 2.2046)), Calendar.getInstance().getTime()+"");
                        db.addWeight(newWeight);
                        db.close();

                        //dismiss the dialog
                        dismiss();

                    } else if(lbs.isChecked()){
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        Weight newWeight = new Weight(Double.parseDouble(weight.getText().toString()), Calendar.getInstance().getTime()+"");
                        db.addWeight(newWeight);
                        db.close();

                        //dismiss the dialog
                        dismiss();
                    } else {
                        Toast.makeText(getContext(),
                                "Please select a weight measurement",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        noThanks = (Button) findViewById(R.id.noThanks);
        noThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

}
