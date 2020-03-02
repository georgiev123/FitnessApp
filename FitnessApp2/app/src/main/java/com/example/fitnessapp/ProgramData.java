package com.example.fitnessapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ProgramData extends Application {

    public static String exerciseName;
    public static String whichActivity;
    public static String imageExercise;
    public static String userProfile;
    public static Double caloriesIntake = 0.0;
    public static Double carbsIntake = 0.0;
    public static Double proteinsIntake = 0.0;
    public static Double fatsIntake = 0.0;
    public static String whichMeal;
    public static Boolean addMeal = false;
    public static Boolean doRestart = false;
    public static String foodChoosed = "";
    public static Integer lastStepAchievement = 0;
    public static Integer exercisesCount = 0;
    public static Integer exercisesToAchievement = 1;

    public static final String CHANNEL_ID = "exampleServiceChannel";


    public static void clear() {
        exerciseName = "";
        whichActivity = "";
        imageExercise = "";
        userProfile = "";
        caloriesIntake = 0.0;
        carbsIntake = 0.0;
        proteinsIntake = 0.0;
        fatsIntake = 0.0;
        whichMeal = "";
        addMeal = false;
        doRestart = false;
        foodChoosed = "";
        lastStepAchievement = 0;
        exercisesCount = 0;
        exercisesToAchievement = 1;

    }
}
