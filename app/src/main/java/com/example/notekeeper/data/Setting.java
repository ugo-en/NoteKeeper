package com.example.notekeeper.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.notekeeper.classes.Extras;

public class Setting {
    public static final String THEME_KEY = "theme";
    public static final String THEME_LIGHT = "L";
    public static final String THEME_DARK = "D";

    private String key, value;

    public Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    private boolean update(Context context){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.updateSetting(this);
    }

    public boolean delete(Context context){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.deleteSetting(this);
    }

    private static Setting createSetting(Context context, String key, String value){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.createSetting(key, value);
    }

    public static Setting getSetting(Context context, String key){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getSetting(key);
    }

    public static Setting saveAndRetrieve(Context context, String key, String value){
        Setting setting = getSetting(context, key);

        if (setting==null){
            // no such Setting exists
            try{
                setting = Setting.createSetting(context, key, value);
            }
            catch (SQLiteConstraintException ex){
                setting = new Setting(key,value);
                setting.update(context);
            }
        }
        else{
            setting.setKey(key);
            setting.setValue(value);
            setting.update(context);
        }
        return setting;
    }
}
