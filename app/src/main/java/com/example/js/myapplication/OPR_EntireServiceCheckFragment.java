package com.example.js.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tpay.app.common.TestParameterMap;

import java.util.HashMap;

import static android.util.Log.d;

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
        d("AAA","setParameter : "+apiName);
//        boolean encrypted = true;
//        ArrayList<String> testApiName = new ArrayList<String>();
//        testApiName.add("App-MultiLineSearch");
//        testApiName.add("App-SubAssociation");
//        testApiName.add("App-MDNSearch");
//        testApiName.add("App-DeviceAppCheck");
//        testApiName.add("App-CustomerExist");
//        testApiName.add("App-CustomerJoinSearch");
//        testApiName.add("App-TOSSearch");
//        testApiName.add("App-CPINCheck");
//        testApiName.add("App-ServiceClose");
//        testApiName.add("App-OTBProvide");
//        testApiName.add("App-CustomerMainSearch");
//        testApiName.add("App-MainNoticeSearch");
//        testApiName.add("App-PushListSearch");
//        testApiName.add("App-PaymentListSearch");
//        testApiName.add("App-PaymentInfoSearch");
//        testApiName.add("App-PrePaymentAmountSearch");
//        testApiName.add("App-PWValidCheck");
//        testApiName.add("App-MDNUsimCheck");
//        testApiName.add("App-ServiceJoin");
//        testApiName.add("App-TmoneyInfoSearch");
//        testApiName.add("App-PWChange");
//        testApiName.add("App-PaymentTOSJoin");
//        testApiName.add("App-WidgetInfoSearch");
//
//        HashMap<String, Object> parameter = new HashMap<String, Object>();
//        // parameter.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");
//        for(int i =0;i<testApiName.size();i++) {
//            try {
//                HashMap<String,Object> result = MagicSE.sendAPI(Config.getCustomerMap(), testApiName.get(i), encrypted);
//                d("INFO",result.get("RESULT_CODE").toString());
//                parameter.put(testApiName.get(i)+" RESULT_CODE ",result.get("RESULT_CODE").toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        HashMap<String, Object> parameter = new TestParameterMap().getTestMap();
        if (!parameter.isEmpty()) {
            d("TAG","result : "+parameter);
            d("TAG","appCompatActivity : "+appCompatActivity);
            TableLayout outputLayout = (TableLayout) appCompatActivity.findViewById(R.id.main_area_output);
            outputLayout.removeAllViews();
            TableRow.LayoutParams param = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            param.setMargins(1,1,1,1);
            for (String key : parameter.keySet()) {
                TableRow row = new TableRow(appCompatActivity);
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                row.setBackgroundColor(Color.BLACK);
                TextView keyText = new TextView(appCompatActivity);
                keyText.setText(key + " : ");
                keyText.setBackgroundColor(Color.WHITE);
                row.addView(keyText, param);
                TextView valueText = new TextView(appCompatActivity);
                valueText.setText(parameter.get(key).toString());
                valueText.setBackgroundColor(Color.WHITE);
                row.addView(valueText, param);
                outputLayout.addView(row);
            }
        }
    }
}
