package com.example.notekeeper.practice;

import android.os.Parcel;
import android.os.Parcelable;

public final class NoteInfo implements Parcelable {
    private NotebookInfo notebookInfo;
    private String mTitle;
    private String mContent;

    public NoteInfo(NotebookInfo course, String title, String text) {
        notebookInfo = course;
        mTitle = title;
        mContent = text;
    }

    private NoteInfo(Parcel parcel) {
        notebookInfo = parcel.readParcelable(NotebookInfo.class.getClassLoader());
        mTitle = parcel.readString();
        mContent = parcel.readString();
    }

    public NotebookInfo getNotebook() {
        return notebookInfo;
    }

    public void setNoteBook(NotebookInfo course) {
        notebookInfo = course;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String text) {
        mContent = text;
    }

    private String getCompareKey() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteInfo that = (NoteInfo) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(notebookInfo, 0);
        parcel.writeString(mTitle);
        parcel.writeString(mContent);
    }

    public static final Creator<NoteInfo> CREATOR =
            new Creator<NoteInfo>() {
                @Override
                public NoteInfo createFromParcel(Parcel parcel) {
                    return new NoteInfo(parcel);
                }

                @Override
                public NoteInfo[] newArray(int size) {
                    return new NoteInfo[size];
                }
            };
}












