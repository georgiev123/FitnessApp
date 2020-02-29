package com.example.fitnessapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private int positionChange = 0;
    private ArrayList<String> mWeights = new ArrayList<>();
    private ArrayList<String> mRepetitions = new ArrayList<>();
    private ArrayList<String> mExecrcises = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();

    private ArrayList<String> mProductName = new ArrayList<>();
    private ArrayList<String> mCalories = new ArrayList<>();
    private ArrayList<String> mGrams = new ArrayList<>();


    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> firstArr, ArrayList<String> secondArr, ArrayList<String> thirdArr, ArrayList<String> forthArr) {
        this.context = context;

        if(((Activity) context).getLocalClassName().equals("ExerciseHistoryActivity")) {
            this.mWeights= firstArr;
            this.mRepetitions = secondArr;
            this.mExecrcises = thirdArr;
            this.mDates = forthArr;
        }else {
            this.mProductName = firstArr;
            this.mGrams = secondArr;
            this.mCalories = thirdArr;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(((Activity) context).getLocalClassName().equals("ExerciseHistoryActivity")) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_item,parent,false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food_item,parent,false);
        }

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "ONbINDvIEWhOLDER: CALLED");

//        Glide.with(context)
//                .load(mWeights.get(position))
//                .load(mRepetitions.get(position))
//                .load(mExecrcises.get(position))
//                .load(mDates.get(position));

        if(((Activity) context).getLocalClassName().equals("ExerciseHistoryActivity")) {
            holder.weight1.setText(mWeights.get(position+positionChange));
            holder.weight2.setText(mWeights.get(position+1+positionChange));
            holder.weight3.setText(mWeights.get(position+2+positionChange));
            holder.repetition1.setText(mRepetitions.get(position+positionChange));
            holder.repetition2.setText(mRepetitions.get(position+1+positionChange));
            holder.repetition3.setText(mRepetitions.get(position+2+positionChange));
            holder.exercise.setText(mExecrcises.get(position));
            holder.date.setText(mDates.get(position));
            positionChange += 2;
        }else {
            holder.foodName.setText(mProductName.get(position+positionChange));
            holder.foodCalories.setText(mCalories.get(position+positionChange));
            holder.foodWeight.setText(mGrams.get(position+positionChange));
            positionChange += 2;
            holder.foodItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, CustomFoodActivity.class));
                }
            });
        }



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

        TextView foodName;
        TextView foodCalories;
        TextView foodWeight;
        Button foodItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if(((Activity) context).getLocalClassName().equals("ExerciseHistoryActivity")) {
                weight1 = itemView.findViewById(R.id.weight1);
                weight2 = itemView.findViewById(R.id.weight2);
                weight3 = itemView.findViewById(R.id.weight3);
                repetition1 = itemView.findViewById(R.id.repetition1);
                repetition2 = itemView.findViewById(R.id.repetition2);
                repetition3 = itemView.findViewById(R.id.repetition3);
                date = itemView.findViewById(R.id.date);
                exercise = itemView.findViewById(R.id.tvExName);
                parentLayout = itemView.findViewById(R.id.parent_layout);
            }else {
                foodName = itemView.findViewById(R.id.tvFoodName);
                foodCalories = itemView.findViewById(R.id.tvFoodCalories);
                foodWeight = itemView.findViewById(R.id.tvFoodWeight);
                foodItem = itemView.findViewById(R.id.btnFoodItem);
            }

        }
    }
}
