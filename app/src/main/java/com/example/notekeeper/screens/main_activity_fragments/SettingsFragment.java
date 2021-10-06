package com.example.notekeeper.screens.main_activity_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.notekeeper.R;
import com.example.notekeeper.data.Note;
import com.example.notekeeper.classes.Setting;
import com.example.notekeeper.adapters.SettingsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment  extends Fragment {
    private RecyclerView mRecycler;

    private ArrayAdapter<Note> adapterNotes;
    private List<Setting> mSettings;
    private SettingsAdapter mSettingsAdapter;
    private LinearLayoutManager mSettingsLayoutManager;

    public SettingsFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecycler = (RecyclerView) inflater.inflate(R.layout.recycler, container, false);
        mSettingsLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mSettingsLayoutManager);

        mSettings = createAllSettings();
        mSettingsAdapter = new SettingsAdapter(getContext(), mSettings);
        mRecycler.setAdapter(mSettingsAdapter);
        return inflater.inflate(R.layout.settings_activity, container, false);
    }
    private ArrayList<Setting> createAllSettings(){
        ArrayList<Setting> settings = new ArrayList<>();
        settings.add(new Setting("Setting 1","Sub Text"));
        return settings;
    }
}