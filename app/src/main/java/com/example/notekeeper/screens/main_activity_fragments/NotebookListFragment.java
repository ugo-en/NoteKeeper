package com.example.notekeeper.screens.main_activity_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.data.Notebook;
import com.example.notekeeper.adapters.NotebookListAdapter;
import com.example.notekeeper.screens.NotebookActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotebookListFragment extends Fragment {
    private View rootView;
    private EditText searchbar;
    private FloatingActionButton addBtn;
    private List<Notebook> mNotebooks;
    private NotebookListAdapter mNotebookRecyclerAdapter;
    private RecyclerView mRecyclerNotebooks;
    private LinearLayoutManager mNotebooksLayoutManager;

    public NotebookListFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notebook_list, container, false);

        addBtn = rootView.findViewById(R.id.add_notebook_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewNotebookActivity();
            }
        });

        initDisplayContent();


        searchbar = rootView.findViewById(R.id.notebook_list_searchbar);
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
            List<Notebook> notebooks = Notebook.getAllNotebooks(getContext());
            mNotebooks.clear();

            for (int i = 0; i < notebooks.size(); i++){
                Notebook n = notebooks.get(i);

                boolean inName = n.getName().toLowerCase().contains(searchTerm);
                boolean inDescription = n.getDescription().toLowerCase().contains(searchTerm);

                if (inName || inDescription){
                    mNotebooks.add(n);
                }
            }

            mNotebookRecyclerAdapter.notifyDataSetChanged();
        }
        else{
            Extras.showToast(getContext(),"Enter a search term!");
        }
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

            mRecyclerNotebooks = rootView.findViewById(R.id.notebooks_recycler);
//
            mNotebooksLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerNotebooks.setLayoutManager(mNotebooksLayoutManager);

            mNotebookRecyclerAdapter = new NotebookListAdapter(getContext(), mNotebooks);
            mRecyclerNotebooks.setAdapter(mNotebookRecyclerAdapter);
        }
    }
}