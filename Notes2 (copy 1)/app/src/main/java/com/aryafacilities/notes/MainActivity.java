package com.aryafacilities.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.Time;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private int len = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private RecyclerView noteRecView;
    ArrayList<Notes> notes = new ArrayList<>();

    NotesRecViewAdapter adapter;


    @Override
    protected void onResume() {
        super.onResume();
        Intent dataIntent = getIntent();
        String[] data = dataIntent.getStringArrayExtra("data");
        System.out.println("Is Called");
        if(data!=null){
            System.out.println("Wroking");
            System.out.println(data[0]+ data[1]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteRecView = findViewById(R.id.noteRecView);
        FloatingActionButton fab = findViewById(R.id.addNote);
        adapter = new NotesRecViewAdapter(MainActivity.this);
        preferences = getSharedPreferences("Notes", Context.MODE_PRIVATE);
        editor =  preferences.edit();
        noteRecView.setLayoutManager(new LinearLayoutManager(this));

//        for (int i = 0; i < preferences.getAll().size(); i++) {
//            notes.add(new Notes("a[0]", "a[1]" , "01/01/1999"));
//            adapter.setNotes(notes);
//            noteRecView.setAdapter(adapter);
//        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNoteAcitivity = new Intent();
                addNoteAcitivity.setClass(MainActivity.this,AddNotes.class);
                startActivityForResult(addNoteAcitivity, 9875);

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 9875 && resultCode==Activity.RESULT_OK && data!=null ){
            String[] a = data.getStringArrayExtra("data");
            editor.putString(a[0],a[1]);
            editor.commit();
            len = preferences.getAll().size();
            notes.add(new Notes(a[0], a[1] , "01/01/1999"));
            adapter.setNotes(notes);
            noteRecView.setAdapter(adapter);
        }
    }
}
