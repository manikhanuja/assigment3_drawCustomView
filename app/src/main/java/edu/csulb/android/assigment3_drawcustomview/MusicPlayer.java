package edu.csulb.android.assigment3_drawcustomview;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MusicPlayer extends IntentService {
    public static String DEBUG_TAG = "Music Player Started";
    public MusicPlayer() {

        super("Start Music Player");
        Log.e(DEBUG_TAG,"Music Starting");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(DEBUG_TAG,"Music Ending");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        synchronized (this)
        {
            final MediaPlayer player = MediaPlayer.create(this,R.raw.eraser);
            player.start();
        }

    }
}
