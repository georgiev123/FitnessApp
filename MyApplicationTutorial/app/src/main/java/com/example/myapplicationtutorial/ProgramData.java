package com.example.myapplicationtutorial;

import android.app.Application;

public class ProgramData extends Application {
    public Boolean regScreenOpened = Boolean.FALSE;

    public void setRegScreenOpened(Boolean regScreenOpened) {
        this.regScreenOpened = regScreenOpened;
    }

    public Boolean getRegScreenOpened() {
        return regScreenOpened;
    }
}
