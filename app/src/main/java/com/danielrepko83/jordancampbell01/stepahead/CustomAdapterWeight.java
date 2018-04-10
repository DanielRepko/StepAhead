package com.danielrepko83.jordancampbell01.stepahead;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;

import java.util.ArrayList;

public class CustomAdapterWeight extends RecyclerView.Adapter {
    private ArrayList<Weight> weights;
    Context context;

    public CustomAdapterWeight(ArrayList<Weight> weights) {
        this.weights = weights;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Weight weight = weights.get(position);
        String weightText = weight.getPounds().toString() + " lbs";
        ((CustomViewHolder) holder).weight.setText(weightText);
        ((CustomViewHolder) holder).date.setText(weight.getDate());
    }

    public int getItemCount() {
        if(weights != null){
            return weights.size();
        } else {
            return 0;
        }
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView weight;
        protected TextView date;

        public CustomViewHolder(View view) {
            super(view);
            this.weight = view.findViewById(R.id.weightTextView);
            this.date = view.findViewById(R.id.dateTextView);
        }
    }
}
