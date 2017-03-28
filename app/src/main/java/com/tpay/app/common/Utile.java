package com.tpay.app.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import java.util.Calendar;

import static android.util.Log.d;
import static android.util.Log.e;

/**
 * Created by Administrator on 2017-03-03.
 * Device ID등 폰 공통적으로 호출하는 부분
 */

public class Utile {

    static int idx = 0;

    public String getDeviceId(Context mContext) {
        int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);

        d(this.getClass().getName(),"permissionCheck : "+permissionCheck);
        d(this.getClass().getName(),"PackageManager.PERMISSION_GRANTED : "+PackageManager.PERMISSION_GRANTED);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.READ_PHONE_STATE}, Manifest.permission.REQUEST_READ_PHONE_STATE);
        } else {
            final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        }
        e(this.getClass().getName(),"getDeviceId Fail");
        return "123456790";
    }

     public synchronized String getIdx(){
        idx = idx+1;
         if(idx >= 10){
             idx = 0;
         }
         if(idx<10){
             return "0"+idx;
         }else{
             return idx+"";
         }
    }

    public String getTransactionId() {
        Calendar ca = Calendar.getInstance();
        String year = ca.get(Calendar.YEAR)+"";
        String month = (ca.get(Calendar.MONTH) > 10) ? (ca.get(Calendar.MONTH)+1)+"" : "0"+(ca.get(Calendar.MONTH)+1);
        String day = (ca.get(Calendar.DAY_OF_MONTH) > 10) ? ca.get(Calendar.DAY_OF_MONTH)+"" : "0"+ca.get(Calendar.DAY_OF_MONTH);
        String hour = (ca.get(Calendar.HOUR_OF_DAY) > 10) ? ca.get(Calendar.HOUR_OF_DAY)+"" : "0"+ca.get(Calendar.HOUR_OF_DAY);
        String min = (ca.get(Calendar.MINUTE) > 10) ? ca.get(Calendar.MINUTE)+"" : "0"+ca.get(Calendar.MINUTE);
        String sec = (ca.get(Calendar.SECOND) > 10) ? ca.get(Calendar.SECOND)+"" : "0"+ca.get(Calendar.SECOND);
        d(this.getClass().getName(),year+month+day+hour+min+sec+getIdx());
        return year+month+day+hour+min+sec+getIdx();
    }
}
