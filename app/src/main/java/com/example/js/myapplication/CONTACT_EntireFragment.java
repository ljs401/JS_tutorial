package com.example.js.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JS on 2017-03-13.
 */

public class CONTACT_EntireFragment extends Fragment {
    private Activity thisActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        thisActivity = getActivity();
        View rootView = inflater.inflate(R.layout.contact_entire_fragment, fragment_container, false);
        return rootView;
    }
}
