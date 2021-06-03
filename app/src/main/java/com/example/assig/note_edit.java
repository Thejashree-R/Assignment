package com.example.assig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import java.util.ArrayList;
import java.util.HashSet;

import yuku.ambilwarna.AmbilWarnaDialog;

public class note_edit extends AppCompatActivity {
    ConstraintLayout mainlayout;
    int noteId;
    int defaultColor;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        mainlayout=(ConstraintLayout) findViewById(R.id.mylayout);
        defaultColor=ContextCompat.getColor(note_edit.this,R.color.design_default_color_on_primary);
        setContentView(R.layout.activity_note_edit);
        edit=findViewById(R.id.editText);
        Button colorpicker=(Button) findViewById(R.id.colorpicker);

        colorpicker.setOnClickListener(v -> openColorpicker());

        Intent intent= getIntent();
        noteId=intent.getIntExtra("noteId",-1);
        if(noteId !=-1){
            edit.setText(MainActivity.notes.get(noteId));
        }
        else{
            MainActivity.notes.add(noteId + "");
            noteId=MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();


                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.note", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }
    public void openColorpicker(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this,defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                edit.setTextColor(defaultColor);
            }
        } );
        ambilWarnaDialog.show();
    }

}