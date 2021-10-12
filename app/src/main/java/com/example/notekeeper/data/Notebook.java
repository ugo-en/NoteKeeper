package com.example.notekeeper.data;

import android.content.Context;

import java.sql.Date;
import java.util.ArrayList;

public class Notebook {
    private int id, onlineId;
    private String name, description;
    private Date dateCreated;

    protected Notebook(int id, int onlineId, String name, String description, Date dateCreated) {
        this.id = id;
        this.onlineId = onlineId;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public int getOnlineId() { return onlineId; }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public Date getDateCreated() { return dateCreated; }

    public void setName(String name) { this.name = name; }

    public void setOnlineId(int onlineId) { this.onlineId = onlineId; }

    public void setDescription(String description) { this.description = description; }

    public boolean save(Context context){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.updateNotebook(this);
    }

    public static Notebook createNotebook(Context context, String name, String description){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.createNotebook(name, description, -1);
    }

    public static ArrayList<Notebook> getAllNotebooks(Context context){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getAllNotebooks();
    }

    public static Notebook getNotebookById(Context context, int id){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getNotebook(id);
    }

    public ArrayList<Note> getAllItsNotes(Context context){
        DBGuy dbGuy = new DBGuy(context);
        return dbGuy.getAllNotesFromNotebook(this.id);
    }
}
