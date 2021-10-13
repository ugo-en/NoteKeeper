package com.example.notekeeper.classes;

public abstract class ToggleOption extends DefaultOption {
    private boolean isOn;

    public ToggleOption(String mainText, String subText, int settingType, boolean isOn) {
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
