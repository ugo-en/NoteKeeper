package com.example.notekeeper.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notekeeper.R;
import com.example.notekeeper.data.Note;
import com.example.notekeeper.adapters.NoteListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListActivity extends AppCompatActivity {
    private ListView listNotes;
    private ArrayAdapter<Note> adapterNotes;
    private List<Note> mNotes;
    private NoteListAdapter mNoteRecyclerAdapter;
    private RecyclerView mRecyclerNotes;
    private LinearLayoutManager mNotesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   

        FloatingActionButton fab = findViewById(R.id.add_note_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        initDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDisplayContent();
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initDisplayContent(){
        mRecyclerNotes = findViewById(R.id.notes_recycler);
        mNotesLayoutManager = new LinearLayoutManager(this);
        mRecyclerNotes.setLayoutManager(mNotesLayoutManager);

        mNotes = Note.getAllNotesByDateLastEdited(getBaseContext(),false);
        mNoteRecyclerAdapter = new NoteListAdapter(this, mNotes);
        mRecyclerNotes.setAdapter(mNoteRecyclerAdapter);
    }

}
