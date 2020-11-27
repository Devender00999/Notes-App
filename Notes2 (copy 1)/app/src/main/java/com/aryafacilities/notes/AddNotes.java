package com.aryafacilities.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNotes extends AppCompatActivity {


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private EditText noteText;
    private EditText noteTitle;
    private FloatingActionButton addBtn;
    private FloatingActionButton deleteBtn;
    Intent notesIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        preferences = getSharedPreferences("Notes", Context.MODE_PRIVATE);
        editor =  preferences.edit();
        addBtn = findViewById(R.id.addBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        noteText= findViewById(R.id.noteText);
        noteTitle= findViewById(R.id.noteTitle);


        notesIntent = getIntent();
        if (notesIntent.getBooleanExtra("FirstIntent", false)) {
            String[] notesData = notesIntent.getStringArrayExtra("notesData");
            System.out.println(notesData[0]+notesData[1]);
            noteText= findViewById(R.id.noteText);
            noteTitle= findViewById(R.id.noteTitle);

            noteText.setText(notesData[2]);
            noteTitle.setText(notesData[0]);
        }


        addBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (notesIntent.getBooleanExtra("FirstIntent", false)) {
                    System.out.println("First Intent");

                    Intent notesIntent = new Intent();
                    notesIntent.setClass(getApplicationContext(),MainActivity.class);
                    notesIntent.putExtra("data",new String[]{noteTitle.getText().toString(), noteText.getText().toString()});
                    setResult(Activity.RESULT_OK,notesIntent);
                    notesIntent.putExtra("SecondActivity",true);
                    finish();
                }

                else{
                    Intent notesIntent = new Intent();
                    notesIntent.putExtra("data",new String[]{noteTitle.getText().toString(), noteText.getText().toString()});
                    setResult(Activity.RESULT_OK,notesIntent);
                    finish();
                }

            }
        });

    }
}
