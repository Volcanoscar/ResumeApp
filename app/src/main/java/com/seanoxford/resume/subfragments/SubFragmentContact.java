package com.seanoxford.resume.subfragments;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.activities.ActivityMain;
import com.seanoxford.resume.widgets.ResumeApplication;

public class SubFragmentContact extends Fragment {


    protected ResumeApplication mApp;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subfragment_contact, container, false);

//        TextView tvContact
//        TextView tvContactDetail
//        TextView tvPhone
//        TextView tvPhoneDetail




        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ResumeApplication.getInstance();




    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
