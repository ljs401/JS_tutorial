package com.tpay.app.common;

import static android.util.Log.d;

/**
 * Created by Administrator on 2017-03-14.
 */

public class LogUtile {

    /*******************************************************************
     * Log 처리 레벨
     * 0 : 로그 생성하지 않음
     * 1 : Info
     * 2 : Debug
     ******************************************************************/
    static int logLevel = 1;



    public static void logDebug(Class cls, String str){
        if(logLevel >= 2){
            d("Debug",cls.getName()+": "+str);
        }
    }

    public static void logInfo(Class cls, String str){
        if(logLevel >= 1){
            d("Info",cls.getName()+": "+str);
        }
    }
}
