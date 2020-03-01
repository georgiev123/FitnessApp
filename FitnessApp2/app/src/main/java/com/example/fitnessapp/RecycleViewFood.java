package com.example.fitnessapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecycleViewFood extends RecyclerView.Adapter<RecycleViewFood.MyViewHolder> {

    private ArrayList<String> arrNames;
    private ArrayList<Double> arrGrams;
    private ArrayList<String> arrCalories;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvGrams;
        public TextView tvCalories;
        public MyViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tempTV = v.findViewById(R.id.tvFoodName);
                    ProgramData.foodChoosed = tempTV.getText().toString();
                    v.getContext().startActivity(new Intent(v.getContext(), CustomFoodActivity.class));
                }
            });

            tvName = v.findViewById(R.id.tvFoodName);
            tvGrams = v.findViewById(R.id.tvFoodWeight);
            tvCalories = v.findViewById(R.id.tvFoodCalories);
        }
    }

    public RecycleViewFood(ArrayList<String> names, ArrayList<Double> grams, ArrayList<String> calories) {
        arrNames = names;
        arrGrams = grams;
        arrCalories = calories;
    }

    @Override
    public RecycleViewFood.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View btnFood = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_food_item, parent, false);
        MyViewHolder vh = new MyViewHolder(btnFood);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(arrNames.get(position));
        holder.tvGrams.setText(arrGrams.get(position) + " grams");
        holder.tvCalories.setText("calories\n" + arrCalories.get(position));

    }

    @Override
    public int getItemCount() {
        return arrNames.size();
    }

}
