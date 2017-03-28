package com.example.js.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tpay.app.common.MagicSE;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static android.util.Log.d;

/**
 * Created by JS on 2017-03-13.
 */

public class APP_CustomerMainSearchFragment extends CustomFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_customer_main_search_fragment, fragment_container, false);
        return rootView;
    }

    @Override
    public void setParameter(AppCompatActivity appCompatActivity, String apiName) {
        //TODO
        //Step 1) 암호화 여부 설정
        boolean encrypted = false;

        //TODO
        //Step 2) layout에서 필드값 읽어오기
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        DatePicker eventDatePicker = (DatePicker) appCompatActivity.findViewById(R.id.event_last_time_datePicker);
        calendar.set(eventDatePicker.getYear(), eventDatePicker.getMonth(), eventDatePicker.getDayOfMonth());
        String eventLastTime = dateFormat.format(calendar.getTime()) + "000000";

        DatePicker noticeDatePicker = (DatePicker) appCompatActivity.findViewById(R.id.notice_last_time_datePicker);
        calendar.set(noticeDatePicker.getYear(), noticeDatePicker.getMonth(), noticeDatePicker.getDayOfMonth());
        String noticeLastTime = dateFormat.format(calendar.getTime()) + "000000";

        //TODO
        //Step 3) 읽어온 필드값 파라미터로 세팅
        HashMap<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("SESSION_ID", "536a4832ce358f30575f58f0fdd44cb45e91da7f");
        parameter.put("EVENT_LAST_TIME", eventLastTime);
        parameter.put("NOTICE_LAST_TIME", noticeLastTime);
        // parameter.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");

        super.sendAPI(appCompatActivity, apiName, encrypted, parameter);
    }
}
