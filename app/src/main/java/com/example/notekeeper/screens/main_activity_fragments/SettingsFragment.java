package com.example.notekeeper.screens.main_activity_fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.notekeeper.R;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.classes.ToggleSetting;
import com.example.notekeeper.data.Note;
import com.example.notekeeper.classes.DefaultSetting;
import com.example.notekeeper.adapters.SettingsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment  extends Fragment {
    private View rootView;
    private RecyclerView mRecycler;

    private ArrayAdapter<Note> adapterNotes;
    private List<DefaultSetting> mSettings;
    private SettingsAdapter mSettingsAdapter;
    private LinearLayoutManager mSettingsLayoutManager;

    public SettingsFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.settings_activity, container, false);

        mRecycler = rootView.findViewById(R.id.settings_recycler);
        mSettingsLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mSettingsLayoutManager);

        mSettings = createAllSettings();
        mSettingsAdapter = new SettingsAdapter(getContext(), mSettings);
        mRecycler.setAdapter(mSettingsAdapter);
        return rootView;
    }
    private ArrayList<DefaultSetting> createAllSettings(){
        ArrayList<DefaultSetting> settings = new ArrayList<>();
        settings.add(new ToggleSetting("Enable Dark Mode", "Toggle light or dark mode", DefaultSetting.TOGGLE, true) {
            @Override
            public void clickFunction() {
//                if (this.isOn()) {
//                    getContext().setTheme(R.style.DarkTheme);
//                    this.setOn(false);
//                } else {
//                    getActivity().setTheme(R.style.AppTheme);
//                    this.setOn(true);
//                }
//                mSettingsAdapter.notifyDataSetChanged();
                Extras.showToast(getContext(),"Change themes");
            }
        });
        settings.add(new DefaultSetting("Setting 1", "Sub Text", DefaultSetting.DEFAULT) {
            @Override
            public void clickFunction() {
                Extras.showToast(getContext(),"Clicked!");
            }
        });
        return settings;
    }
}