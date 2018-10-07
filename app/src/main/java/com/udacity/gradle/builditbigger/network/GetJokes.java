package com.udacity.gradle.builditbigger.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udafil.dhruvamsharma.androidjokelib.DisplayJokeActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class GetJokes extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;

    private WeakReference<Context> contextWeakReference;

    public GetJokes(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        MainActivity.loadJokes(contextWeakReference.get().getResources().getString(R.string.startLoadingTag), contextWeakReference.get());

    }

    @Override
    public String doInBackground(Void ... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }


        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        MainActivity.loadJokes(contextWeakReference.get().getResources().getString(R.string.stopLoadingTag), contextWeakReference.get());


        Intent intent = new Intent(contextWeakReference.get(), DisplayJokeActivity.class);
        intent.putExtra(contextWeakReference.get().getPackageName(), result);

        contextWeakReference.get().startActivity(intent);

    }
}
