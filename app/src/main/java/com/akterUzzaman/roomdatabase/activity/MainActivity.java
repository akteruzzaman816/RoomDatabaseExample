package com.akterUzzaman.roomdatabase.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.akterUzzaman.roomdatabase.utils.AppExecutors;
import com.akterUzzaman.roomdatabase.R;
import com.akterUzzaman.roomdatabase.adapter.RecyclerViewAdapter;
import com.akterUzzaman.roomdatabase.roomDatabase.Note;
import com.akterUzzaman.roomdatabase.roomDatabase.NoteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    NoteDatabase noteDatabase;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    TextView warningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.add);
        recyclerView         = findViewById(R.id.recycler_view);
        warningText          = findViewById(R.id.warning_text);

        noteDatabase         = NoteDatabase.getInstance(this);


        //call function to load all Data
        loadData();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });


    }


    private void loadData() {

        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {


                noteDatabase.noteDao().getAllNotes().observe(MainActivity.this, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        AppExecutors.getInstance().mainThread().execute(new Runnable() {
                            @Override
                            public void run() {

                                if (notes.size() > 0) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    warningText.setVisibility(View.GONE);
                                    adapter = new RecyclerViewAdapter(MainActivity.this, notes);
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                    warningText.setVisibility(View.VISIBLE);
                                }


                            }
                        });


                    }
                });
            }
        });


    }
}
