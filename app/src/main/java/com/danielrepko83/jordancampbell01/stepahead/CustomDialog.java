package com.danielrepko83.jordancampbell01.stepahead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by awsom on 4/20/2018.
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

            }
        });

        noThanks = (Button) findViewById(R.id.noThanks);

    }

}
