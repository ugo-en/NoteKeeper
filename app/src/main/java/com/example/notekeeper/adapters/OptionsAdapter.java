package com.example.notekeeper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.classes.DefaultOption;
import com.example.notekeeper.classes.ToggleOption;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<DefaultOption> mSettings;

    public OptionsAdapter(Context context, List<DefaultOption> notes) {
        this.mContext = context;
        this.mSettings = notes;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType){
            case DefaultOption.TOGGLE:
                itemView = mLayoutInflater.inflate(R.layout.list_item_toggle_option, parent, false);
                break;
            default: itemView = mLayoutInflater.inflate(R.layout.list_item_option, parent, false);
        }

        return new ViewHolder(itemView,parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DefaultOption setting = mSettings.get(position);

        holder.getMainText().setText(setting.getMainText());
        holder.getSubText().setText(setting.getSubText());

        if (setting instanceof ToggleOption){
            holder.setToggleSwitch((ToggleOption) setting);
        }

        holder.setClickFunction(setting);

    }

    @Override
    public int getItemViewType(int position) {
        DefaultOption setting = mSettings.get(position);
        return setting.getSettingType();
    }


    @Override
    public int getItemCount() {
        return mSettings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ViewGroup mParent;
        private View mItemView;
        private TextView mSubText, mMainText;
        private Switch mToggleSwitch;

        public TextView getSubText() {
            return this.mSubText;
        }

        public TextView getMainText() {
            return this.mMainText;
        }

        public TextView getToggleSwitch() {
            return this.mToggleSwitch;
        }

        public void setToggleSwitch(final ToggleOption setting) {
            mToggleSwitch = mItemView.findViewById(R.id.settings_list_item_toggle);
            if (mToggleSwitch != null){
                mToggleSwitch.setChecked(setting.isOn());
                mToggleSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                   setting.clickFunction();
                    }
                });
            }
        }

        public void setClickFunction(final DefaultOption setting){
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setting.clickFunction();
                }
            });
        }

        public ViewHolder(View itemView, ViewGroup parent) {
            super(itemView);
            this.mItemView = itemView;
            this.mParent = parent;
            mMainText = (TextView) itemView.findViewById(R.id.settings_list_item_main_text);
            mSubText = (TextView) itemView.findViewById(R.id.settings_list_item_sub_text);
        }
    }
}
