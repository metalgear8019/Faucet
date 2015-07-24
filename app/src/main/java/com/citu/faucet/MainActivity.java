package com.citu.faucet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    static Timer timer = new Timer();
    boolean hasLost = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Faucet.hud = (TextView) findViewById(R.id.hitpoints);

        final ImageAdapter im = new ImageAdapter(this);
        Faucet.initAssets(im.getContext(), 4, 1);
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns((int) Math.sqrt(Faucet.count));
        gridview.setAdapter(im);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Faucet.reverseFaucetState(position);
                Faucet.updateFaucetState(position);
                if (Faucet.replenish())
                    goToNextScreen(hasLost);
            }
        });

        startOpeningFaucets();
    }

    class Task extends TimerTask {
        @Override
        public void run() {
            int delay = Faucet.randomSleepTime(10000);
            Task nextTask = new Task();
            timer.schedule(nextTask, delay);
            Log.d("MainActivity", new java.util.Date().toString());
            try {
                Faucet.generatePos();
                if (!Faucet.faucetStates[Faucet.getGeneratedPos()] && !hasLost) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Faucet.openFaucetState(Faucet.getGeneratedPos());
                            Faucet.updateFaucetState(Faucet.getGeneratedPos());
                            Faucet.generatePos();
                            hasLost = Faucet.drain();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (hasLost) {
                goToNextScreen(hasLost);
                nextTask.cancel();
            }
        }
    }

    public void startOpeningFaucets() {
        new Task().run();
    }

    public void goToNextScreen(boolean df) {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("deathFlag", df);
        startActivity(intent);
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
}