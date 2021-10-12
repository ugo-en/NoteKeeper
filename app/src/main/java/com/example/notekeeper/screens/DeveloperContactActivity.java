package com.example.notekeeper.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.notekeeper.R;
import com.example.notekeeper.adapters.DeveloperContactsAdapter;
import com.example.notekeeper.adapters.SettingsAdapter;
import com.example.notekeeper.classes.DefaultSetting;
import com.example.notekeeper.classes.DeveloperContact;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.data.Setting;

import java.util.ArrayList;
import java.util.List;

public class DeveloperContactActivity extends AppCompatActivity {
    private List<DeveloperContact> mDeveloperContacts;

    private RecyclerView mRecycler;
    private DeveloperContactsAdapter mDeveloperContactsAdapter;
    private LinearLayoutManager mDeveloperContactsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_contact);

        mDeveloperContacts = createAllContacts();
        mDeveloperContactsAdapter = new DeveloperContactsAdapter(getBaseContext(), mDeveloperContacts);
        mRecycler = findViewById(R.id.developer_contact_recycler);
        mDeveloperContactsLayoutManager = new LinearLayoutManager(getBaseContext());

        mRecycler.setLayoutManager(mDeveloperContactsLayoutManager);
        mRecycler.setAdapter(mDeveloperContactsAdapter);
        mDeveloperContactsAdapter.notifyDataSetChanged();
    }

    private List<DeveloperContact> createAllContacts() {
        ArrayList<DeveloperContact> contacts = new ArrayList<>();
        contacts.add(new DeveloperContact("Website","https://ugo.pythonanywhere.com/",DeveloperContact.TYPE_DEFAULT));
        contacts.add(new DeveloperContact("Email","ugochukwuenwachukwu@gmail.com",DeveloperContact.TYPE_EMAIL));
        contacts.add(new DeveloperContact("LinkedIn","https://www.linkedin.com/in/ugochukwu-nwachukwu-495887205/",DeveloperContact.TYPE_DEFAULT));
        contacts.add(new DeveloperContact("Facebook","https://web.facebook.com/ugochukwu.nwachukwu.3110/",DeveloperContact.TYPE_DEFAULT));
        return contacts;
    }

    private void showTheme(){
        Setting theme = Extras.getTheme(getBaseContext());
        setTheme(theme.getValue().equals(Setting.THEME_LIGHT)?R.style.AppTheme:R.style.DarkTheme);
    }
}