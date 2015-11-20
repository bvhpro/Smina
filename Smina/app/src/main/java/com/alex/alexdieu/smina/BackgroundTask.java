package com.alex.alexdieu.smina;

import android.content.ContentValues;
import android.os.AsyncTask;

/**
 * Created by Alex Dieu on 11/2/2015.
 */
public class BackgroundTask extends AsyncTask<Integer,Process,Boolean> {

    ContentValues values;

    protected void onPreExecute(){

    }

    @Override
    protected Boolean doInBackground(Integer... params) {

        try{


            return true;
        }
        catch (Exception e){
            return  false;
        }

    }



}
