package com.alex.alexdieu.smina;

/**
 * Created by Alex Dieu on 9/6/2015.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

class SminaDbHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/"+Utils.PACKAGE_NAME+"/databases/";
    private static String DB_NAME = "sminadb.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public SminaDbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                Log.v("Copy database file...","");
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
        File dbFile = myContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

        Log.v("Copy done!....","");
    }

    public void openDataBase() throws SQLException{
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Cursor getDataDo(String sqlQuery){
        Cursor res = null;
        Log.v("Cau truy van: ",sqlQuery);
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            res = db.rawQuery(sqlQuery, null);
            return res;
        }
        catch (SQLiteException e){

        }
        return res;
    }

    /*
    * Các màn hình muốn getData, giá trị của scrID:
    * - list từ mới của 1 bài học cụ thể, 1
    * - list bài học ở 1 trình độ cụ thể, 2
    * - list từ mới yêu thích, 3
    * - list ngữ pháp trong 1 bài cụ thể, 4
    * - list chữ cái, 5
    * - list từ mới trong màn hình tìm kiếm, 6
    * - list kanji trong 1 bài học cụ thể, 7
    * - list kaiwa trong 1 bài học cụ thể, 8
    * */
    public Cursor getData(String keyword, int ID, int scrID){
        Cursor res = null;
        try{
            String sql = "select * from posts where active = 1 ";
            switch (scrID){
                case 1:
                    sql += " and type = 'w' and lesson_ID = " + ID;
                    break;
                case 2:
                    sql += " and txt_title != '' and type = 'l' and level = " + ID;
                    break;
                case 3:
                    sql += " and type = 'w' and is_favour = 1 ";
                    break;
                case 4:
                    sql += " and type = 'g' and lesson_ID = " + ID;
                    break;
                case 5:
                    sql += " and type = 'c' ";
                    break;
                case 6:
                    sql += " and type = 'w' and txt_title like '%"+ keyword +"%' or txt_hira_kun like '%"+ keyword +"%' or txt_kata_on like '%"+ keyword +"%' ";
                    break;
                case 7:
                    sql += " and type = 'kj' and lesson_ID = " + ID;
                    break;
                case 8:
                    sql += " and type = 'kw' and lesson_ID = " + ID;
                    break;
                default:
                    break;
            }

            sql += " order by sort_order ASC ";

            res =  getDataDo(sql);
            Log.v("So ban ghi lay ra dc: ",String.valueOf(numberOfRows("w")));
        }
        catch (SQLiteException e){
            //Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            //toast.show();
        }
        return res;
    }

    public int numberOfRows(String type){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "posts"," type = '"+ type +"' ");
        return numRows;
    }

}
