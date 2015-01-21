package com.seanoxford.labtob.resume.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seanoxford.labtob.resume.R;
import com.seanoxford.labtob.resume.activities.BaseActivity;
import com.seanoxford.labtob.resume.customviews.CustomChildLayout;
import com.seanoxford.labtob.resume.customviews.CustomRelativeLayout;

public class FragmentAboutMe extends Fragment {


    BaseActivity mParentActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mParentActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, null);

        CustomRelativeLayout view = (CustomRelativeLayout) v.findViewById(R.id.ll_about_me);


        CustomChildLayout ccl = new CustomChildLayout(mParentActivity, R.layout.relative_layout_contact, "dog", "#8099cc00", R.drawable.contact_photo, mParentActivity.getRobotoLight());
        CustomChildLayout ccl2 = new CustomChildLayout(mParentActivity, 0, "fish", "#90aa66cc", R.drawable.prof_photo, mParentActivity.getRobotoMedium());
        CustomChildLayout ccl3 = new CustomChildLayout(mParentActivity, 0, "sandwich", "#84ff4444", R.drawable.exp_photo, mParentActivity.getRobotoMedium());
        CustomChildLayout ccl4 = new CustomChildLayout(mParentActivity, 0, "ducks", "#8033b5e5", R.drawable.edu_photo, mParentActivity.getRobotoMedium());
        CustomChildLayout ccl5 = new CustomChildLayout(mParentActivity, 0, "pillow", "#84007700", R.drawable.edu_photo, mParentActivity.getRobotoMedium());
        CustomChildLayout ccl6 = new CustomChildLayout(mParentActivity, 0, "rhino", "#70259244", R.drawable.exp_photo, mParentActivity.getRobotoMedium());
        CustomChildLayout ccl7 = new CustomChildLayout(mParentActivity, 0, "wombat", "#70849302", R.drawable.contact_photo, mParentActivity.getRobotoMedium());
        CustomChildLayout ccl8 = new CustomChildLayout(mParentActivity, 0, "squid", "#70849302", R.drawable.exp_photo, mParentActivity.getRobotoMedium());

        view.addChildLayout(ccl);
        view.addChildLayout(ccl2);
        view.addChildLayout(ccl3);
        view.addChildLayout(ccl4);
        view.addChildLayout(ccl5);
//        view.addChildLayout(ccl6);
//        view.addChildLayout(ccl7);
//        view.addChildLayout(ccl8);


        return v;
    }
}
