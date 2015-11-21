package com.alex.alexdieu.smina;


import android.app.Dialog;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Alex Dieu on 11/8/2015.
 */
public class AppModel extends Common {
    protected SminaDbHelper dbHelper = new SminaDbHelper(this);

    public AppModel(){
    }

    public void initDb(){
        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            dbHelper.openDataBase();
        }catch(SQLException sqle){
            throw new Error("Unable to open database");
        }
    }

    public void buildTheListRowWord(final Cursor data, RelativeLayout MainContent){
        LinearLayout ContentOfList = new LinearLayout(this);
        LinearLayout.LayoutParams lop = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ContentOfList.setOrientation(LinearLayout.VERTICAL);

        ContentOfList.setPadding(10, 10, 10, 10);

        try{
            if(data.moveToFirst()){
                do {
                    RelativeLayout row = new RelativeLayout(this);
                    row.setBackgroundColor(getResources().getColor(R.color.AntiqueWhite));
                    LinearLayout.LayoutParams rowParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    //row.setPadding(10,20,10,20);

                    TextView tv = new TextView(this);
                    final String audio_name = data.getString(3);
                    tv.setText(data.getString(5) + System.getProperty("line.separator") + data.getString(7) + System.getProperty("line.separator") + data.getString(4));
                    tv.setId(data.getInt(0));
                    tv.setLineSpacing((float) 0, (float) 1.50);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new PlaySoundBackgroundTask().execute("audio/" + audio_name + ".mp3");
                        }
                    });
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.ALIGN_LEFT);
                    lp.addRule(RelativeLayout.CENTER_VERTICAL);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setBackgroundColor(getResources().getColor(R.color.Lavender));
                    row.addView(tv, lp);
                    lp = null;

                    Button addNoteBtn = new Button(this);
                    addNoteBtn.setText("Add Note");
                    addNoteBtn.setId(11111);
                    addNoteBtn.setPadding(10,0,10,0);
                    RelativeLayout.LayoutParams addNoteBtnParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    addNoteBtn.setBackgroundColor(getResources().getColor(R.color.AliceBlue));
                    addNoteBtnParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    addNoteBtnParam.addRule(RelativeLayout.CENTER_VERTICAL);
                    addNoteBtnParam.setMargins(0,0,10,0);
                    row.addView(addNoteBtn, addNoteBtnParam);

                    Button addFovourBtn = new Button(this);
                    addFovourBtn.setText("Add");
                    RelativeLayout.LayoutParams btnParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    addFovourBtn.setBackgroundColor(getResources().getColor(R.color.AliceBlue));
                    btnParam.addRule(RelativeLayout.LEFT_OF, addNoteBtn.getId());
                    btnParam.addRule(RelativeLayout.CENTER_VERTICAL);
                    btnParam.setMargins(0,0,10,0);
                    row.addView(addFovourBtn, btnParam);

                    ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class
                    Paint paint = rectShapeDrawable.getPaint();
                    paint.setColor(Color.GRAY);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(2); // you can change the value of 5
                    row.setBackgroundDrawable(rectShapeDrawable);

                    rowParam.setMargins(0, 0, 0, (int)getResources().getDimension(R.dimen.List_item_margin_bottom));
                    ContentOfList.addView(row,rowParam);
                    rowParam = null;
                }
                while (data.moveToNext());

            }
        }
        catch (Exception e) {
            Log.v("Co loi xay ra", e.toString());
        }
        MainContent.addView(ContentOfList, lop);

    }


    public void buildTheListLesson(final Cursor data, final RelativeLayout MainContent){
        GridLayout ContentOfList = new GridLayout(this);

        GridLayout.LayoutParams lop = new GridLayout.LayoutParams();
        lop.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lop.height = ViewGroup.LayoutParams.MATCH_PARENT;
        ContentOfList.setOrientation(GridLayout.HORIZONTAL);
        ContentOfList.setColumnCount(5);

        try{
            if(data.moveToFirst()){
                do {
                    TextView tv = new TextView(this);
                    tv.setText(data.getString(4));
                    final int lessonID = data.getInt(0);
                    tv.setId(lessonID);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainContent.removeAllViews();
                            String[] sTerm = {"", "" + lessonID, "1"};
                            genView(MainContent, "new_word", sTerm);
                        }
                    });
                    GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                    lp.setMargins(Math.round(getResources().getDimension(R.dimen.Grid_item_margin_left)), Math.round(getResources().getDimension(R.dimen.Grid_item_margin_top)), Math.round(getResources().getDimension(R.dimen.Grid_item_margin_right)), Math.round(getResources().getDimension(R.dimen.Grid_item_margin_bottom)));
                    tv.setPadding(Math.round(getResources().getDimension(R.dimen.Grid_item_padding_left)), Math.round(getResources().getDimension(R.dimen.Grid_item_padding_top)), Math.round(getResources().getDimension(R.dimen.Grid_item_padding_right)), Math.round(getResources().getDimension(R.dimen.Grid_item_padding_bottom)));
                    tv.setBackgroundColor(Color.LTGRAY);
                    tv.setMinimumWidth((int) MainContent.getWidth() / 5);

                    ContentOfList.addView(tv, lp);
                    lp = null;
                }
                while (data.moveToNext());

            }
        }
        catch (Exception e) {
            Log.v("Co loi xay ra", e.toString());
        }
        MainContent.addView(ContentOfList, lop);

    }

    public void buildTheListChars(final Cursor data, final RelativeLayout MainContent){
        GridLayout ContentOfList = new GridLayout(this);

        GridLayout.LayoutParams lop = new GridLayout.LayoutParams();
        lop.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lop.height = ViewGroup.LayoutParams.MATCH_PARENT;
        ContentOfList.setOrientation(GridLayout.HORIZONTAL);
        ContentOfList.setColumnCount(5);

        try{
            if(data.moveToFirst()){
                do {
                    TextView tv = new TextView(this);
                    tv.setTextSize(30);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tv.setText(data.getString(4));
                    final String audio_name = data.getString(3);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new PlaySoundBackgroundTask().execute("audio/chars/" + audio_name + ".wav");

                            // create popup windows



                            // end create popup windows
                        }
                    });
                    GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                    lp.setMargins(Math.round(getResources().getDimension(R.dimen.Grid_item_margin_left)), Math.round(getResources().getDimension(R.dimen.Grid_item_margin_top)), Math.round(getResources().getDimension(R.dimen.Grid_item_margin_right)), Math.round(getResources().getDimension(R.dimen.Grid_item_margin_bottom)));
                    tv.setPadding(Math.round(getResources().getDimension(R.dimen.Grid_item_padding_left)), Math.round(getResources().getDimension(R.dimen.Grid_item_padding_top)), Math.round(getResources().getDimension(R.dimen.Grid_item_padding_right)), Math.round(getResources().getDimension(R.dimen.Grid_item_padding_bottom)));
                    tv.setBackgroundColor(Color.LTGRAY);
                    tv.setMinimumWidth((int) MainContent.getWidth() / 5);

                    ContentOfList.addView(tv, lp);
                    lp = null;
                }
                while (data.moveToNext());

            }
        }
        catch (Exception e) {
            Log.v("Co loi xay ra", e.toString());
        }
        MainContent.addView(ContentOfList, lop);

    }


    public void genView(final RelativeLayout MainContent ,String screenName, String[]   args){
        initDb();
        Utils.TOP_NAVIGATION_TITLE = getResources().getString(R.string.app_name);

        switch (screenName){
            case "home":
                float loW = Utils.scrWidth/2;
                RelativeLayout.LayoutParams logoPR = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                logoPR.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

                ImageView img = new ImageView(this);
                img.setImageResource(R.drawable.logo);
                img.setId(0);
                MainContent.addView(img, logoPR);

                /* -------- search box --------- */
                RelativeLayout searchBoxLayout = new RelativeLayout(this);
                searchBoxLayout.setId(10);
                searchBoxLayout.setPadding(0, (int) (loW * 1.3), 0, 10);

                RelativeLayout.LayoutParams searchBoxLayoutPR = new RelativeLayout.LayoutParams((int)loW, ViewGroup.LayoutParams.WRAP_CONTENT);
                searchBoxLayoutPR.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                searchBoxLayoutPR.addRule(RelativeLayout.BELOW, img.getId());
                MainContent.addView(searchBoxLayout, searchBoxLayoutPR);

                final EditText searchBox = new EditText(this);
                searchBox.setId(11);
                searchBox.setWidth((int) (loW * 0.75));
                searchBoxLayout.addView(searchBox);

                Button searchBtn = new Button(this);
                searchBtn.setText(getResources().getString(R.string.txt_search_btn));
                searchBtn.setWidth((int) (loW * 0.2));
                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String key = searchBox.getText().toString().trim();
                        if(!key.isEmpty() && key != null){
                            MainContent.removeAllViews();
                            String[] searchTerm = {key,"","6"};
                            genView(MainContent, "find_word_result",searchTerm);
                        }
                    }
                });
                RelativeLayout.LayoutParams searchBtnPR = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                searchBtnPR.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                searchBoxLayout.addView(searchBtn, searchBtnPR);

                /* -------- search box --------- */

                /* -------- add button and OnClickListener event --------- */
                RelativeLayout.LayoutParams lp0 = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp0.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                lp0.addRule(RelativeLayout.BELOW, searchBoxLayout.getId());

                Button btn_bcc = new Button(this);
                btn_bcc.setText(getResources().getString(R.string.txt_bangchucai));
                btn_bcc.setWidth(200);
                btn_bcc.setId(1);

                btn_bcc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainContent.removeAllViews();
                        String[] sTerm = {"","","5"};
                        genView(MainContent, "charTable",sTerm);
                    }
                });
                MainContent.addView(btn_bcc,lp0);
                /* -------- / add button and OnClickListener event --------- */

                /* -------- add button and OnClickListener event --------- */
                RelativeLayout.LayoutParams lp2 = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                lp2.addRule(RelativeLayout.BELOW, btn_bcc.getId());

                Button btn_n5 = new Button(this);
                btn_n5.setText(getResources().getString(R.string.txt_n5));
                btn_n5.setWidth(200);
                btn_n5.setId(2);

                btn_n5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainContent.removeAllViews();
                        String[] sTerm = {"","5","2"};
                        genView(MainContent, "ListN5",sTerm);
                    }
                });
                MainContent.addView(btn_n5,lp2);
        /* -------- / add button and OnClickListener event --------- */

                /* -------- add button and OnClickListener event --------- */
                RelativeLayout.LayoutParams lp1 = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                lp1.addRule(RelativeLayout.BELOW, btn_n5.getId());

                Button btn_n4 = new Button(this);
                btn_n4.setText(getResources().getString(R.string.txt_n4));
                btn_n4.setWidth(200);
                btn_n4.setId(3);

                btn_n4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainContent.removeAllViews();
                        String[] sTerm = {"","4","2"};
                        genView(MainContent, "ListN5",sTerm);
                    }
                });
                MainContent.addView(btn_n4,lp1);
        /* -------- / add button and OnClickListener event --------- */
                break;

            case "charTable":
                Utils.TOP_NAVIGATION_TITLE = getResources().getString(R.string.txt_bangchucai);
                Utils.LIST_TYPE = 5;
                new SQLBackgroundTask_GetData().execute(args);
                break;

            case "ListN4":
                Utils.TOP_NAVIGATION_TITLE = getResources().getString(R.string.txt_n4);
                Utils.LIST_TYPE = 2;
                new SQLBackgroundTask_GetData().execute(args);
                break;

            case "ListN5":
                Utils.TOP_NAVIGATION_TITLE = getResources().getString(R.string.txt_n5);
                Utils.LIST_TYPE = 2;
                new SQLBackgroundTask_GetData().execute(args);
                break;

            case "new_word":
                Utils.TOP_NAVIGATION_TITLE = getResources().getString(R.string.txt_tuvung);
                Utils.LIST_TYPE = 1;
                new SQLBackgroundTask_GetData().execute(args);
                break;

            case "favour_word":
                Utils.TOP_NAVIGATION_TITLE = getResources().getString(R.string.scrName_favour_word);
                Utils.LIST_TYPE = 1;
                new SQLBackgroundTask_GetData().execute(args);
                break;

            case "find_word_result":
                Utils.TOP_NAVIGATION_TITLE = getResources().getString(R.string.scrName_search_result);
                Utils.LIST_TYPE = 1;
                new SQLBackgroundTask_GetData().execute(args);

                break;

            case "grammar":
                break;

            case "kanJi":
                break;

            case "kaiWa":
                break;

            default:
                break;
        }

        TextView Navigation_Title = (TextView) this.findViewById(Utils.TOP_NAVIGATION_ID);

        Navigation_Title.setText(Utils.TOP_NAVIGATION_TITLE);

    }


    public class SQLBackgroundTask_GetData extends AsyncTask<String,Process,Cursor> {
        protected void onPreExecute(){
        }

        @Override
        protected Cursor doInBackground(String... params) {
            Cursor result = null;
            try{
                result = dbHelper.getData(params[0], Integer.valueOf( "0" + params[1]), Integer.valueOf( "0" + params[2]));
            }
            catch (Exception e){
                Log.v("Loi con me gi the nhi","");
            }
            return result;
        }



        /*
        * Giá trị của biến LIST_TYPE
        * - List từ mới, 1
        * - List các bài học tương ứng với mỗi trình độ, 2
        * - List kanji của mỗi bài, 3
        * - List ngữ pháp trong 1 bài, 4
        * - List chữ cái trong bảng chữ cái, 5
        *
        * */
        @Override
        protected void onPostExecute(Cursor result){
            RelativeLayout MainContent = (RelativeLayout) findViewById(Utils.MAINCONTENT_LAYOUT_ID);
            switch (Utils.LIST_TYPE){
                case 1:
                    buildTheListRowWord(result, MainContent);
                    break;
                case 2:
                    buildTheListLesson(result, MainContent);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    buildTheListChars(result, MainContent);
                    break;
                default:
                    break;
            }
            dbHelper.close();
        }

    }


    public class PlaySoundBackgroundTask extends AsyncTask<String, Process, Void> {
        protected void onPreExecute(){
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                MediaPlayer m = new MediaPlayer();
                AssetFileDescriptor descriptor = getAssets().openFd(params[0]);
                m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();
                m.prepare();
                m.setVolume(1f, 1f);
                m.setLooping(false);
                m.start();
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("Loi: ","ko mo dc file am thanh");
            }
            return null;
        }
    }




}
