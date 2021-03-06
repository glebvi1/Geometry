package com.example.abcgeometry.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.abcgeometry.R;
import com.example.abcgeometry.additional.TabOne;
import com.example.abcgeometry.drawing.TabTwo;
import com.example.abcgeometry.additional.TabZero;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_0, R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private static final String TABS = "tab11111111111111121";

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        //TabZero tabZero;

            switch (position) {
                case 0 :
                    TabZero tabZero = new TabZero();
                    return tabZero;
                case 1:
                        TabTwo two = new TabTwo();
                        return two;
                case 2:
                        TabOne one = new TabOne();
                        return one;
                default:
                    return null;
            }

/*        switch (position) {
            case 0:
                return new TabZero();
            case 1:
                return new TabTwo();
            case 2:
                return new TabOne();
            default:
                return null;
        }*/
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
