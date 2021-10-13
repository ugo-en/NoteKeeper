package com.example.notekeeper.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.data.Notebook;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.data.Setting;

public class NotebookActivity extends AppCompatActivity {

    public static final String NOTEBOOK_ID = "com.example.notekeeper.NOTEBOOK_ID";
    public static int NULL_NOTEBOOK_ID = -1;

    private EditText nameView, descriptionView;
    private Toolbar toolbar;
    private Button btn;

    private int notebookId = 0;
    private int currentNoteInEdit;

    private boolean isCreating = false;

    private String prevNoteName, prevNoteTags, prevNoteContent;
    private int prevNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_notebook);
        setSupportActionBar(toolbar);

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
        isCreating = notebookId==NULL_NOTEBOOK_ID;

        setDisplayValues();
        setPrevNoteItems();
    }

    private void setDisplayValues(){
        Notebook notebook = Notebook.getNotebookById(getBaseContext(),notebookId);

        // if it's null, then it's a new notebook
        if (notebook!=null){
            toolbar.setTitle(notebook.getName());
            nameView.setText(notebook.getName());
            descriptionView.setText(notebook.getDescription());
        }
    }

    private void setPrevNoteItems(){
        Intent intent = getIntent();
        prevNoteId = intent.getIntExtra(NoteActivity.NOTE_ID,NoteActivity.NOTE_ID_NOT_FOUND);
        prevNoteName = intent.getStringExtra(NoteActivity.NOTE_NAME);
        prevNoteTags = intent.getStringExtra(NoteActivity.NOTE_CONTENT);
        prevNoteContent = intent.getStringExtra(NoteActivity.NOTE_CONTENT);
    }

    private void showTheme(){
        Setting theme = Extras.getTheme(getBaseContext());
        setTheme(theme.getValue().equals(Setting.THEME_LIGHT)?R.style.AppTheme_NoActionBar:R.style.DarkTheme_NoActionBar);
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
                }
                else{
                    Extras.showToast(getBaseContext(),"Successfully created this notebook!");
                    moveBackToNoteActivity(notebook.getId());

                }
            }
            else{
                notebook = Notebook.getNotebookById(getBaseContext(),notebookId);
                notebook.setName(name);
                notebook.setDescription(description);
                notebook.save(getBaseContext());
                Extras.showToast(getBaseContext(),"This notebook has been saved!");
                moveToMainActivity();
            }
        }
    }

    private void moveToMainActivity(){
        Intent intent = new Intent(NotebookActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void moveBackToNoteActivity(int notebookId){
        Intent intent = new Intent(NotebookActivity.this,NoteActivity.class);

        intent.putExtra(NoteActivity.CREATED_NOTEBOOK_ID,notebookId);

        intent.putExtra(NoteActivity.NOTE_ID,prevNoteId);
        intent.putExtra(NoteActivity.NOTE_NAME,prevNoteName);
        intent.putExtra(NoteActivity.NOTE_TAGS,prevNoteTags);
        intent.putExtra(NoteActivity.NOTE_CONTENT,prevNoteContent);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notebook, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if (id == R.id.action_cancel){
//            isCancelling = true;
            finish();
        }
        else if(id == R.id.action_delete){
            if (notebookId!=NULL_NOTEBOOK_ID){
                Notebook notebook = Notebook.getNotebookById(getBaseContext(),notebookId);
                if(notebook.delete(getBaseContext())){
                    Extras.showToast(getBaseContext(),"Deleted successfully!");
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}