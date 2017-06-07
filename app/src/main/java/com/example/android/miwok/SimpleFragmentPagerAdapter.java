package com.example.android.miwok;

/**
 * Created by mahe on 6/7/2017.
 */
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;



/**

 * Provides the appropriate {@link Fragment} for a view pager.

 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {



    public SimpleFragmentPagerAdapter(FragmentManager fm) {

        super(fm);

    }



    @Override

    public Fragment getItem(int position) {

        if (position == 0) {

            return new NumbersFragment();

        } else if (position == 1){

            return new FamilyFragment();

        } else if(position == 2){

            return new ColorsFragment();

        }
        else {
            return new PhrasesFragment();
        }

    }



    @Override

    public int getCount() {

        return 4;

    }

}
