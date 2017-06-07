package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



import java.util.ArrayList;



public class Colours extends AppCompatActivity {
    MediaPlayer mpl;
    AudioManager am ;
    AudioManager.OnAudioFocusChangeListener col=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mpl.pause();
                mpl.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mpl.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        setContentView(R.layout.activity_colours);
       // getActionBar().setDisplayHomeAsUpEnabled(true);


        // Create a list of words

        ArrayList<Words> words = new ArrayList<Words>();

        words.add(new Words("red", "weṭeṭṭi",R.drawable.color_red,R.raw.color_red));

        words.add(new Words("mustard yellow", "chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        words.add(new Words("dusty yellow", "ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));

        words.add(new Words("green", "chokokki",R.drawable.color_green,R.raw.color_green));

        words.add(new Words("brown", "ṭakaakki",R.drawable.color_brown,R.raw.color_brown));

        words.add(new Words("gray", "ṭopoppi",R.drawable.color_gray,R.raw.color_gray));

        words.add(new Words("black", "kululli",R.drawable.color_black,R.raw.color_black));

        words.add(new Words("white", "kelelli",R.drawable.color_white,R.raw.color_white));



        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The

        // adapter knows how to create list items for each item in the list.

        WordAdapter adapter = new WordAdapter(this, words);



        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.

        // There should be a {@link ListView} with the view ID called list, which is declared in the

        // word_list.xml layout file.

        ListView listView = (ListView) findViewById(R.id.clist);



        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the

        // {@link ListView} will display list items for each {@link Word} in the list.

        listView.setAdapter(adapter);
        final ListView lv = (ListView) findViewById(R.id.clist);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words currentWord = (Words) lv.getItemAtPosition(position);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(col, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mpl = MediaPlayer.create(getApplicationContext(), currentWord.getAudioResource());
                    mpl.start();
                    mpl.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer(){

        // If the media player is not null, then it may be currently playing a sound.

        if (mpl != null) {

            // Regardless of the current state of the media player, release its resources

            // because we no longer need it.

            mpl.release();



            // Set the media player back to null. For our code, we've decided that

            // setting the media player to null is an easy way to tell that the media player

            // is not configured to play an audio file at the moment.

            mpl = null;

        }

    }

}
