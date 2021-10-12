package com.example.notekeeper.screens.main_activity_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notekeeper.R;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.classes.ToggleSetting;
import com.example.notekeeper.classes.DefaultSetting;
import com.example.notekeeper.adapters.SettingsAdapter;
import com.example.notekeeper.data.Setting;
import com.example.notekeeper.screens.DeveloperContactActivity;
import com.example.notekeeper.screens.MainActivity;
import com.example.notekeeper.screens.PrivacyPolicyActivity;

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

        settings.add(
            new ToggleSetting("Enable Dark Mode", "Toggle light or dark mode.", DefaultSetting.TOGGLE, Extras.getTheme(getContext()).getValue().equals(Setting.THEME_DARK)) {
                @Override
                public void clickFunction() {
                    Extras.showToast(getContext(),String.valueOf(this.isOn()));
                    this.setOn(!this.isOn());
                    try{
                        Setting.saveAndRetrieve(getContext(),Setting.THEME_KEY,this.isOn()?Setting.THEME_DARK:Setting.THEME_LIGHT);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                    catch (Exception ex){
                        Extras.showToast(getContext(),"Cannot change this theme!");
                    }
                }
            }
        );

        settings.add(
            new DefaultSetting("Privacy Policy", "View our privacy statement.", DefaultSetting.DEFAULT) {
                @Override
                public void clickFunction() {
                    Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
                    startActivity(intent);
                }
            }
        );

        settings.add(
            new DefaultSetting("Developer Contact", "Click to see developer contact", DefaultSetting.DEFAULT) {
                @Override
                public void clickFunction() {
                    Intent intent = new Intent(getActivity(), DeveloperContactActivity.class);
                    startActivity(intent);
                }
            }
        );
        return settings;
    }
}