package com.example.js.myapplication;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tpay.app.common.MagicSE;

import java.util.HashMap;

import static android.util.Log.d;

/**
 * Created by JS on 2017-03-14.
 */

public abstract class CustomFragment extends Fragment {
    private HashMap<String, Object> result;

    private AppCompatActivity activity;

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState);

    public abstract void setParameter(AppCompatActivity appCompatActivity, String apiName);

    public void sendAPI(AppCompatActivity appCompatActivity, String apiName, boolean encrypted, HashMap<String, Object> parameter){
        activity = appCompatActivity;
        try {
            result = MagicSE.sendAPI(parameter, apiName, encrypted);
            d("TAG", result.toString());
            showResultValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showResultValues() {
        if (!result.isEmpty()) {
            d("TAG","result : "+result);
            d("TAG","getActivity() : "+getActivity());
            TableLayout outputLayout = (TableLayout) getActivity().findViewById(R.id.main_area_output);
            outputLayout.removeAllViews();
            TableRow.LayoutParams param = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            param.setMargins(1,1,1,1);
            for (String key : result.keySet()) {
                TableRow row = new TableRow(activity);
                row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                row.setBackgroundColor(Color.BLACK);
                TextView keyText = new TextView(activity);
                keyText.setText(key + " : ");
                keyText.setBackgroundColor(Color.WHITE);
                row.addView(keyText, param);
                TextView valueText = new TextView(activity);
                valueText.setText(result.get(key).toString());
                valueText.setBackgroundColor(Color.WHITE);
                row.addView(valueText, param);
                outputLayout.addView(row);
            }
        }
    }
}
