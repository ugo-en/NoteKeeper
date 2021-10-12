package com.example.notekeeper.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.data.Note;
import com.example.notekeeper.data.Notebook;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.data.Setting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_ID = "com.example.notekeeper.NOTE_POSITION";
    public static final String CREATED_NOTEBOOK_ID = "com.example.notekeeper.NOTEBOOK_ID";

    public static final int NOTE_ID_NOT_FOUND = -1;

    private EditText noteNameView, noteContentView, noteTagsView;
    private TextView hdr;
    private Button newNotebookBtn;
    private FloatingActionButton saveBtn;

    private Note note;
    private ArrayList<Notebook> allNotebooks;

    private Spinner spinnerNotebooks;
    private boolean isNewNote, isMakingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerNotebooks = findViewById(R.id.spinner_notebooks);
        saveBtn = findViewById(R.id.new_notebook_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNewNote){
                    createNote();
                }
                else{
                    saveNote();
                }
            }
        });

        newNotebookBtn = findViewById(R.id.new_notebook_btn);
        newNotebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewNotebook();
            }
        });

        allNotebooks = Notebook.getAllNotebooks(getBaseContext());
        ArrayList<String> allNotebooksNames = new ArrayList<String>();

        for (int i = 0; i < allNotebooks.size(); i++){
            allNotebooksNames.add(allNotebooks.get(i).getName());
        }

        ArrayAdapter<String> adapterNotebooks = new ArrayAdapter<>(this, R.layout.spinner_item,allNotebooksNames);
        adapterNotebooks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerNotebooks.setSelection(0);
        spinnerNotebooks.setAdapter(adapterNotebooks);

        hdr = findViewById(R.id.note_activity_header);

        noteNameView = findViewById(R.id.note_name);
        noteContentView = findViewById(R.id.note_content);
        noteTagsView = findViewById(R.id.note_tags);

        isMakingNote = false;

        readDisplayStateValues();
    }

    @Override
    protected void onResume(){
        super.onResume();
        isMakingNote = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isMakingNote){
            if (!isNewNote){
                saveNote();
            }
        }
    }

    private void moveToNewNotebook(){
        isMakingNote = true;

        Intent intent = new Intent(NoteActivity.this, NotebookActivity.class);
        if (note != null){
            intent.putExtra(NOTE_ID,note.getId());
        }
        startActivity(intent);
    }

    private void createNote() {
        String name = noteNameView.getText().toString().trim();
        String content = noteContentView.getText().toString().trim();
        String tags = noteTagsView.getText().toString().trim();

        if(name.isEmpty() || content.isEmpty() || tags.isEmpty()){
            Extras.showToast(getBaseContext(),"Please fill enter all fields!");
        }
        else if (spinnerNotebooks.getSelectedItemPosition() == NOTE_ID_NOT_FOUND){
            Extras.showToast(getBaseContext(),"Please select a notebook!");
        }
        else{
            Notebook notebook = allNotebooks.get(spinnerNotebooks.getSelectedItemPosition());
            Note note = Note.createNote(getBaseContext(),name,content,tags,notebook.getId());
            if(note==null){
                Extras.showToast(getBaseContext(),"An error occurred while saving this note!");
            }
            else{
                Extras.showToast(getBaseContext(),"This note has ben saved!");
                moveToMainActivity();
            }
        }
    }
    private void saveNote() {
        String name = noteNameView.getText().toString().trim();
        String content = noteContentView.getText().toString().trim();
        String tags = noteTagsView.getText().toString().trim();

        if(name.isEmpty() || content.isEmpty() || tags.isEmpty()){
            Extras.showToast(getBaseContext(),"Please fill enter all fields!");
        }
        else if (spinnerNotebooks.getSelectedItemPosition() == NOTE_ID_NOT_FOUND){
            Extras.showToast(getBaseContext(),"Please select a notebook!");
        }
        else{
           if(note==null){
                Extras.showToast(getBaseContext(),"An error occurred while saving this note!");
            }
            else{
                Notebook notebook = allNotebooks.get(spinnerNotebooks.getSelectedItemPosition());
                note.setName(name);
                note.setContent(content);
                note.setTags(tags);
                note.setNoteBookId(notebook.getId());

                if(note.update(getBaseContext())){
                    Extras.showToast(getBaseContext(),"This note has ben saved!");
                }
                else{
                    Extras.showToast(getBaseContext(),"Could not save this note due to an error!");
                }

                moveToMainActivity();
            }
        }
    }

    private void displayNote() {
        if (note!=null) {
            List<Notebook> notebooks = Notebook.getAllNotebooks(getBaseContext());
            spinnerNotebooks.setSelection(notebooks.indexOf(note.getNoteBook(getBaseContext())));

            hdr.setText(note.getName());

            noteNameView.setText(note.getName());
            noteTagsView.setText(note.getTags());
            noteContentView.setText(note.getContent());
        }
    }

    private void showTheme(){
        Setting theme = Extras.getTheme(getBaseContext());
        setTheme(theme.getValue().equals(Setting.THEME_LIGHT)?R.style.AppTheme_NoActionBar:R.style.DarkTheme_NoActionBar);
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        int noteId = intent.getIntExtra(NOTE_ID, NOTE_ID_NOT_FOUND);

        note = noteId == NOTE_ID_NOT_FOUND ?null:Note.getNoteById(getBaseContext(),noteId);

        isNewNote = note == null;

        if(!isNewNote){
            displayNote();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail) {
            sendEmail();
            return true;
        }
        else if (id == R.id.action_cancel){
//            isCancelling = true;
            finish();
        }
        else if(id == R.id.action_delete){
            if (note!=null){
                if(note.delete(getBaseContext())){
                    Extras.showToast(getBaseContext(),"Deleted successfully!");
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {
//        NoteInfo notebook = (NoteInfo) spinnerNotebooks.getSelectedItem();

        String noteName = noteNameView.getText().toString().trim();
        String noteContent = noteContentView.getText().toString().trim();

        if (noteName.isEmpty() || noteContent.isEmpty()) {
            Extras.showToast(getBaseContext(), "Enter the name and the content!");
        }
        else {
            String text = "Check out my note: \"" + noteContent; //+ "\"\n\n" + note.getContent();

            // To send it as an email
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc2822");
            intent.putExtra(Intent.EXTRA_SUBJECT, noteName);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(intent);
        }
    }

    private void moveToMainActivity(){
        Intent intent = new Intent(NoteActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
