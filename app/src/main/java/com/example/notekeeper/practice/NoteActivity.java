package com.example.notekeeper.practice;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.notekeeper.R;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_POSITION = "com.example.notekeeper.NOTE_POSITION";
    public static final int POSITION_NOT_FOUND = -1;

    private EditText textNoteTitleView, textContentTitleView;

    private NoteInfo note;

    private Spinner spinnerNotebooks;
    private boolean isNewNote;
    private int notePosition;
    private boolean isCancelling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinnerNotebooks = findViewById(R.id.spinner_notebooks);

        List<NotebookInfo> courses = DataManager.getInstance().getNotebooks();

        ArrayAdapter<NotebookInfo> adapterCourses = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerNotebooks.setAdapter(adapterCourses);

        textNoteTitleView = findViewById(R.id.note_name);
        textContentTitleView = findViewById(R.id.note_content);

        readDisplayStateValues();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCancelling){
            if (isNewNote) DataManager.getInstance().removeNote(notePosition);
        }
        else{
            saveNote();
        }
    }

    private void saveNote() {
        note.setNoteBook((NotebookInfo) spinnerNotebooks.getSelectedItem());
        note.setTitle(textNoteTitleView.getText().toString());
        note.setContent(textContentTitleView.getText().toString());
    }

    private void displayNote() {
        List<NotebookInfo> notebooks = DataManager.getInstance().getNotebooks();
        int notebookIndex = notebooks.indexOf(note.getNotebook());
        spinnerNotebooks.setSelection(notebookIndex);

        textNoteTitleView.setText(note.getTitle());
        textContentTitleView.setText(note.getContent());
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        int position = intent.getIntExtra(NOTE_POSITION,POSITION_NOT_FOUND);

        note = position == POSITION_NOT_FOUND?null:DataManager.getInstance().getNotes().get(position);

        isNewNote = note == null;

        if(isNewNote){
            createNewNote();
        }
        else{
            displayNote();
        }
    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        notePosition = dm.createNewNote();
        note = dm.getNotes().get(notePosition);
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
            isCancelling = true;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {
//        NoteInfo notebook = (NoteInfo) spinnerNotebooks.getSelectedItem();

        String subject = textNoteTitleView.getText().toString();
        String text = "Check out my note: \""+note.getTitle()+"\"\n\n"+note.getContent();

        // To send it as an email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(intent);
    }
}
