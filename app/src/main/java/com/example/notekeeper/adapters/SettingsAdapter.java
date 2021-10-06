package com.example.notekeeper.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.screens.NoteActivity;
import com.example.notekeeper.classes.Setting;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<Setting> mSettings;

    public SettingsAdapter(Context context, List<Setting> notes) {
        this.mContext = context;
        this.mSettings = notes;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Setting setting = mSettings.get(position);

        holder.getMainText().setText(setting.getMainText());
        holder.getSubText().setText(setting.getSubText());
    }

    @Override
    public int getItemCount() {
        return mSettings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView subText, mainText;
        private int mNoteId;

        public TextView getSubText() {
            return this.subText;
        }

        public TextView getMainText() {
            return this.mainText;
        }

        public void setNoteId(int noteId) {
            this.mNoteId = noteId;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            mainText = (TextView) itemView.findViewById(R.id.settings_main_text);
            subText = (TextView) itemView.findViewById(R.id.settings_sub_text);

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
