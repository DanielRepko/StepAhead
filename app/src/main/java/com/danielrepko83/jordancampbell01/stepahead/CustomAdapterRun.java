package com.danielrepko83.jordancampbell01.stepahead;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.RunJournal;

import java.util.ArrayList;

/**
 * Created by Daniel Repko on 4/16/2018.
 */

public class CustomAdapterRun extends RecyclerView.Adapter {
    private ArrayList<RunJournal> runs;
    Context context;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(runs != null){
            return runs.size();
        } else {
            return 0;
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView distanceLabel;
        protected TextView distance;
        protected TextView duration;
        protected TextView calories;

        public CustomViewHolder(View view){
            super(view);
            this.distanceLabel = (TextView) view.findViewById(R.id.distanceLabel);
            this.distance = (TextView) view.findViewById(R.id.distance);
            this.duration = (TextView) view.findViewById(R.id.duration);
            this.calories = (TextView) view.findViewById(R.id.calories);
        }
    }
}
