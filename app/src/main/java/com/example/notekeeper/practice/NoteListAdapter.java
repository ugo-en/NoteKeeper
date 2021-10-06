package com.example.notekeeper.practice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notekeeper.R;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<NoteInfo> mNotes;

    public NoteListAdapter(Context context, List<NoteInfo> notes) {
        this.mContext = context;
        this.mNotes = notes;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteInfo note = mNotes.get(position);

        holder.getNoteBookTitle().setText(note.getNotebook().getTitle());
        holder.getNoteTitle().setText(note.getTitle());
        holder.setCurrentPosition(position);

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mNoteTitle;
        private TextView mNoteBookTitle;
        private int mCurrentPosition;

        public int getCurrentPosition() {
            return mCurrentPosition;
        }

        public void setCurrentPosition(int currentPosition) {
            this.mCurrentPosition = currentPosition;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            mNoteBookTitle = (TextView) itemView.findViewById(R.id.settings_main_text);
            mNoteTitle = (TextView) itemView.findViewById(R.id.settings_sub_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NoteActivity.class);
                    intent.putExtra(NoteActivity.NOTE_POSITION,mCurrentPosition);
                    mContext.startActivity(intent);
                }
            });
        }

        public TextView getNoteTitle() {
            return mNoteTitle;
        }

        public TextView getNoteBookTitle() {
            return mNoteBookTitle;
        }
    }
}
