package com.aryafacilities.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    LinearLayout scrollView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText text;
    Set<String> notes =  new HashSet<>();
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("Notes",Context.MODE_PRIVATE);
        editor = preferences.edit();
        setContentView(R.layout.activity_main);
        scrollView = (LinearLayout) findViewById(R.id.scrollLayout);
        if  (preferences.contains("notes")){
            for (String note : preferences.getStringSet("notes", null)) {
                notes.add(note);
                TextView newText = new TextView(getApplicationContext());
                newText.setPadding(0, 2,0,2);
                newText.setTextSize(18);
                newText.setTextColor(Color.BLACK);
                newText.setText(note);
                scrollView.addView(newText);
            }
        }
   }
    public void addNote(View v){

        text = (EditText)findViewById(R.id.editText);
        String note = text.getText().toString();
        if(text.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(),"Please Add Your Note First",Toast.LENGTH_SHORT).show();
        else{
            TextView newText = new TextView(getApplicationContext());
            newText.setTextSize(18);
            newText.setTextColor(Color.BLACK);
            newText.setText(note);
            notes.add(note);
            editor.putStringSet("notes",notes);
            editor.commit();
            scrollView.addView(newText);
            text.setText("");
        }
   }

}
