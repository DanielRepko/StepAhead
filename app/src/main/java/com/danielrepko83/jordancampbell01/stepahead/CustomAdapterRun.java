package com.danielrepko83.jordancampbell01.stepahead;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    public CustomAdapterRun(ArrayList<RunJournal> runs) {
        this.runs = runs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_recycler_item, parent, false);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);
        context = parent.getContext();

        view.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.run_history_page_alert_title)
                        .setMessage(R.string.run_history_page_alert_message)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int run = viewHolder.getAdapterPosition();
                                DatabaseHandler db = new DatabaseHandler(context);
                                db.deleteRun(runs.get(run).getId());
                                runs.remove(run);
                                notifyItemRemoved(run);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return false;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RunJournal runJournal = runs.get(position);
        ((CustomViewHolder) holder).distance.setText(runJournal.getDistanceKM()+"");
        ((CustomViewHolder) holder).duration.setText(runJournal.getDuration());
        ((CustomViewHolder) holder).calories.setText(runJournal.getCalories()+"");
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
