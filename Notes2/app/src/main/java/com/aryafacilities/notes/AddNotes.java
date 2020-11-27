package com.aryafacilities.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNotes extends AppCompatActivity {

    private DatabaseHelper myDb;



    private EditText noteText;
    private EditText noteTitle;
    private FloatingActionButton addBtn;
    private FloatingActionButton deleteBtn;
    private int index;
    private Intent notesIntent;
    private boolean isCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        addBtn = findViewById(R.id.addBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        noteText= findViewById(R.id.noteText);
        noteTitle= findViewById(R.id.noteTitle);
        final String[] title = {""};


        notesIntent = getIntent();
        if (notesIntent.getBooleanExtra("FirstIntent", false)) {
            String[] notesData = notesIntent.getStringArrayExtra("notesData");
            noteTitle.setText(notesData[0]);
            noteText.setText(notesData[1]);
            index = notesIntent.getIntExtra("index", 0);
        }
        boolean isCancel = notesIntent.getBooleanExtra("cancelButton",false);
        if(isCancel){
            deleteBtn.setImageResource(R.drawable.ic_cancel);
        }
        else{
            isCancel = false;
        }



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noteTitle.getText().toString().isEmpty() || noteText.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNotes.this);
                    builder.setTitle("Error While Saving").setMessage("Note Name or Note Cannot be Empty").create().show();
                    System.out.println("ERROR");
                    return;
                }
                if (notesIntent.getBooleanExtra("FirstIntent", false)) {
                    notesIntent = new Intent();

                    title[0] = noteTitle.getText().toString();
                    Date date =  Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd MMMM YYYY ");
                    notesIntent.setClass(getApplicationContext(),MainActivity.class);
                    boolean update = myDb.updateDate(title[0],noteTitle.getText().toString(),noteText.getText().toString(),df.format(date));
                    if(update)
                        Toast.makeText(AddNotes.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddNotes.this, "Error in Updating", Toast.LENGTH_SHORT).show();

                    notesIntent.putExtra("data",new String[]{noteTitle.getText().toString(), noteText.getText().toString()});
                    setResult(Activity.RESULT_OK,notesIntent);
                    notesIntent.putExtra("index", index);
                    notesIntent.putExtra("SecondActivity",true);
                    notesIntent.putExtra("isDeleted",true);
                    finish();
                }
                else{
                    Intent notesIntent = new Intent();
                    notesIntent.putExtra("data",new String[]{noteTitle.getText().toString(), noteText.getText().toString()});
                    setResult(Activity.RESULT_OK,notesIntent);
                    finish();
                }
            }
        });;

        final boolean finalIsCancel = isCancel;
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!finalIsCancel) {
                    String titleD = noteTitle.getText().toString();
                    notesIntent = new Intent();
                    notesIntent.putExtra("isDelete", true);
                    notesIntent.putExtra("index", index);
                    setResult(Activity.RESULT_OK, notesIntent);
                    int deleted = myDb.deleteData(titleD);
                    if (deleted > 0) {
                        Toast.makeText(AddNotes.this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(AddNotes.this, "Error In Deleting Data", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });
    }
}
