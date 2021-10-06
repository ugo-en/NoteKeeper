package com.example.notekeeper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.notekeeper.classes.Extras;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


class DBGuy extends SQLiteOpenHelper {
    private final Context context;

    private static final String DB_NAME = "db.db";

    private static final String NOTEBOOKS = "notebooks";
    private static final String NOTEBOOK_ID = "notebook_id";
    private static final String NOTEBOOK_NAME = "notebook_name";
    private static final String NOTEBOOK_DESCRIPTION = "notebook_description";
    private static final String NOTEBOOK_DATE_CREATED = "notebook_date_created";

    private static final String NOTES = "notes";
    private static final String NOTE_ID = "note_id";
    private static final String NOTE_NOTEBOOK_ID = "note_notebook_id";
    private static final String NOTE_NAME = "note_name";
    private static final String NOTE_TAGS = "note_tags";
    private static final String NOTE_DATE_CREATED = "notebook_date_created";
    private static final String NOTE_LAST_EDITED_DATE = "notebook_last_edited";
    private static final String NOTE_CONTENT = "notebook_content";

    public DBGuy(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase dbx) {
        try{
            dbx.execSQL(
                "CREATE TABLE IF NOT EXISTS "+NOTEBOOKS+"("+
                    NOTEBOOK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NOTEBOOK_NAME+" TEXT, "+
                    NOTEBOOK_DESCRIPTION+" TEXT, "+
                    NOTEBOOK_DATE_CREATED+" LONG "+
                ")"
            );
            dbx.execSQL(
                "CREATE TABLE IF NOT EXISTS "+NOTES+"("+
                    NOTE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NOTE_NOTEBOOK_ID+" INTEGER, "+
                    NOTE_NAME+" TEXT, "+
                    NOTE_TAGS +" TEXT, "+
                    NOTE_CONTENT+" TEXT, "+
                    NOTE_DATE_CREATED+" LONG, "+
                    NOTE_LAST_EDITED_DATE+" LONG "+
                ")"
            );
        }
        catch (Exception ex){
            System.out.println("An error has occurred while creating in-built database");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbx, int oldVersion, int newVersion) {
        try {
            dbx.execSQL("DROP TABLE IF EXISTS " + NOTES);
            dbx.execSQL("DROP TABLE IF EXISTS " + NOTEBOOKS);
            onCreate(dbx);
        }
        catch (Exception ex){
            System.out.println("An error occurred while upgrading in-built database");
        }
    }

    void clearAllNotebooks() {
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            dbx.execSQL("DELETE FROM "+  NOTEBOOKS);
            dbx.close();
            Extras.showToast(context,"Successfully deleted all the notebooks!");
        }
        catch (Exception ex){
            Extras.showToast(context,"Could not clear all notebooks!");
        }
    }

