package com.seanoxford.resume.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seanoxford.resume.R;
import com.seanoxford.resume.activities.BaseActivity;
import com.seanoxford.resume.customviews.CustomChildLayout;
import com.seanoxford.resume.customviews.CustomRelativeLayout;

public class FragmentAboutMe extends Fragment {

    protected BaseActivity mParentActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mParentActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, null);

        FragmentManager childFragmentManager = getChildFragmentManager();
        CustomRelativeLayout view = (CustomRelativeLayout) v.findViewById(R.id.ll_about_me);

        CustomChildLayout ccl = new CustomChildLayout(mParentActivity, "Contact", "#8099cc00", R.drawable.contact_photo, mParentActivity.getRobotoLight(), childFragmentManager);
        CustomChildLayout ccl2 = new CustomChildLayout(mParentActivity, "Proficiencies", "#90aa66cc", R.drawable.prof_photo, mParentActivity.getRobotoLight(), childFragmentManager);
        CustomChildLayout ccl3 = new CustomChildLayout(mParentActivity, "Experience", "#84ff4444", R.drawable.exp_photo, mParentActivity.getRobotoLight(), childFragmentManager);
        CustomChildLayout ccl4 = new CustomChildLayout(mParentActivity, "Education", "#8033b5e5", R.drawable.edu_photo, mParentActivity.getRobotoLight(), childFragmentManager);
        CustomChildLayout ccl5 = new CustomChildLayout(mParentActivity, "pillow", "#84007700", R.drawable.edu_photo, mParentActivity.getRobotoMedium(), childFragmentManager);
        CustomChildLayout ccl6 = new CustomChildLayout(mParentActivity, "rhino", "#70259244", R.drawable.exp_photo, mParentActivity.getRobotoMedium(), childFragmentManager);
        CustomChildLayout ccl7 = new CustomChildLayout(mParentActivity, "wombat", "#70849302", R.drawable.contact_photo, mParentActivity.getRobotoMedium(), childFragmentManager);
        CustomChildLayout ccl8 = new CustomChildLayout(mParentActivity, "squid", "#70849302", R.drawable.exp_photo, mParentActivity.getRobotoMedium(), childFragmentManager);

        ccl4.alignImageBottomOnResize(true);

        view.addChildLayout(ccl);
        view.addChildLayout(ccl2);
        view.addChildLayout(ccl3);
//        view.addChildLayout(ccl4);
//        view.addChildLayout(ccl5);
//        view.addChildLayout(ccl6);
//        view.addChildLayout(ccl7);
//        view.addChildLayout(ccl8);


        return v;
    }
}
