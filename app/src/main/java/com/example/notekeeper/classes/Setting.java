package com.example.notekeeper.classes;

import android.os.Handler;

public class Setting {
    private String mainText, subText;

    public Setting(String mainText, String subText) {
        this.mainText = mainText;
        this.subText = subText;
    }

    public String getMainText() {
        return mainText;
    }

    public String getSubText() {
        return subText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }
}
