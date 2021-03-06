package com.jplate8.dndcharsheet;

import android.app.Fragment;
import android.app.Activity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    /** Constructor used each time a new tab is created.
      * @param activity  The host Activity, used to instantiate the fragment
      * @param tag  The identifier tag for the fragment
      * @param clz  The fragment's Class, used to instantiate the fragment
      */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
    	Fragment preInitializedFragment = (Fragment) mActivity.getFragmentManager().findFragmentByTag(mTag);
    	
        // Check if the fragment is already initialized
        if (mFragment == null && preInitializedFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(R.id.fragment_container, mFragment, mTag);
        } else if (mFragment != null) {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        } else if (preInitializedFragment != null) {
        	ft.attach(preInitializedFragment);
        	mFragment = preInitializedFragment;
        }
    }
    
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}