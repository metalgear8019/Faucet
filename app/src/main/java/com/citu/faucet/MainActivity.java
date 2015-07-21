package com.citu.faucet;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageAdapter im = new ImageAdapter(this);
        FaucetGrid.initAssets(im.getContext(), 4);
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns((int) Math.sqrt(FaucetGrid.count));
        gridview.setAdapter(im);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                /*Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();*/
                FaucetGrid.reverseFaucetState(position);
                ImageView imageView = (ImageView) v;
                imageView.setImageResource(FaucetGrid.getResourceAt(position));
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int pos = (int) (Math.random() * FaucetGrid.count);
                                FaucetGrid.reverseFaucetState(pos);
                                ImageView imageView = FaucetGrid.ivList[pos];
                                imageView.setImageResource(FaucetGrid.getResourceAt(pos));
                                imageView.invalidate();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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