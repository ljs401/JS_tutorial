package com.example.js.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nshc.nfilter.NFilter;
import com.tpay.app.common.Config;
import com.tpay.app.common.MagicSE;
import com.tpay.app.common.NetworkThread;
import com.tpay.app.common.NfilterOBJ;

import java.util.HashMap;

import static android.util.Log.d;

public class NfilterSampleActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfilter_sample);
        /**
         * 안드로이드 Activity가 바로 실행 될 때 Network으로 웹서버 등에 접속 시 android.os.NetworkOnMainThreadException이 발생
         * 해당 부분 우회하기 위해서 별도 Thread에서 Session 처리시작함
         */
        try{
            NetworkThread thread = new NetworkThread(this);
            thread.start();
        }catch (Exception e){
            d("tag","Exception : "+e.getMessage()+"");
        }

        final AppCompatActivity appCompatActivity = this;
        Button button2 = (Button) findViewById(R.id.btnViewNative);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HashMap<String,Object> map = new HashMap<String,Object>();
                    map.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");
                    String apiName = "App-SessionInitialize";
                    HashMap<String,Object>  temmpp = MagicSE.getInstance(appCompatActivity).sendAPI(map,apiName,true);
                    d("TAG",temmpp.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button button3 = (Button) findViewById(R.id.Nfilter);
        final Activity a = this;
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NfilterOBJ nfilter = NfilterOBJ.getInstance(a, Config.getNfilterKey());
                nfilter.nFilterload(findViewById(R.id.activity_main),R.id.textView, NFilter.KEYPADNUM,200);
            }
        });
    }
}



