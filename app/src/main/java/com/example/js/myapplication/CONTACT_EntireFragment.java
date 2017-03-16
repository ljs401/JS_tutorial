package com.example.js.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by JS on 2017-03-13.
 */

public class CONTACT_EntireFragment extends CustomFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_entire_fragment, fragment_container, false);
        return rootView;
    }

    @Override
    public void setParameter(AppCompatActivity appCompatActivity, String apiName) {
        boolean encrypted = false;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("SESSION_ID", "536a4832ce358f30575f58f0fdd44cb45e91da7f");
        // parameter.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");

        super.sendAPI(appCompatActivity, apiName, encrypted, parameter);
    }
}
