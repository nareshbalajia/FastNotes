package com.naresh.fastnotes;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    public List<String> notesList=null;
    public ApplicationAdaptor notesListAdaptor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesList=getNotesList();
        notesListAdaptor=getNotesListAdaptor();
        setListAdapter(notesListAdaptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try
        {
            String noteList=notesList.get(position);
            Intent viewNote=new Intent(this,ViewNote.class);
            Bundle bundle=new Bundle();
            bundle.putString("noteName",noteList);
            viewNote.putExtras(bundle);
            startActivity(viewNote);

        }
        catch(Exception e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public List<String> getNotesList()
    {
        ArrayList<String> notesList=new ArrayList<String>();
        File path=new File(getFilesDir(),"FastNotes");
        File file[]=path.listFiles();
        if(file!=null) {
            for (int i = 0; i < file.length; i++) {
                notesList.add(file[i].getName());
            }
        }
        else{
            notesList.add("No note created yet");
        }
        notesListAdaptor=new ApplicationAdaptor(MainActivity.this,R.layout.notes_list_adaptor_layout, notesList);
        return notesList;

    }

    public ApplicationAdaptor getNotesListAdaptor()
    {
        ApplicationAdaptor notesListAdaptor=null;
        notesListAdaptor=new ApplicationAdaptor(MainActivity.this,R.layout.notes_list_adaptor_layout, notesList);
        return notesListAdaptor;
    }
}
