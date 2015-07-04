package com.naresh.fastnotes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.TokenPair;


public class GetNotes extends Activity {

    String title,content;
    EditText noteName;
    EditText noteContent;
    Button loginButton;

    private DropboxAPI<AndroidAuthSession> dropboxAPI;

    private final static String  DROPBOX_FILE_DIR= "/Notes/";
    private final static String DROPBOX_NAME= "dropbox_prefs";
    private final static String ACCESS_KEY="4nbnyug88dhl4ge";
    private final static String ACCESS_SECRET="e66vxnad0q9b5p0";
    private final static Session.AccessType ACCESS_TYPE= Session.AccessType.DROPBOX;
    Context context=this;
    private Boolean isUserLoggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_notes);

        noteName=(EditText) findViewById(R.id.noteName);
        noteContent=(EditText) findViewById(R.id.noteText);
        loginButton=(Button) findViewById(R.id.loginButton);


        loggedIn(false);


        AppKeyPair appKeys = new AppKeyPair(ACCESS_KEY, ACCESS_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        dropboxAPI = new DropboxAPI<AndroidAuthSession>(session);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserLoggedIn) {
                    dropboxAPI.getSession().unlink();
                    loggedIn(false);
                } else {
                    dropboxAPI.getSession().startOAuth2Authentication(GetNotes.this);
                }
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (dropboxAPI.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                dropboxAPI.getSession().finishAuthentication();

                String accessToken = dropboxAPI.getSession().getOAuth2AccessToken();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }

            title = noteName.getText().toString() + ".txt";
            content = noteContent.getText().toString();

            //to write the note to a file
            try {
                File path = new File(getFilesDir(), "FastNotes");
                if (!path.exists()) {
                    path.mkdir();
                }
                File noteFile = new File(path, title);
                FileOutputStream fileOutputStream = new FileOutputStream(noteFile);
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();

                UploadFile uploadFile=new UploadFile(context,dropboxAPI,DROPBOX_FILE_DIR,title,content);
                uploadFile.execute();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void loggedIn(boolean userLoggedIn)
    {
        isUserLoggedIn=userLoggedIn;
        loginButton.setText(userLoggedIn ? "Logout" : "Save & Sync");

    }
}
