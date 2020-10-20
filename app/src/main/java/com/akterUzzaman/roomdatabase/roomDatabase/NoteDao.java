package com.akterUzzaman.roomdatabase.roomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface NoteDao {

    @Query("select * from note")
    LiveData<List<Note>> getAllNotes();

    @Insert
    Void saveNotes(Note note);

    @Update
    Void updateNote(Note note);

    @Delete
    Void deleteNote(Note note);

    @Query("SELECT * FROM note WHERE noteId = :id")
    Note loadNoteById(int id);
}
