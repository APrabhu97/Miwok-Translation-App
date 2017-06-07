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


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {
    ArrayList<Words> words = new ArrayList<>();
    MediaPlayer mpl;
    AudioManager am;
    ListView listView;
    AudioManager.OnAudioFocusChangeListener afChangeListener= new AudioManager.OnAudioFocusChangeListener() {
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
            am.abandonAudioFocus(afChangeListener);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        words.add(new Words("Ek","one", R.drawable.number_one,R.raw.number_one));
        words.add(new Words("Do","two",R.drawable.number_two,R.raw.number_two));
        words.add(new Words("Teen","three",R.drawable.number_three,R.raw.number_three));
        words.add(new Words("Chaar","four",R.drawable.number_four,R.raw.number_four));
        words.add(new Words("Paanch","five",R.drawable.number_five,R.raw.number_five));
        words.add(new Words("Chey","six",R.drawable.number_six,R.raw.number_six));
        words.add(new Words("Saat","seven",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Words("Aath","eight",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Words("Naugh","nine",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Words("Das","ten",R.drawable.number_ten,R.raw.number_ten));


        listView = (ListView) rootView.findViewById(R.id.list);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words currentWord = words.get(position);
                releaseMediaPlayer();

                int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
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
