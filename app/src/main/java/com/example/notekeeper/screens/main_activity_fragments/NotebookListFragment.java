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
import com.example.notekeeper.data.Notebook;
import com.example.notekeeper.adapters.NotebookListAdapter;
import com.example.notekeeper.screens.NoteActivity;
import com.example.notekeeper.screens.NotebookActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotebookListFragment extends Fragment {
    private View rootView;
    private FloatingActionButton addBtn;
    private List<Notebook> mNotebooks;
    private NotebookListAdapter mNoteRecyclerAdapter;
    private RecyclerView mRecyclerNotebooks;
    private LinearLayoutManager mNotesLayoutManager;

    public NotebookListFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.note_list_activity, container, false);
        addBtn = rootView.findViewById(R.id.add_note_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewNotebookActivity();
            }
        });
        initDisplayContent();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initDisplayContent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDisplayContent();
    }

    private void moveToNewNotebookActivity(){
        Intent intent = new Intent(getContext(), NotebookActivity.class);
        startActivity(intent);
    }

    private void initDisplayContent(){
        if (rootView != null){
            mNotebooks = Notebook.getAllNotebooks(getContext());

            mRecyclerNotebooks = rootView.findViewById(R.id.notes_recycler);
//
            mNotesLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerNotebooks.setLayoutManager(mNotesLayoutManager);

            mNoteRecyclerAdapter = new NotebookListAdapter(getContext(), mNotebooks);
            mRecyclerNotebooks.setAdapter(mNoteRecyclerAdapter);
        }
    }
}