package com.example.js.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by JS on 2017-03-13.
 */

public class APP_SubAssociationFragment extends CustomFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_sub_association_fragment, fragment_container, false);
        return rootView;
    }

    @Override
    public void setParameter(AppCompatActivity appCompatActivity, String apiName) {
        //TODO
        //Step 1) 암호화 여부 설정
        boolean encrypted = true;

        //TODO
        //Step 2) layout에서 필드값 읽어오기
        //임시로 layout에 서비스 관리 번호 박아 넣음. MultiLineSearch가 선행되어야 값 얻어올 수 있음
        EditText svcMgmtNum = ((EditText) appCompatActivity.findViewById(R.id.svc_mgmt_num_editText));

        //TODO
        //Step 3) 읽어온 필드값 파라미터로 세팅
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("SVC_MGMT_NUM", svcMgmtNum.getText().toString());

        super.sendAPI(appCompatActivity, apiName, encrypted, parameter);
    }
}
