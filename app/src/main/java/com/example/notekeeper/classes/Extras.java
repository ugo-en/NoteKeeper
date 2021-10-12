package com.example.notekeeper.classes;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.notekeeper.data.Setting;

public class Extras {
    public static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static Setting getTheme(Context context){
        Setting theme = Setting.getSetting(context,Setting.THEME_KEY);
        if (theme == null){
            theme = Setting.saveAndRetrieve(context,Setting.THEME_KEY,Setting.THEME_LIGHT);
        }
        return theme;
    }
}
