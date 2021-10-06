package com.example.notekeeper.data;

import android.content.Context;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class Note {
    private int id, noteBookId;
    private String name, tags, content;
    private Date dateCreated, dateLastEdited;

    protected Note(int id, int noteBookId, String name, String tags, String content, Date dateCreated, Date dateLastEdited) {
        this.id = id;
        this.noteBookId = noteBookId;
        this.name = name;
        this.tags = tags;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateLastEdited = dateLastEdited;
    }

    public int getId() { return id; }

    public int getNoteBookId() { return noteBookId; }

    public Notebook getNoteBook(Context context) {
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getNotebook(this.noteBookId);
    }

    public String getName() { return name; }

    public String getTags() { return tags; }

    public String getContent() { return content; }

    public Date getDateCreated() { return dateCreated; }

    public Date getDateLastEdited() { return dateLastEdited; }

    public void setNoteBookId(int noteBookId) { this.noteBookId = noteBookId; }

    public void setName(String name) { this.name = name; }

    public void setTags(String tags) { this.tags = tags; }

    public void setContent(String content) { this.content = content; }

    public void setDateLastEdited(Date dateLastEdited) { this.dateLastEdited = dateLastEdited; }

    public boolean update(Context context){
        Date lastEdited = new Date(0);
        lastEdited.setTime(Calendar.getInstance().getTimeInMillis());

        this.setDateLastEdited(lastEdited);
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.updateNote(this);
    }

    public boolean delete(Context context){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.deleteNote(this);
    }

    public static Note createNote(Context context, String name, String tags, String content, int noteBookId){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.createNote(noteBookId, name,tags,content);
    }

    public static Note getNoteById(Context context, int id){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getNote(id);
    }

    public static ArrayList<Note> getAllNotesByDateLastEdited(Context context, boolean asc){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getAllNotesByDateEdited(asc);
    }

    public static ArrayList<Note> getAllNotesByName(Context context, boolean asc){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getAllNotesByName(false);
    }

    public static ArrayList<Note> getAllNotesByDateCreated(Context context, boolean asc){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getAllNotesByDateCreated(false);
    }
}
