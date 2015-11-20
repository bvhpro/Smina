package com.alex.alexdieu.learnandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent it = getIntent();
        int a = it.getIntExtra("a", 0);
        int b = it.getIntExtra("b", 0);

        int c = a + b;

        if(c==0){
            setContentView(R.layout.activity_result_1);
            this.setTitle("Title 1");
        }
        else{
            setContentView(R.layout.activity_result);
            this.setTitle("Title 2");
        }

        TextView d = (TextView)findViewById(R.id.tv_result);
        d.setText(String.valueOf(c));
        //Log.v("settext ok...", "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
