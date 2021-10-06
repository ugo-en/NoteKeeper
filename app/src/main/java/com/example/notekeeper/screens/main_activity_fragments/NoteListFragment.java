package com.example.notekeeper.screens.main_activity_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notekeeper.R;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.data.Note;
import com.example.notekeeper.adapters.NoteListAdapter;
import com.example.notekeeper.screens.MainActivity;
import com.example.notekeeper.screens.NoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListFragment extends Fragment {
    private View rootView;
    private List<Note> mNotes;
    private NoteListAdapter mNoteRecyclerAdapter;
    private RecyclerView mRecyclerNotes;
    private FloatingActionButton addBtn;
    private LinearLayoutManager mNotesLayoutManager;

    public NoteListFragment(){
        // require a empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDisplayContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        initDisplayContent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.note_list_activity, container, false);

        addBtn = rootView.findViewById(R.id.add_note_btn);
//        Extras.showToast(getContext(),"Here though!");
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewNoteActivity();
            }
        });
        initDisplayContent();
        return rootView;
    }

    private void moveToNewNoteActivity(){
        Intent intent = new Intent(getContext(), NoteActivity.class);
        startActivity(intent);
    }

    private void initDisplayContent(){
        if (rootView != null){
            mNotes = Note.getAllNotesByDateLastEdited(getContext(), false);

            mRecyclerNotes = rootView.findViewById(R.id.notes_recycler);
//
            mNotesLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerNotes.setLayoutManager(mNotesLayoutManager);

            mNoteRecyclerAdapter = new NoteListAdapter(getContext(), mNotes);
            mRecyclerNotes.setAdapter(mNoteRecyclerAdapter);
        }
    }

}
