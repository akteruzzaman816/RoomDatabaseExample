package com.akterUzzaman.roomdatabase.roomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class},version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    private static final String DB_NAME = "note-db";
    private static volatile NoteDatabase instance =null;


    public static  NoteDatabase getInstance(Context context)
    {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,DB_NAME)
                        .build();

        }
        return instance;
    }


    public abstract NoteDao noteDao();




}
