package com.example.android.miwok;

/**
 * Created by mahe on 6/7/2017.
 */
import android.content.Context;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;



/**

 * Provides the appropriate {@link Fragment} for a view pager.

 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Numbers", "Family Members", "Colours", "Phrases" };
    private Context mcontext;


    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mcontext=context;
    }



    @Override

    public Fragment getItem(int position) {

        if (position == 0)
            return new NumbersFragment();

        else if (position == 1)
            return new ColorsFragment();

        else if(position == 2)
            return new FamilyFragment();

        else if(position == 3)
            return new PhrasesFragment();

        else
            return null;

    }



    @Override

    public int getCount() {
        return 4;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch(position){
            case 0:
                return mcontext.getString(R.string.category_numbers);
            case 1:
                return mcontext.getString(R.string.category_colors);
            case 2:
                return mcontext.getString(R.string.category_family);
            case 3:
                return mcontext.getString(R.string.category_phrases);
            default:
                return null;
        }
    }


}
