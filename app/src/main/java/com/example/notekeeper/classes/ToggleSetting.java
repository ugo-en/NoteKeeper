package com.example.notekeeper.classes;

public abstract class ToggleSetting extends DefaultSetting{
    private boolean isOn;

    public ToggleSetting(String mainText, String subText, int settingType, boolean isOn) {
        super(mainText, subText, settingType);
        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
