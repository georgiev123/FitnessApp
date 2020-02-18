package com.example.fitnessapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ProgramData extends Application {

    public String currentUsername;
    public Double calories;
    public static String exerciseName;
    public static String whichActivity;

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getWhichActivity() {
        return whichActivity;
    }

    public void setWhichActivity(String whichActivity) {
        this.whichActivity = whichActivity;
    }



    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }
}
