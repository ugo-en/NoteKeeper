package com.example.notekeeper.classes;


public abstract class DefaultOption {
    public static final int DEFAULT = 0;
    public static final int TOGGLE = 1;

    private String mainText, subText;
    private int settingType = DEFAULT;

    public DefaultOption(String mainText, String subText, int settingType) {
        this.mainText = mainText;
        this.subText = subText;
        this.settingType = settingType;
    }

    public int getSettingType() {
        return settingType;
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

    abstract public void clickFunction();
}


