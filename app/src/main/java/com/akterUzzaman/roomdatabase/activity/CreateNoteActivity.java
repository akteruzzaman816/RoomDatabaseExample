package com.akterUzzaman.roomdatabase.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.akterUzzaman.roomdatabase.utils.AppExecutors;
import com.akterUzzaman.roomdatabase.R;
import com.akterUzzaman.roomdatabase.roomDatabase.Note;
import com.akterUzzaman.roomdatabase.roomDatabase.NoteDatabase;

public class CreateNoteActivity extends AppCompatActivity {

    EditText title,content;
    Button   addButton;
    NoteDatabase noteDatabase;
    Toolbar toolbar;
    Intent intent;
    int noteID;

    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        toolbar          = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);

        title            = findViewById(R.id.create_title);
        content          = findViewById(R.id.create_content);
        addButton        = findViewById(R.id.add_button);

        noteDatabase     = NoteDatabase.getInstance(this);

        intent =getIntent();

        if (intent != null && intent.hasExtra("noteId")){
             noteID          = intent.getIntExtra("noteId",-1);
            String updateTitle = intent.getStringExtra("notetitle");
            String updateContent = intent.getStringExtra("noteContent");
            addButton.setText("Update");
            title.setText(updateTitle);
            content.setText(updateContent);
        }


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (title.getText().toString().isEmpty())
                {
                    Toast.makeText(CreateNoteActivity.this, "Title", Toast.LENGTH_SHORT).show();
                    return;
                }else if (content.getText().toString().isEmpty())
                {
                    Toast.makeText(CreateNoteActivity.this, "Content", Toast.LENGTH_SHORT).show();
                    return;
                }

                note = new Note(title.getText().toString(), content.getText().toString());

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if (intent.hasExtra("noteId"))
                        {
                            note.setNoteId(noteID);
                            noteDatabase.noteDao().updateNote(note);

                        }else {
                            noteDatabase.noteDao().saveNotes(note);
                        }
                        finish();

                    }

                });
            }

        });


    }
}
