package com.alex.alexdieu.learnandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean timerIsRunning;
    private int second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // run timer
        runTimer();

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

    public void onSendValue(View v){
        //Intent intent = new Intent(this,ResultActivity.class);
        Intent intent = new Intent(Intent.ACTION_SEND);

        EditText a = (EditText)findViewById(R.id.editText1) ;
        EditText b = (EditText)findViewById(R.id.editText2) ;
        //int aa =  Integer.parseInt(a.getText().toString());
        //Log.v("gia tri a: ", String.valueOf(aa));
        //int bb =  Integer.parseInt(b.getText().toString());
        //Log.v("gia tri b: ", String.valueOf(bb));

        //intent.putExtra("a",aa);
        //intent.putExtra("b",bb);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,a.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT,b.getText().toString());

        startActivity(intent);
    }

    public void onStartTimerClick(View view){
        timerIsRunning = true;
    }

    public void onPauseTimerClick(View view){
        timerIsRunning = false;
    }

    public void onResetTimerClick(View view){
        timerIsRunning = false;
        second = 0;
    }

    private void runTimer(){
        final TextView tv = (TextView)findViewById(R.id.timeView);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = second / 3600;
                int minus = (second % 3600) / 60;
                int secs = second % 60;

                String time = String.format("%02d:%02d:%02d", hours, minus, secs);
                tv.setText(time);

                if (timerIsRunning) {
                    second++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }


}
