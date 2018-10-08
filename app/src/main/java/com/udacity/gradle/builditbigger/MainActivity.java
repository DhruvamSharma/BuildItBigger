package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.network.GetJokes;


public class MainActivity extends AppCompatActivity {

    private static ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.jokeLoader_pb);
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

    /**
     * This method takes a joke from the java library- "javaJokeLib" and
     * sends the joke to the Android Library that displays the joke
     * @param view
     */
    public void tellJoke(View view) {

        //getting jokes from the GCE
        GetJokes jokes = new GetJokes(this);
        jokes.execute();

    }


    public static void loadJokes(String task, Context context) {

        if(task.equals(context.getResources().getString(R.string.startLoadingTag))) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else if(task.equals(context.getResources().getString(R.string.stopLoadingTag))){
            mProgressBar.setVisibility(View.GONE);
        }

    }


}
