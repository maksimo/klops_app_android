package ru.klops.klops.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.klops.klops.fragments.NewDataNewsFragment;
import ru.klops.klops.fragments.PopularDataNewsFragment;

public class SlideAdapter extends FragmentStatePagerAdapter {
    private int pages = 2;

    public SlideAdapter(FragmentManager manager, int pages) {
        super(manager);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewDataNewsFragment();
            case 1:
                return new PopularDataNewsFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return pages;
    }

}
