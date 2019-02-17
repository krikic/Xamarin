package com.okason.prontoshop.core;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.okason.prontoshop.fragments.CustomerListFragment;
import com.okason.prontoshop.fragments.ProductListFragment;
import com.okason.prontoshop.fragments.CheckoutFragment;
import com.okason.prontoshop.fragments.TransactionListFragment;

/**
 * Created by Valentine on 4/10/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment selectedFragment;
        switch (position) {
            case 0:
                selectedFragment = new ProductListFragment();
                break;
            case 1:
                selectedFragment = new CustomerListFragment();
                break;
            case 2:
                selectedFragment = new CheckoutFragment();
                break;
            case 3:
                selectedFragment = new TransactionListFragment();
                break;
            default:
                selectedFragment = new ProductListFragment();
                break;
        }
        return selectedFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Products";
                break;
            case 1:
                title = "Customers";
                break;
            case 2:
                title = "Shopping Cart";
                break;
            case 3:
                title = "Transactions";
                break;
        }
        return title;
    }
}
