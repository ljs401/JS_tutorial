package com.example.js.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tpay.app.common.MagicSE;

import java.util.HashMap;

import static android.util.Log.d;

/**
 * Created by JS on 2017-03-13.
 */

public class APP_SessionInitializeFragment extends CustomFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_session_initialize_fragment, fragment_container, false);
        return rootView;
    }

    @Override
    public void setParameter(AppCompatActivity appCompatActivity, String apiName) {
        boolean encrypted = true;
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("SESSION_ID", "536a4832ce358f30575f58f0fdd44cb45e91da7f");
        // parameter.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");

        super.sendAPI(appCompatActivity, apiName, encrypted, parameter);
    }
}
