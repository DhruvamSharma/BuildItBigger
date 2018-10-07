package com.udafil.dhruvamsharma.androidjokelib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        if(getIntent().hasExtra(getPackageName())) {

            setUpActivity(getIntent().getStringExtra(getPackageName()));

        }
    }

    private void setUpActivity(String joke) {

        TextView jokeTextView = findViewById(R.id.joke_tv);

        jokeTextView.setText(joke);

    }
}
