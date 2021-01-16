package com.loginius.loginiusinfotech;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyTabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyTabAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                ProjectFragment sell=new ProjectFragment(context);
                return sell;
            case 1:
                AttendanceFragment available=new AttendanceFragment(context);
                return  available;
            default:
                return null;
        }
    }

}
