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
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udafil.dhruvamsharma.androidjokelib.DisplayJokeActivity;
import com.udafil.dhruvamsharma.javajokelib.MyClass;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class GetJokes extends AsyncTask<Context, Void, String> {

    private static MyApi myApiService = null;

    private WeakReference<Context> contextWeakReference;

    @Override
    public String doInBackground(Context... pairs) {
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

        contextWeakReference = new WeakReference<>( pairs[0]);

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        Intent intent = new Intent(contextWeakReference.get(), DisplayJokeActivity.class);
        intent.putExtra(contextWeakReference.get().getPackageName(), result);

        contextWeakReference.get().startActivity(intent);

    }
}
