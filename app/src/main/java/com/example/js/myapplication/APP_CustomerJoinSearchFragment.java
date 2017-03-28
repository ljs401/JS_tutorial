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

public class APP_CustomerJoinSearchFragment extends CustomFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_customer_join_search_fragment, fragment_container, false);
        return rootView;
    }

    @Override
    public void setParameter(AppCompatActivity appCompatActivity, String apiName) {
        //TODO
        //Step 1) 암호화 여부 설정
        boolean encrypted = true;

        //TODO
        //Step 2) layout에서 필드값 읽어오기
        EditText mdn = ((EditText) appCompatActivity.findViewById(R.id.mdn_editText));
        EditText name = ((EditText) appCompatActivity.findViewById(R.id.name_editText));
        EditText birthday = ((EditText) appCompatActivity.findViewById(R.id.birthday_editText));
        EditText sexCd = ((EditText) appCompatActivity.findViewById(R.id.sex_cd_editText));

        //TODO
        //Step 3) 읽어온 필드값 파라미터로 세팅
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("MDN", mdn.getText().toString());
        parameter.put("NAME", name.getText().toString());
        parameter.put("BIRTHDAY", birthday.getText().toString());
        parameter.put("SEX_CD", sexCd.getText().toString());

        super.sendAPI(appCompatActivity, apiName, encrypted, parameter);
    }
}
