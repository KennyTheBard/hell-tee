package com.impaler.helltea.fragment;

import androidx.fragment.app.Fragment;

import org.threeten.bp.LocalDate;

public abstract class UpdatableFragment extends Fragment {

    public abstract void update(LocalDate startDate, LocalDate endDate);

}
