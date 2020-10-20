package com.akterUzzaman.roomdatabase.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akterUzzaman.roomdatabase.utils.AppExecutors;
import com.akterUzzaman.roomdatabase.R;
import com.akterUzzaman.roomdatabase.activity.CreateNoteActivity;
import com.akterUzzaman.roomdatabase.roomDatabase.Note;
import com.akterUzzaman.roomdatabase.roomDatabase.NoteDatabase;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{


        Context context;
        List<Note> allDataList;
        NoteDatabase noteDatabase;


    public RecyclerViewAdapter(Context context, List<Note> dataList) {
            this.context = context;
            this.allDataList = dataList;
            noteDatabase     = NoteDatabase.getInstance(this.context);
        }

        public RecyclerViewAdapter() {
            super();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view;
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.custom_card_view, viewGroup, false);




            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder myViewHolder, int i) {


            final String title = allDataList.get(i).getTitle();
            final String content = allDataList.get(i).getContent();
            final int     id     = allDataList.get(i).getNoteId();

            myViewHolder.title.setText(title);
            myViewHolder.content.setText(content);

            myViewHolder.editContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CreateNoteActivity.class);
                    intent.putExtra("noteId",id);
                    intent.putExtra("notetitle",title);
                    intent.putExtra("noteContent",content);
                    context.startActivity(intent);
                }
            });

            myViewHolder.cardLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                           Note note  = new Note(id,title,content);
                           noteDatabase.noteDao().deleteNote(note);

                        }
                    });


                    return true;
                }
            });

        }

        @Override
        public int getItemCount() {
            return allDataList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView title,content;
            ImageView editContent;
            LinearLayout cardLayout;



            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                title         = itemView.findViewById(R.id.title);
                content       = itemView.findViewById(R.id.content);
                editContent   = itemView.findViewById(R.id.edit);
                cardLayout    = itemView.findViewById(R.id.card_layout);


            }


        }



}


