package com.example.notekeeper.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.notekeeper.R;
import com.example.notekeeper.screens.main_activity_fragments.NoteListFragment;
import com.example.notekeeper.screens.main_activity_fragments.NotebookListFragment;
import com.example.notekeeper.screens.main_activity_fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    NoteListFragment noteListFragment = new NoteListFragment();
    NotebookListFragment notebookListFragment = new NotebookListFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.note);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, noteListFragment).commit();
                return true;
            case R.id.notebooks:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, notebookListFragment).commit();
                return true;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingsFragment).commit();
                return true;
        }
        return false;
    }
}