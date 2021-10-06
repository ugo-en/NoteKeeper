package com.example.notekeeper.classes;

import android.content.Context;
import android.widget.Toast;

public class Extras {
    public static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
