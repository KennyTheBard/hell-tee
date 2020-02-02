package com.impaler.helltea.ui.main;

import android.content.Context;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.impaler.helltea.R;
import com.impaler.helltea.fragment.ExerciseListingFragment;
import com.impaler.helltea.fragment.HydrateTodayFragment;
import com.impaler.helltea.fragment.UpdatableFragment;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    private List<UpdatableFragment> fragments;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

        fragments = new ArrayList<>();
        fragments.add(new ExerciseListingFragment());
        fragments.add(new HydrateTodayFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(LocalDate startDate, LocalDate endDate) {
        fragments.forEach(f -> f.update(startDate, endDate));
    }
}