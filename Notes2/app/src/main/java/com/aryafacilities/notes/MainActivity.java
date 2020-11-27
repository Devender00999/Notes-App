package com.aryafacilities.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.Time;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private Date date;
    private int len = 0;
    private RecyclerView noteRecView;
    private ArrayList<Notes> notes = new ArrayList<>();
    private NotesRecViewAdapter adapter;

    private SimpleDateFormat df;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        date =  Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd MMMM YYYY ");
        noteRecView = findViewById(R.id.noteRecView);
        FloatingActionButton fab = findViewById(R.id.addNote);
        adapter = new NotesRecViewAdapter(MainActivity.this);
        noteRecView.setLayoutManager(new LinearLayoutManager(this));
        viewAll();




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNoteAcitivity = new Intent();
                addNoteAcitivity.setClass(MainActivity.this,AddNotes.class);
                addNoteAcitivity.putExtra("cancelButton",true);
                startActivityForResult(addNoteAcitivity,9875);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 9875 && resultCode==Activity.RESULT_OK && data!=null && !data.getBooleanExtra("isDelete",false)){
            String[] a = data.getStringArrayExtra("data");
            System.out.println(a[0]+" data "+a[1]);

            boolean isInserted = myDb.insertData(a[0],a[1],df.format(date));
            if(isInserted)
            {
                notes.add(new Notes(a[0], a[1] ,df.format(date)));
                adapter.setNotes(notes);
                noteRecView.setAdapter(adapter);
            }
            else
                Toast.makeText(this, "Data Is Not Inserted", Toast.LENGTH_SHORT).show();
        }

        if(requestCode==12345 && resultCode==RESULT_OK && data!=null&& !(data.getBooleanExtra("isDelete",false ))){
            String[] note = data.getStringArrayExtra("data");
            notes.set(data.getIntExtra("index", 0), new Notes(note[0], note[1] ,df.format(date)));
            adapter.notifyDataSetChanged();
        }
        if (requestCode==12345 && resultCode==RESULT_OK && data!=null&& (data.getBooleanExtra("isDelete",false ))){
            notes.remove(data.getIntExtra("index", 0));
            adapter.notifyDataSetChanged();
        }

    }

    public void viewAll(){
        if(myDb!=null){
            Cursor res = myDb.getAllData();
            if(res.getCount()==0){
                return;
            }
            while (res.moveToNext()){
                notes.add(new Notes(res.getString(1),res.getString(2) ,res.getString(3)));
                adapter.setNotes(notes);
                noteRecView.setAdapter(adapter);
            }
            len = notes.size();
        }


    }


}
