package com.example.notekeeper.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.data.Notebook;
import com.example.notekeeper.screens.NotebookActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NotebookListAdapter extends RecyclerView.Adapter<NotebookListAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<Notebook> mNotebooks;

    public NotebookListAdapter(Context context, List<Notebook> notebooks) {
        this.mContext = context;
        this.mNotebooks = notebooks;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.notebook_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notebook notebook = mNotebooks.get(position);

        holder.getName().setText(notebook.getName());
        holder.getDescription().setText(notebook.getDescription());
        holder.setNotebookId(notebook.getId());
    }

    @Override
    public int getItemCount() {
        return mNotebooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mDescription, mName;
        private int mNotebookId;

        public int getNotebookId() {
            return mNotebookId;
        }

        public TextView getDescription() {
            return this.mDescription;
        }

        public TextView getName() {
            return this.mName;
        }

        public void setNotebookId(int notebookId) {
            this.mNotebookId = notebookId;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.notebook_list_item_name);
            mDescription = (TextView) itemView.findViewById(R.id.notebook_list_item_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NotebookActivity.class);
                    intent.putExtra(NotebookActivity.NOTEBOOK_ID, mNotebookId);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
