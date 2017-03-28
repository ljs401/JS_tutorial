package com.example.js.myapplication;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tpay.app.common.Config;
import com.tpay.app.common.MagicSE;

import java.util.HashMap;

import static android.util.Log.d;

/**
 * Created by JS on 2017-03-14.
 */

public abstract class CustomFragment extends Fragment {
    private HashMap<String, Object> result;

    private AppCompatActivity activity;
    private String apiName;
    private boolean encrypted;
    private HashMap<String, Object> parameter;

    public abstract View onCreateView(LayoutInflater inflater, ViewGroup fragment_container, Bundle savedInstanceState);

    public abstract void setParameter(AppCompatActivity appCompatActivity, String apiName);

    public void sendAPI(AppCompatActivity appCompatActivity, String apiName, boolean encrypted, HashMap<String, Object> parameter) {
        activity = appCompatActivity;
        this.apiName = apiName;
        this.encrypted = encrypted;
        this.parameter = parameter;
        new AsyncNetworkConnection().execute();
    }

    public void showResultValues() {
        TableLayout outputLayout = (TableLayout) getActivity().findViewById(R.id.main_area_output);
        if (!result.isEmpty()) {
            d("TAG","result : "+result);
            d("TAG","getActivity() : "+getActivity());
            outputLayout.removeAllViews();
            TableRow.LayoutParams param = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            param.setMargins(1, 1, 1, 1);
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
            String code = result.get(getResources().getString(R.string.result_code)).toString();
            if (!code.equals(getResources().getString(R.string.result_value_zero))) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                builder.setMessage(Config.responseCode.get(code))
                        .setTitle(code)
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        }
    }

    class AsyncNetworkConnection extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(activity, "연결중", "잠시만 기다려주세요", false);
            TableLayout outputLayout = (TableLayout) getActivity().findViewById(R.id.main_area_output);
            outputLayout.removeAllViews();
        }

        // 처리를 하는 메소드
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                result = MagicSE.getInstance(activity).sendAPI(parameter, apiName, encrypted);
                d("TAG", result.toString());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        // 처리가 모두 끝나면 불리는 메소드
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            showResultValues();
            progressDialog.dismiss();
        }
    }
}
