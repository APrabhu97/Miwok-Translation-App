package com.example.android.miwok;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mahe on 5/26/2017.
 */
public class Words {
    private String mMiwo;
    private String mEng;
    private int imgp = -1;
    private int aud;
    public Words(String eng, String miwo, int ani){
        mMiwo=miwo;
        mEng=eng;
        aud=ani;
    }
    public Words(String eng, String miwo, int img,int ani){
        mMiwo=miwo;
        mEng=eng;
        imgp = img;
        aud=ani;
    }
    public String getMiwokTranslation(){

        return mMiwo;
    }
    public String getEnglishTranslation(){

        return mEng;
    }
    public int getImageResource(){

        return imgp;
    }
    public boolean hasImage(){
        if(imgp==-1)
            return false;
        return true;
    }
    public int getAudioResource(){
        return aud;
    }
}
