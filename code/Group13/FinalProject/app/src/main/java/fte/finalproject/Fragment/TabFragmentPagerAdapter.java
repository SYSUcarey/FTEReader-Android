package fte.finalproject.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

public class TabFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private FragmentManager fm;
    private List<Fragment> list;

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
