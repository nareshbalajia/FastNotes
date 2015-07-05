package com.naresh.fastnotes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ViewNote extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        TextView noteContent= (TextView) findViewById(R.id.showText);
        Bundle bundle=getIntent().getExtras();
        String noteTitle=bundle.getString("noteName");

        File path = new File(getFilesDir(), "FastNotes");
        File noteFile=new File(path,noteTitle);

        StringBuilder stringBuilder=new StringBuilder();
        try
        {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(noteFile));
            String line;

            while((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
            bufferedReader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        noteContent.setText(stringBuilder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_note, menu);
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
}
