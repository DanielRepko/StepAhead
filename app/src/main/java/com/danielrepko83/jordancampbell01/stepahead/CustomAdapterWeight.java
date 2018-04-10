package com.danielrepko83.jordancampbell01.stepahead;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

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
        
    }

    public int getItemCount() {
        if(weights != null){
            return weights.size();
        } else {
            return 0;
        }
    }
}