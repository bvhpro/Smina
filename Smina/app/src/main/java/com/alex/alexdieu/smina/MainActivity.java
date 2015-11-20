package com.alex.alexdieu.smina;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.util.DisplayMetrics;
import android.widget.Switch;
import android.widget.TextView;

import android.database.Cursor;

import java.io.IOException;
import java.sql.SQLException;

public class MainActivity extends AppModel  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Utils.scrWidth = metrics.widthPixels;

        /* -------- creating RelativeLayout --------- */
        final LinearLayout Body = new LinearLayout(this);
        Body.setOrientation(LinearLayout.VERTICAL);

        // creating LayoutParams
        LinearLayout.LayoutParams Body_LayoutParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        Body.setBackgroundColor(Color.WHITE);

        /* add top navigation */
        RelativeLayout TopNavigationLayout = new RelativeLayout(this);
        TopNavigationLayout.setId(101);
        TopNavigationLayout.setPadding(0, 0, 0, 0);

        RelativeLayout.LayoutParams topNavigationLayoutPR = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        topNavigationLayoutPR.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        TopNavigationLayout.setBackgroundColor(Color.BLUE);

        Body.addView(TopNavigationLayout, topNavigationLayoutPR);

        Button MenuBtn = new Button(this);
        MenuBtn.setText(getResources().getString(R.string.txt_btn_menu));
        MenuBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout Content = (RelativeLayout) findViewById(Utils.MAINCONTENT_LAYOUT_ID);
                if(Content != null){
                    Content.removeAllViews();
                    genView(Content, "home",null);
                }
            }
        });
        RelativeLayout.LayoutParams MenuBtnPR = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        MenuBtn.setBackgroundColor(Color.YELLOW);
        TopNavigationLayout.addView(MenuBtn, MenuBtnPR);

        TextView NaviTitle = new TextView(this);
        NaviTitle.setId(Utils.TOP_NAVIGATION_ID);
        RelativeLayout.LayoutParams NaviTitlePR = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        NaviTitlePR.addRule(RelativeLayout.CENTER_HORIZONTAL);
        NaviTitlePR.addRule(RelativeLayout.CENTER_VERTICAL);
        NaviTitlePR.addRule(RelativeLayout.RIGHT_OF,MenuBtn.getId());

        TopNavigationLayout.addView(NaviTitle, NaviTitlePR);

        /* end add top navigation */

        /* Add main content */

        final RelativeLayout Content = new RelativeLayout(this);

        // creating LayoutParams
        RelativeLayout.LayoutParams Content_LayoutParam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        Content_LayoutParam.addRule(RelativeLayout.BELOW, TopNavigationLayout.getId());
        Content.setPadding(Math.round(getResources().getDimension(R.dimen.MainContent_padding_left)),Math.round(getResources().getDimension(R.dimen.MainContent_padding_top)), Math.round(getResources().getDimension(R.dimen.MainContent_padding_right)), Math.round(getResources().getDimension(R.dimen.MainContent_padding_bottom)));
        Content.setId(Utils.MAINCONTENT_LAYOUT_ID);
        Body.addView(Content);

        /* end add main content */

        setContentView(Body, Body_LayoutParam);
        genView(Content, "home", null);
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

} // End MainActivity
