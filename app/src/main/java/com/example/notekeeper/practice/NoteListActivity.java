package com.example.notekeeper.practice;

import android.content.Intent;
import android.os.Bundle;

import com.example.notekeeper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private ListView listNotes;
    private ArrayAdapter<NoteInfo> adapterNotes;
    private List<NoteInfo> mNotes;
    private NoteListAdapter mNoteRecyclerAdapter;
    private RecyclerView mRecyclerNotes;
    private LinearLayoutManager mNotesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   

        FloatingActionButton fab = findViewById(R.id.add_note_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteListActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });

        initDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNoteRecyclerAdapter.notifyDataSetChanged();

    }

    private void initDisplayContent(){
//        mRecyclerNotes = findViewById(R.id.notes_recycler);
//        mNotesLayoutManager = new LinearLayoutManager(this);
//        mRecyclerNotes.setLayoutManager(mNotesLayoutManager);
//
//        mNotes = DataManager.getInstance().getNotes();
//        mNoteRecyclerAdapter = new NoteListAdapter(this, mNotes);
//        mRecyclerNotes.setAdapter(mNoteRecyclerAdapter);
    }

}
