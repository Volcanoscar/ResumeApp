package com.seanoxford.resume.subfragments;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.activities.ActivityMain;
import com.seanoxford.resume.widgets.ResumeApplication;

public class SubFragmentContact extends Fragment {


    protected ResumeApplication mApp;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subfragment_contact, container, false);


        return v;
    }

    private void initClickListener(View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeOutAnim = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.fragment_fade_out);
                fadeOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {



                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ResumeApplication.getInstance();
    }
}
