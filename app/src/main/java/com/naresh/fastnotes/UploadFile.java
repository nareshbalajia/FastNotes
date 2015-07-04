package com.naresh.fastnotes;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class UploadFile extends AsyncTask<Void,Void,Boolean>  {

    private Context context;
    private DropboxAPI dropboxAPI;
    String path;
    String title;
    String content;

    public UploadFile(Context context, DropboxAPI dropboxAPI, String path, String title, String content) {
        this.context = context;
        this.dropboxAPI = dropboxAPI;
        this.path = path;
        this.title = title;
        this.content = content;
    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        final File tempDir=context.getCacheDir();
        File tempFile;
        FileWriter fileWriter;

        try
        {
            tempFile=File.createTempFile(title,".txt",tempDir);
            fileWriter=new FileWriter(tempFile);
            fileWriter.write(content);
            fileWriter.close();

            FileInputStream fileInputStream=new FileInputStream(tempFile);
            dropboxAPI.putFile(path + title, fileInputStream, tempFile.length(), null, null);
            tempFile.delete();
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(DropboxException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        if(result)
        {
            Toast.makeText(context,"File uploaded sucessfully..!!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Error uploading file!!",Toast.LENGTH_SHORT).show();
        }
    }
}
