package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class FamilyFragment extends Fragment {
    MediaPlayer mpl;
    ArrayList<Words> words = new ArrayList<Words>();
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener fam = new AudioManager.OnAudioFocusChangeListener() {
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

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_family, container, false);
        am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        //getActionBar().setDisplayHomeAsUpEnabled(true);




        // Create a list of words



        words.add(new Words("father", "әpә",R.drawable.family_father,R.raw.family_father));

        words.add(new Words("mother", "әṭa",R.drawable.family_mother,R.raw.family_mother));

        words.add(new Words("son", "angsi",R.drawable.family_son,R.raw.family_son));

        words.add(new Words("daughter", "tune",R.drawable.family_daughter,R.raw.family_daughter));

        words.add(new Words("older brother", "taachi",R.drawable.family_older_brother,R.raw.family_older_brother));

        words.add(new Words("younger brother", "chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));

        words.add(new Words("older sister", "teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));

        words.add(new Words("younger sister", "kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));

        words.add(new Words("grandmother ", "ama",R.drawable.family_grandmother,R.raw.family_grandmother));

        words.add(new Words("grandfather", "paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The

        // adapter knows how to create list items for each item in the list.

        WordAdapter adapter = new WordAdapter(getActivity(), words);



        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.

        // There should be a {@link ListView} with the view ID called list, which is declared in the

        // word_list.xml layout file.

        final ListView listView = (ListView) getActivity().findViewById(R.id.flist);



        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the

        // {@link ListView} will display list items for each {@link Word} in the list.

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words currentWord = (Words) listView.getItemAtPosition(position);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(fam, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
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
    }
}
