package com.tpay.app.common;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.HashMap;

import static android.util.Log.d;

/**
 * Created by Administrator on 2017-03-04.
 */

public class NetworkThread extends Thread {

    AppCompatActivity view;
    int threadType = 1;
    JSONObject jObj = null;
    String apiName = null;
    HashMap<String,Object> paramMap = null;
    boolean flag = false;

    public NetworkThread(AppCompatActivity view){
        this.view = view;
        this.threadType = 1;
    }

    public NetworkThread(AppCompatActivity view, JSONObject jObj, String apiName, boolean flag){
        this.view = view;
        this.jObj = jObj;
        this.apiName = apiName;
        this.flag = flag;
        this.threadType = 2;
    }

    public NetworkThread(AppCompatActivity view, HashMap<String,Object> paramMap, String apiName, boolean flag){
        this.view = view;
        this.paramMap = paramMap;
        this.apiName = apiName;
        this.flag = flag;
        this.threadType = 3;
    }

    @Override
    public void run() {
        super.run();
        try{
            if(threadType == 1) {
                MagicSE.getInstance(view);
            }else if(threadType == 2) {
                MagicSE.getInstance(view).sendAPI(jObj,apiName,flag);
            }else if(threadType == 3) {
                MagicSE.getInstance(view).sendAPI(paramMap,apiName,flag);
            }else{
                d("Error","threadType : "+threadType);
            }
        }catch (Exception e){
            d("TAG","MagicSE Exception");
            e.printStackTrace();
        }
    }
}
