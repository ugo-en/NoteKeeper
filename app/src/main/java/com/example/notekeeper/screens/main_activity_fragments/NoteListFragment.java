package com.example.notekeeper.screens.main_activity_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.data.Note;
import com.example.notekeeper.adapters.NoteListAdapter;
import com.example.notekeeper.screens.NoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListFragment extends Fragment {
    private View rootView;
    private EditText searchbar;
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
        rootView = inflater.inflate(R.layout.fragment_note_list, container, false);

        addBtn = rootView.findViewById(R.id.add_note_btn);
//        Extras.showToast(getContext(),"Here though!");
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewNoteActivity();
            }
        });
        initDisplayContent();

        searchbar = rootView.findViewById(R.id.note_list_searchbar);
        searchbar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                search();
                return true;
            }
        });
        searchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }

    private void search(){
        String searchTerm = searchbar.getText().toString().trim().toLowerCase();

        if (!searchTerm.isEmpty()){
            List<Note> notes = Note.getAllNotesByName(getContext(),true);
            mNotes.clear();

            for (int i = 0; i < notes.size(); i++){
                Note n = notes.get(i);

                boolean inName = n.getName().toLowerCase().contains(searchTerm);
                boolean inTags = n.getTags().toLowerCase().contains(searchTerm);
                boolean inContent = n.getContent().toLowerCase().contains(searchTerm);
                if (inName || inTags || inContent){
                    mNotes.add(n);
                }
            }

            mNoteRecyclerAdapter.notifyDataSetChanged();
        }
        else{
            Extras.showToast(getContext(),"Enter a search term!");
        }
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
