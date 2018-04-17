package com.danielrepko83.jordancampbell01.stepahead;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;

import java.util.ArrayList;

public class CustomAdapterWeight extends RecyclerView.Adapter {
    private ArrayList<Weight> weights;
    Context context;
    SharedPreferences sharedPref;

    public CustomAdapterWeight(ArrayList<Weight> weights) {
        this.weights = weights;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weight_recycler_view, parent, false);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);
        context = parent.getContext();

        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Weight Entry")
                        .setMessage("WARNING: Are you sure you want to delete this weight entry? This cannot be undone.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int weight = viewHolder.getAdapterPosition();
                                DatabaseHandler db = new DatabaseHandler(context);
                                db.deleteWeight(weights.get(weight).getId());
                                weights.remove(weight);
                                notifyItemRemoved(weight);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return false;
            }
        });

        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Weight weight = weights.get(position);
        String weightText;
        if(Integer.parseInt(sharedPref.getString("weight_preference", "1")) == 1) {
            weightText = weight.getPounds().toString() + " lbs";
        } else {
            Double weightPounds = weight.getPounds() * (1 / 2.2046);
            weightText = Double.toString(weightPounds) + " kg";
        }

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
