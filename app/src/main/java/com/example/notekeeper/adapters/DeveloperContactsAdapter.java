package com.example.notekeeper.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.classes.DeveloperContact;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DeveloperContactsAdapter extends RecyclerView.Adapter<DeveloperContactsAdapter.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<DeveloperContact> mContacts;

    public DeveloperContactsAdapter(Context context, List<DeveloperContact> contacts) {
        this.mContext = context;
        this.mContacts = contacts;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // using the 'mLayoutInflater', which is a member of this class was yielding errors.
        // Thus, after some research, I resorted to using 'LayoutInflater.from(parent.getContext())'
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.developer_contacts_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeveloperContact contact = mContacts.get(position);

        holder.getContactMeans().setText(contact.getContactMeans());
        holder.getUrl().setText(contact.getUrl());

        holder.setClickFunction(contact);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mItemView;
        private TextView mUrl, mContactMeans;

        public TextView getUrl() {
            return this.mUrl;
        }

        public TextView getContactMeans() {
            return this.mContactMeans;
        }

        public void setClickFunction(final DeveloperContact contact){
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contact.getType().equals(DeveloperContact.TYPE_DEFAULT)){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl.getText().toString()));
                        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(browserIntent);
                    }
                    else if (contact.getType().equals(DeveloperContact.TYPE_EMAIL)){
                        // To send it as an email
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("message/rfc2822");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Hey! I am <insert name>");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mUrl.getText().toString()});
                        mItemView.getContext().startActivity(intent);
                    }
                }
            });
        }

        public ViewHolder(View itemView) {
            super(itemView);
            this.mItemView = itemView;
            this.mContactMeans = (TextView) itemView.findViewById(R.id.developer_contact_list_item_contact_means);
            this.mUrl = (TextView) itemView.findViewById(R.id.developer_contact_list_item_url);
        }
    }
}
