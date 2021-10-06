package com.example.notekeeper.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.data.Note;
import com.example.notekeeper.screens.NoteActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<Note> mNotes;

    public NoteListAdapter(Context context, List<Note> notes) {
        this.mContext = context;
        this.mNotes = notes;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mNotes.get(position);

        holder.getName().setText(note.getName());
        holder.getNotebookName().setText(note.getNoteBook(this.mContext).getName());
        holder.setNoteId(note.getId());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mNotebookName;
        private TextView mName;
        private int mNoteId;

        public int getNoteId() {
            return mNoteId;
        }

        public TextView getNotebookName() {
            return this.mNotebookName;
        }

        public TextView getName() {
            return this.mName;
        }

        public void setNoteId(int noteId) {
            this.mNoteId = noteId;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.note_list_item_name);
            mNotebookName = (TextView) itemView.findViewById(R.id.note_list_item_notebook_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NoteActivity.class);
                    intent.putExtra(NoteActivity.NOTE_ID, mNoteId);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
