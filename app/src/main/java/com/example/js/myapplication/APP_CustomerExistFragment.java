package com.example.js.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by JS on 2017-03-13.
 */

public class APP_CustomerExistFragment extends CustomFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_customer_exist_fragment, fragment_container, false);
        return rootView;
    }

    @Override
    public void setParameter(AppCompatActivity appCompatActivity, String apiName) {
        //TODO
        //Step 1) 암호화 여부 설정
        boolean encrypted = true;

        //TODO
        //Step 2) layout에서 필드값 읽어오기
        String mdn = ((EditText)appCompatActivity.findViewById(R.id.action_bar_mdn_editText)).getText().toString();

        //TODO
        //Step 3) 읽어온 필드값 파라미터로 세팅
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("MDN", mdn);

        super.sendAPI(appCompatActivity, apiName, encrypted, parameter);
    }
}
