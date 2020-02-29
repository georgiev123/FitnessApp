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

    public String currentUsername;
    public static int calories;
    public static String exerciseName;
    public static String whichActivity;
    public static String imageExercise;
    public static String userProfile;
    public static Double caloriesIntake = 0.0;
    public static Double carbsIntake = 0.0;
    public static Double proteinsIntake = 0.0;
    public static Double fatsIntake = 0.0;
    public static String whichMeal;
    public static String barcodeScanned;
    public static Boolean openedByBarcodeScanner = false;

    public static final String CHANNEL_ID = "exampleServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
