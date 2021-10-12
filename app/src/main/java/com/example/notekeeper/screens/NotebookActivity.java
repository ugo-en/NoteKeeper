package com.example.notekeeper.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notekeeper.R;
import com.example.notekeeper.data.Notebook;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.data.Setting;

public class NotebookActivity extends AppCompatActivity {

    public static final String NOTEBOOK_ID = "com.example.notekeeper.NOTEBOOK_ID";
    public static int NULL_NOTEBOOK_ID = -1;

    private EditText nameView, descriptionView;
    private Button btn;

    private int notebookId = 0;
    private int currentNoteInEdit;

    private boolean isCreating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_activity);

        nameView = findViewById(R.id.new_notebook_name);
        descriptionView = findViewById(R.id.new_notebook_description);

        btn = findViewById(R.id.new_note_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFunction();
            }
        });
        btn.setText(notebookId==NULL_NOTEBOOK_ID?"Create":"Save");

        Intent intent = getIntent();

        notebookId = intent.getIntExtra(NOTEBOOK_ID, NULL_NOTEBOOK_ID);
        currentNoteInEdit = intent.getIntExtra(NoteActivity.NOTE_ID, NULL_NOTEBOOK_ID);

        isCreating = notebookId==NULL_NOTEBOOK_ID;

        setDisplayValues();
    }

    private void setDisplayValues(){
        Notebook notebook = Notebook.getNotebookById(getBaseContext(),notebookId);

        // if it's null, then it's a new notebook
        if (notebook!=null){
            nameView.setText(notebook.getName());
            descriptionView.setText(notebook.getDescription());
        }
    }

    private void showTheme(){
        Setting theme = Extras.getTheme(getBaseContext());
        setTheme(theme.getValue().equals(Setting.THEME_LIGHT)?R.style.AppTheme:R.style.DarkTheme);
    }


    private void btnFunction(){
        String name = nameView.getText().toString().trim();
        String description = descriptionView.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()){
            Extras.showToast(getBaseContext(),"Enter all fields!");
        }
        else{
            Notebook notebook;

            if (isCreating){
                notebook = Notebook.createNotebook(getBaseContext(),name,description);
                if (notebook == null){
                    Extras.showToast(getBaseContext(),"An error occurred while creating this notebook!");
                    return;
                }
                else{
                    Extras.showToast(getBaseContext(),"Successfully created this notebook!");
                    moveBackToNoteActivity(isCreating, notebookId);
                }
            }
            else{
                notebook = Notebook.getNotebookById(getBaseContext(),notebookId);
                notebook.setName(name);
                notebook.setDescription(description);
                notebook.save(getBaseContext());
                Extras.showToast(getBaseContext(),"This notebook has been saved!");
                moveToMainActivity(isCreating, notebookId);
            }
        }
    }

    private void moveToMainActivity(boolean isCreating, int notebookId){
        Intent intent = new Intent(NotebookActivity.this,MainActivity.class);
        if (isCreating){
            intent.putExtra(NoteActivity.CREATED_NOTEBOOK_ID,notebookId);
        }
        startActivity(intent);
    }

    private void moveBackToNoteActivity(boolean isCreating, int notebookId){
        Intent intent = new Intent(NotebookActivity.this,MainActivity.class);
        if (isCreating){
            intent.putExtra(NoteActivity.CREATED_NOTEBOOK_ID,notebookId);
            if (currentNoteInEdit != NULL_NOTEBOOK_ID){
                intent.putExtra(NoteActivity.NOTE_ID,currentNoteInEdit);
            }
        }
        startActivity(intent);
    }
}