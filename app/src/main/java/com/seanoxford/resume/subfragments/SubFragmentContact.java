package com.seanoxford.resume.subfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.seanoxford.resume.R;
import com.seanoxford.resume.widgets.ResumeApplication;

public class SubFragmentContact extends Fragment {

    protected ResumeApplication mApp;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subfragment_contact, container, false);


        TextView address = (TextView) v.findViewById(R.id.tv_contact_address_detail);
        TextView phone = (TextView) v.findViewById(R.id.tv_contact_phone_detail);
        TextView email = (TextView) v.findViewById(R.id.tv_contact_email_detail);

        address.setTypeface(mApp.getRobotoLight());
        phone.setTypeface(mApp.getRobotoLight());
        email.setTypeface(mApp.getRobotoLight());

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ResumeApplication.getInstance();
    }
}
