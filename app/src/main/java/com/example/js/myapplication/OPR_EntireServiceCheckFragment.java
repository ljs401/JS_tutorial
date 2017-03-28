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

public class OPR_EntireServiceCheckFragment extends CustomFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.opr_entire_service_check_fragment, fragment_container, false);
        return rootView;
    }

    @Override
    public void setParameter(AppCompatActivity appCompatActivity, String apiName) {
        //TODO
        //Step 1) 암호화 여부 설정
        boolean encrypted = false;

        //TODO
        //Step 2) layout에서 필드값 읽어오기

        //TODO
        //Step 3) 읽어온 필드값 파라미터로 세팅
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("SESSION_ID", "536a4832ce358f30575f58f0fdd44cb45e91da7f");
        // parameter.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");

        super.sendAPI(appCompatActivity, apiName, encrypted, parameter);
    }
}