    void clearAllNotes() {
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            dbx.execSQL("DELETE FROM "+  NOTES);
            dbx.close();
            Extras.showToast(context,"Successfully deleted all the notes!");
        }
        catch (Exception ex){
            System.out.println("Could not clear all notes!");
        }
    }

    ArrayList<Notebook> getAllNotebooks(){
        ArrayList<Notebook> allNotebooks = new ArrayList<>();
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            Cursor cur = dbx.rawQuery("SELECT * FROM "+NOTEBOOKS+" ORDER BY "+NOTEBOOK_DATE_CREATED ,null);

            if (cur.moveToFirst()){
                System.out.println("Moved to first");
                do{
                    System.out.println("Next");

                    int id = cur.getInt(0);
                    String name = cur.getString(1);
                    String description = cur.getString(2);

                    Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLong = cur.getLong(3);
                    dateCreated.setTime(dateLong);

                    System.out.println("Notebook: "+name+" "+id+" ");
                    allNotebooks.add(new Notebook(id,name,description,dateCreated));
                }
                while (cur.moveToNext());
            }
            cur.close();
            dbx.close();
        }
        catch (Exception ex){
            System.out.println("An error occurred while getting the notebooks!");
        }
        return allNotebooks;
    }

    Notebook getNotebook(int id){
        Notebook notebook = null;

        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            Cursor cur = dbx.rawQuery("SELECT * FROM "+NOTEBOOKS+" WHERE "+NOTEBOOK_ID+" = "+id+" ORDER BY "+NOTEBOOK_DATE_CREATED ,null);

            if (cur.moveToNext()){
                System.out.println("Next");

                String name = cur.getString(1);
                String description = cur.getString(2);

                Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                long dateLong = cur.getLong(3);
                dateCreated.setTime(dateLong);

                System.out.println("Specific notebook: "+name+" "+id+" ");
                notebook = new Notebook(id,name,description,dateCreated);
            }
            cur.close();
            dbx.close();
        }
        catch (Exception ex){
            System.out.println("An error occurred while getting this notebook!");
        }
        return notebook;
    }

    ArrayList<Note> getAllNotesByName(boolean asc){
        ArrayList<Note> allNotes = new ArrayList<>();
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();

            String order = asc?" ASC":" DESC";

            Cursor cur = dbx.rawQuery("SELECT * FROM "+NOTES+" ORDER BY "+NOTE_NAME + order,null);

            if (cur.moveToFirst()){
                System.out.println("Moved to first");
                do{
                    System.out.println("Next");

                    int id = cur.getInt(0);
                    int noteBookId = cur.getInt(1);

                    String name = cur.getString(2);
                    String tags = cur.getString(3);
                    String content = cur.getString(4);

                    Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLong = cur.getLong(5);
                    dateCreated.setTime(dateLong);

                    Date dateLastEdited = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLastEditedLong = cur.getLong(6);
                    dateLastEdited.setTime(dateLastEditedLong);

                    System.out.println("Note by name: "+name+" "+id+" ");
                    allNotes.add(new Note(id,noteBookId,name,tags,content,dateCreated,dateLastEdited));
                }
                while (cur.moveToNext());
            }
            cur.close();
            dbx.close();
        }
        catch (Exception ex){
            System.out.println("An error occurred while getting the notes by name!");
        }
        return allNotes;
    }


    ArrayList<Note> getAllNotesByDateCreated(boolean asc){
        ArrayList<Note> allNotes = new ArrayList<>();
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();

            String order = asc?" ASC":" DESC";

            Cursor cur = dbx.rawQuery("SELECT * FROM "+NOTES+" ORDER BY "+NOTE_DATE_CREATED + order ,null);

            if (cur.moveToFirst()){
                System.out.println("Moved to first");
                do{
                    System.out.println("Next");

                    int id = cur.getInt(0);
                    int noteBookId = cur.getInt(1);

                    String name = cur.getString(2);
                    String tags = cur.getString(3);
                    String content = cur.getString(4);

                    Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLong = cur.getLong(5);
                    dateCreated.setTime(dateLong);

                    Date dateLastEdited = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLastEditedLong = cur.getLong(6);
                    dateLastEdited.setTime(dateLastEditedLong);

                    System.out.println("Note by date created: "+name+" "+id+" "+dateCreated);
                    allNotes.add(new Note(id,noteBookId,name,tags,content,dateCreated,dateLastEdited));
                }
                while (cur.moveToNext());
            }
            cur.close();
            dbx.close();
        }
        catch (Exception ex){
            System.out.println("An error occurred while getting the notebooks!");
        }
        return allNotes;
    }

    ArrayList<Note> getAllNotesByDateEdited(boolean asc){
        ArrayList<Note> allNotes = new ArrayList<>();
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();

            String order = asc?" ASC":" DESC";

            Cursor cur = dbx.rawQuery("SELECT * FROM "+NOTES+" ORDER BY "+NOTE_LAST_EDITED_DATE + order ,null);

            if (cur.moveToFirst()){
                System.out.println("Moved to first");
                do{
                    System.out.println("Next");

                    int id = cur.getInt(0);
                    int noteBookId = cur.getInt(1);

                    String name = cur.getString(2);
                    String tags = cur.getString(3);
                    String content = cur.getString(4);

                    Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLong = cur.getLong(5);
                    dateCreated.setTime(dateLong);

                    Date dateLastEdited = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLastEditedLong = cur.getLong(6);
                    dateLastEdited.setTime(dateLastEditedLong);

                    System.out.println("Note by last edited: "+name+" "+id+" "+dateLastEdited);
                    allNotes.add(new Note(id,noteBookId,name,tags,content,dateCreated,dateLastEdited));
                }
                while (cur.moveToNext());
            }
            cur.close();
            dbx.close();
        }
        catch (Exception ex){
            System.out.println("An error occurred while getting the notebooks!");
        }
        return allNotes;
    }

    ArrayList<Note> getAllNotesFromNotebook(int notebookId){
        ArrayList<Note> allNotes = new ArrayList<>();
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            Cursor cur = dbx.rawQuery("SELECT * FROM "+NOTES+" WHERE "+NOTE_NOTEBOOK_ID+" = "+notebookId+" ORDER BY "+NOTE_DATE_CREATED ,null);

            if (cur.moveToFirst()){
                System.out.println("Moved to first");
                do{
                    System.out.println("Next");

                    int id = cur.getInt(0);
                    int noteBookId = cur.getInt(1);

                    String name = cur.getString(2);
                    String tags = cur.getString(3);
                    String content = cur.getString(4);

                    Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLong = cur.getLong(5);
                    dateCreated.setTime(dateLong);

                    Date dateLastEdited = new Date(Calendar.getInstance().getTimeInMillis());
                    long dateLastEditedLong = cur.getLong(6);
                    dateLastEdited.setTime(dateLastEditedLong);

                    System.out.println("Note: "+name+" "+id+" ");
                    allNotes.add(new Note(id,noteBookId,name,tags,content,dateCreated,dateLastEdited));
                }
                while (cur.moveToNext());
            }
            cur.close();
            dbx.close();
        }
        catch (Exception ex){
            System.out.println("An error occurred while getting the notebooks!");
        }
        return allNotes;
    }

    Note getNote(int id){
        Note note = null;
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            Cursor cur = dbx.rawQuery("SELECT * FROM "+NOTES+" WHERE "+NOTE_ID+" = "+id+" ORDER BY "+NOTE_DATE_CREATED ,null);

            if (cur.moveToNext()){
                System.out.println("Next");

                int noteBookId = cur.getInt(1);

                String name = cur.getString(2);
                String tags = cur.getString(3);
                String content = cur.getString(4);

                Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                long dateLong = cur.getLong(5);
                dateCreated.setTime(dateLong);

                Date dateLastEdited = new Date(Calendar.getInstance().getTimeInMillis());
                long dateLastEditedLong = cur.getLong(6);
                dateLastEdited.setTime(dateLastEditedLong);

                System.out.println("Specific note: "+name+" "+id+" ");
                note = new Note(id,noteBookId,name,tags,content,dateCreated,dateLastEdited);
            }
            cur.close();
            dbx.close();
        }
        catch (Exception ex){
            System.out.println("An error occurred while getting this note!");
        }
        return note;
    }

    Notebook createNotebook(String name, String description){
        Notebook notebook = null;
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(NOTEBOOK_NAME,name);
            cv.put(NOTEBOOK_DESCRIPTION,description);
            cv.put(NOTEBOOK_DATE_CREATED, Calendar.getInstance().getTimeInMillis());

            long id = dbx.insert(NOTEBOOKS,null,cv);

            if (id!=-1){
                Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                dateCreated.setTime(Calendar.getInstance().getTimeInMillis());

                notebook = new Notebook((int)id,name,description,dateCreated);
            }
            dbx.close();
        }
        catch (Exception ex){
            Extras.showToast(context,ex.toString());
        }
        return notebook;
    }

    Note createNote(int notebookId, String name, String tags, String content){
        Note note = null;
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(NOTE_NAME,name);
            cv.put(NOTE_NOTEBOOK_ID,notebookId);
            cv.put(NOTE_TAGS,tags);
            cv.put(NOTE_CONTENT,content);
            cv.put(NOTE_DATE_CREATED, Calendar.getInstance().getTimeInMillis());
            cv.put(NOTE_LAST_EDITED_DATE, Calendar.getInstance().getTimeInMillis());

            long id = dbx.insert(NOTES,null,cv);

            if (id!=-1){
                Date dateCreated = new Date(Calendar.getInstance().getTimeInMillis());
                dateCreated.setTime(Calendar.getInstance().getTimeInMillis());

                note = new Note((int)id, notebookId ,name,tags,content,dateCreated,dateCreated);
            }
            dbx.close();
            return note;
        }
        catch (Exception ex){
            return note;
        }
    }

    boolean updateNotebook(Notebook notebook){
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(NOTEBOOK_NAME,notebook.getName());
            cv.put(NOTEBOOK_DESCRIPTION,notebook.getDescription());

            String whereClause = NOTEBOOK_ID+"=?";
            String[] whereArgs = new String[]{String.valueOf(notebook.getId())};

            dbx.update(NOTEBOOKS,cv,whereClause,whereArgs);
            dbx.close();
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    boolean updateNote(Note note){
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(NOTE_NAME,note.getName());
            cv.put(NOTE_NOTEBOOK_ID,note.getNoteBookId());
            cv.put(NOTE_TAGS,note.getTags());
            cv.put(NOTE_CONTENT,note.getContent());
            cv.put(NOTE_DATE_CREATED,note.getDateCreated().getTime());
            cv.put(NOTE_LAST_EDITED_DATE,note.getDateLastEdited().getTime());

            String whereClause = NOTE_ID+"=?";
            String[] whereArgs = new String[]{String.valueOf(note.getId())};

            dbx.update(NOTES,cv,whereClause,whereArgs);
            dbx.close();
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    boolean deleteNotebook(Notebook notebook){
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            boolean result = dbx.delete(NOTEBOOKS, NOTEBOOK_ID + "=" + notebook.getId(), null) > 0;
            dbx.close();
            return result;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    boolean deleteNote(Note note){
        try{
            SQLiteDatabase dbx = this.getWritableDatabase();
            boolean result = dbx.delete(NOTES, NOTE_ID + "=" + note.getId(), null) > 0;
            dbx.close();
            return result;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
