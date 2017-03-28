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

public class APP_DeviceAppCheckFragment extends CustomFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_device_app_check_fragment, fragment_container, false);
        return rootView;
    }

    @Override
    public void setParameter(AppCompatActivity appCompatActivity, String apiName) {
        //TODO
        //Step 1) 암호화 여부 설정
        boolean encrypted = true;

        //TODO
        //Step 2) layout에서 필드값 읽어오기
        EditText deviceType = ((EditText) appCompatActivity.findViewById(R.id.device_type_editText));
        EditText deviceId = ((EditText) appCompatActivity.findViewById(R.id.device_id_editText));
        EditText appVersion = ((EditText) appCompatActivity.findViewById(R.id.app_version_editText));

        //TODO
        //Step 3) 읽어온 필드값 파라미터로 세팅
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("DEVICE_TYPE", deviceType.getText().toString());
        parameter.put("DEVICE_ID", deviceId.getText().toString());
        parameter.put("APP_VERSION", appVersion.getText().toString());

        super.sendAPI(appCompatActivity, apiName, encrypted, parameter);
    }
}
