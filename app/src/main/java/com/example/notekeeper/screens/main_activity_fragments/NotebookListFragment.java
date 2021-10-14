package com.example.notekeeper.screens.main_activity_fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.classes.Extras;
import com.example.notekeeper.data.Notebook;
import com.example.notekeeper.adapters.NotebookListAdapter;
import com.example.notekeeper.screens.NotebookActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotebookListFragment extends Fragment {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean mPermissionToRecordAccepted = false;
    private String[] mPermissions = new String[]{Manifest.permission.RECORD_AUDIO};

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;

    private View mRootView;
    private ImageView mMicrophone;
    private EditText mSearchbar;
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
        mRootView = inflater.inflate(R.layout.fragment_notebook_list, container, false);

        addBtn = mRootView.findViewById(R.id.add_notebook_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewNotebookActivity();
            }
        });

        initDisplayContent();


        mSearchbar = mRootView.findViewById(R.id.notebook_list_searchbar);
        mSearchbar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                search();
                return true;
            }
        });
        mSearchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        setSpeechRecognizer();

        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mMicrophone = mRootView.findViewById(R.id.notebook_list_mic);
        mMicrophone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mPermissionToRecordAccepted){
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                    Extras.showToast(getContext(),"Listening...");
                }
                return true;
            }
        });
        return mRootView;
    }

    private void search(){
        String searchTerm = mSearchbar.getText().toString().trim().toLowerCase();

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
        if (mRootView != null){
            mNotebooks = Notebook.getAllNotebooks(getContext());

            mRecyclerNotebooks = mRootView.findViewById(R.id.notebooks_recycler);
//
            mNotebooksLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerNotebooks.setLayoutManager(mNotebooksLayoutManager);

            mNotebookRecyclerAdapter = new NotebookListAdapter(getContext(), mNotebooks);
            mRecyclerNotebooks.setAdapter(mNotebookRecyclerAdapter);
        }
    }

    private void checkPermission(){
        try{
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
                requestPermission();
            }
            else{
                mPermissionToRecordAccepted = true;
            }
        }
        catch (Exception ex){
            Extras.showToast(getContext(),"An error occurred while checking microphone permissions!");
        }
    }

    private void requestPermission(){
        try{
            ActivityCompat.requestPermissions(getActivity(), mPermissions,REQUEST_RECORD_AUDIO_PERMISSION);
        }
        catch (Exception ex){
            Extras.showToast(getContext(),"An error occurred while requesting microphone permissions!");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            mPermissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!mPermissionToRecordAccepted) {
            Extras.showToast(getContext(),"Please grant this app permission to record audio");
        }
    }

    private void setSpeechRecognizer(){
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float v) {}

            @Override
            public void onBufferReceived(byte[] bytes) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int i) {}

            @Override
            public void onResults(Bundle bundle) {
                try{
                    ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    mSearchbar.setText(data.get(0));
                    search();
                }
                catch (NullPointerException ex){
                    Extras.showToast(getContext(),"Could you repeat that?");
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                Extras.showToast(getContext(),"Could you repeat that?");
            }

            @Override
            public void onEvent(int i, Bundle bundle) {}
        });
    }
}