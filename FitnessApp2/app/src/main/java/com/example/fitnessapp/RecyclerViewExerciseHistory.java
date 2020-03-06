package com.example.fitnessapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecyclerViewExerciseHistory extends RecyclerView.Adapter<RecyclerViewExerciseHistory.ViewHolder>{

    private static final String TAG = "RecyclerViewExerciseHistory";

    private int positionChange = 0;
    private ArrayList<String> mWeights = new ArrayList<>();
    private ArrayList<String> mRepetitions = new ArrayList<>();
    private ArrayList<String> mExecrcises = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private Context context;

    public RecyclerViewExerciseHistory(Context context, ArrayList<String> firstArr, ArrayList<String> secondArr,
                                       ArrayList<String> thirdArr, ArrayList<String> forthArr) {
        this.context = context;

        if(((Activity) context).getLocalClassName().equals("ExerciseHistoryActivity")) {
            this.mWeights= firstArr;
            this.mRepetitions = secondArr;
            this.mExecrcises = thirdArr;
            this.mDates = forthArr;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_item,parent,false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.weight1.setText(mWeights.get(position+positionChange));
        holder.weight2.setText(mWeights.get(position+1+positionChange));
        holder.weight3.setText(mWeights.get(position+2+positionChange));
        holder.repetition1.setText(mRepetitions.get(position+positionChange));
        holder.repetition2.setText(mRepetitions.get(position+1+positionChange));
        holder.repetition3.setText(mRepetitions.get(position+2+positionChange));
        holder.exercise.setText(mExecrcises.get(position));
        holder.date.setText(mDates.get(position));
        positionChange += 2;

    }

    @Override
    public int getItemCount() {
        return mExecrcises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView weight1;
        TextView weight2;
        TextView weight3;
        TextView repetition1;
        TextView repetition2;
        TextView repetition3;
        TextView date;
        TextView exercise;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weight1 = itemView.findViewById(R.id.weight1);
            weight2 = itemView.findViewById(R.id.weight2);
            weight3 = itemView.findViewById(R.id.weight3);
            repetition1 = itemView.findViewById(R.id.repetition1);
            repetition2 = itemView.findViewById(R.id.repetition2);
            repetition3 = itemView.findViewById(R.id.repetition3);
            date = itemView.findViewById(R.id.date);
            exercise = itemView.findViewById(R.id.tvExName);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
