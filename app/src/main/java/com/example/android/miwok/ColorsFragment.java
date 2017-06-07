package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {
    MediaPlayer mpl;
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener col = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mpl.pause();
                mpl.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mpl.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    private void releaseMediaPlayer() {

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

    public ColorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_colours, container, false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        // getActionBar().setDisplayHomeAsUpEnabled(true);


        // Create a list of words

        ArrayList<Words> words = new ArrayList<Words>();

        words.add(new Words("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));

        words.add(new Words("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        words.add(new Words("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));

        words.add(new Words("green", "chokokki", R.drawable.color_green, R.raw.color_green));

        words.add(new Words("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));

        words.add(new Words("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));

        words.add(new Words("black", "kululli", R.drawable.color_black, R.raw.color_black));

        words.add(new Words("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words);
        final ListView listView = (ListView) getActivity().findViewById(R.id.clist);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words currentWord = (Words) listView.getItemAtPosition(position);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(col, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mpl = MediaPlayer.create(getActivity(), currentWord.getAudioResource());
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
        return rootView;
    }
}


