package com.seanoxford.labtob.resume.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seanoxford.labtob.resume.R;

/**
 * Created by labtob on 12/6/2014.
 */
public class FragmentGuestBook extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);



        return view;
    }
}
