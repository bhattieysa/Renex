package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapterSignUp extends FragmentPagerAdapter
{
    int tabcount;


    public PageAdapterSignUp(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0 : return new ContractorSignupFragment();
            case 1 : return new ClientSignupFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}