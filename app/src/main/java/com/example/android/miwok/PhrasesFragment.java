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
public class PhrasesFragment extends Fragment {
    MediaPlayer mpl;
    AudioManager am ;
    ArrayList<Words> words = new ArrayList<Words>();
    AudioManager.OnAudioFocusChangeListener obj= new AudioManager.OnAudioFocusChangeListener() {
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
    public static PhrasesFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt("ARG",3);
        PhrasesFragment fragment = new PhrasesFragment();
        fragment.setArguments(args);
        return fragment;
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
            am.abandonAudioFocus(obj);
        }

    }


    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.activity_phrases, container, false);
        am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        //getActionBar().setDisplayHomeAsUpEnabled(true);


        // Create a list of words



        words.add(new Words("Where are you going?", "minto wuksus",R.raw.phrase_where_are_you_going));

        words.add(new Words("What is your name?", "tinnә oyaase'nә",R.raw.phrase_what_is_your_name));

        words.add(new Words("My name is...", "oyaaset...",R.raw.phrase_my_name_is));

        words.add(new Words("How are you feeling?", "michәksәs?",R.raw.phrase_how_are_you_feeling));

        words.add(new Words("I’m feeling good.", "kuchi achit",R.raw.phrase_im_feeling_good));

        words.add(new Words("Are you coming?", "әәnәs'aa?",R.raw.phrase_are_you_coming));

        words.add(new Words("Yes, I’m coming.", "hәә’ әәnәm",R.raw.phrase_yes_im_coming));

        words.add(new Words("I’m coming.", "әәnәm",R.raw.phrase_im_coming));

        words.add(new Words("Let’s go.", "yoowutis",R.raw.phrase_lets_go));

        words.add(new Words("Come here.", "әnni'nem",R.raw.phrase_come_here));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WordAdapter adapter = new WordAdapter(getActivity(), words);



        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.

        // There should be a {@link ListView} with the view ID called list, which is declared in the

        // word_list.xml layout file.

        ListView listView = (ListView) getActivity().findViewById(R.id.plist);



        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the

        // {@link ListView} will display list items for each {@link Word} in the list.

        listView.setAdapter(adapter);
        final ListView lv = (ListView) getActivity().findViewById(R.id.plist);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words currentWord = (Words) lv.getItemAtPosition(position);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(obj, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
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
