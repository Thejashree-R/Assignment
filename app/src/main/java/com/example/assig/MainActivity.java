

package com.example.assig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes= new ArrayList<>();
    static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=findViewById(R.id.listView);

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.assig", Context.MODE_PRIVATE);
        HashSet<String > set=(HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes.add("write your note here");
        }else {
            notes=new ArrayList<>(set);
        }

        arrayAdapter= new ArrayAdapter(this,R.layout.bullet,notes);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),note_edit.class);
                intent.putExtra("noteId",position);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int itemtodelete=position;

                new AlertDialog.Builder(MainActivity.this)

                        .setTitle("sure want to delete the note?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                notes.remove(itemtodelete);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.assig", Context.MODE_PRIVATE);

                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();

                            }
                        })
                        .setNegativeButton("no",null)
                        .show();
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.addNote){
            Intent intent=new Intent(getApplicationContext(),note_edit.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}