package com.aryafacilities.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NotesRecViewAdapter extends RecyclerView.Adapter<NotesRecViewAdapter.ViewHolder>{
    private ArrayList<Notes> notes = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private Activity context;
    public NotesRecViewAdapter(Activity context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notelist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.noteTitle.setText(notes.get(position).getNoteTitle());
        holder.noteText.setText(notes.get(position).getNoteText());
        holder.noteDate.setText((CharSequence) notes.get(position).getNoteDate());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteActivity = new Intent();
                noteActivity.setClass(context, AddNotes.class);
                noteActivity.putExtra("FirstIntent", true);
                noteActivity.putExtra("isDelete", true);
                noteActivity.putExtra("index", position);
                noteActivity.putExtra("notesData", new String []{notes.get(position).getNoteTitle(),
                        notes.get(position).getNoteText(),
                        notes.get(position).getNoteDate()});
                context.startActivityForResult(noteActivity, 12345);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public void setNotes(ArrayList<Notes> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView noteTitle;
        private CardView parent;
        private TextView noteDate;
        private TextView noteText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            noteDate = itemView.findViewById(R.id.noteDate);
            noteText = itemView.findViewById(R.id.noteText);
        }
    }
}
