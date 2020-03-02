package com.example.fitnessapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewFolowee extends RecyclerView.Adapter<RecyclerViewFolowee.MyViewHolder> {

    private ArrayList<String> arrFolloweeNames;
    private ArrayList<String> arrFolloweeActivity;
    private ArrayList<String> arrActivityKind;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNames;
        public TextView tvActivities;
        public MyViewHolder(View v) {
            super(v);
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    TextView tempTV = v.findViewById(R.id.tvFoodName);
//                    ProgramData.foodChoosed = tempTV.getText().toString();
//                    v.getContext().startActivity(new Intent(v.getContext(), CustomFoodActivity.class));
//                }
//            });

            tvNames = v.findViewById(R.id.tvFolloweeName);
            tvActivities = v.findViewById(R.id.tvFolloweeActivity);
        }
    }

    public RecyclerViewFolowee(ArrayList<String> arrFolloweeNames, ArrayList<String> arrFolloweeActivity, ArrayList<String> arrActivityKind) {
        this.arrFolloweeNames = arrFolloweeNames;
        this.arrFolloweeActivity = arrFolloweeActivity;
        this.arrActivityKind = arrActivityKind;
    }

    @Override
    public RecyclerViewFolowee.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View followeeAct = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_folowee_item, parent, false);
        MyViewHolder vh = new MyViewHolder(followeeAct);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewFolowee.MyViewHolder holder, int position) {
        holder.tvNames.setText(arrFolloweeNames.get(position));
        if(arrActivityKind.get(position).equals("Pedometer")) {
            holder.tvActivities.setText("Walked\n" + arrFolloweeActivity.get(position) + " steps.");
        }else {
            holder.tvActivities.setText("Did " + arrFolloweeActivity.get(position) + " workouts today.");
        }

    }

    @Override
    public int getItemCount() {
        return arrFolloweeNames.size();
    }
}
