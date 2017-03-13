package com.example.js.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String serverURL;
    private FragmentManager fm = getFragmentManager();
    private ArrayList<String> mdnList = new ArrayList<>();
    private ArrayList<String> apiList = new ArrayList<>();
    private ArrayList<String> apiPath = new ArrayList<>();
    private ArrayAdapter<String> apiAdapter;
    private Spinner apiSpinner;
    private int isSelectedTab;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        final View mCustomView = getLayoutInflater().inflate(R.layout.actionbar_custom_view, null);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setCustomView(mCustomView);
        ((Toolbar) mCustomView.getParent()).setContentInsetsAbsolute(0, 0);
        ((Toolbar) mCustomView.getParent()).setPadding(0, 0, 0, 0);

        final Spinner serverSpinner = (Spinner) mCustomView.findViewById(R.id.action_bar_server_spinner);
        ArrayAdapter<CharSequence> serverAdapter = ArrayAdapter.createFromResource(this, R.array.ServerList, android.R.layout.simple_spinner_item);
        serverAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        serverSpinner.setAdapter(serverAdapter);
        serverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                <string-array name="ServerList">
                    <item>개발</item>
                    <item>스테이징</item>
                    <item>상용</item>
                </string-array>
                */
                switch (position) {
                    case 0:
                        serverURL = CommonVariables.devServerURL;
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                        break;
                    case 1:
                        serverURL = CommonVariables.stgServerURL;
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                        break;
                    case 2:
                        serverURL = CommonVariables.prdServerURL;
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final EditText mdnText = (EditText) mCustomView.findViewById(R.id.action_bar_mdn_editText);
        final Spinner mdnSpinner = (Spinner) mCustomView.findViewById(R.id.action_bar_mdn_spinner);
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        String phoneNum = telephonyManager.getLine1Number();
        mdnList = CommonVariables.mdnList;
        mdnList.clear();
        mdnList.add(phoneNum);
        mdnList.add("01000001234");
        mdnList.add("01000005678");
        mdnText.setText(phoneNum);
        mdnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdnSpinner.performClick();
            }
        });

        final ArrayAdapter<String> mdnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mdnList);
        mdnAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mdnSpinner.setAdapter(mdnAdapter);
        mdnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = mdnAdapter.getItem(position);
                mdnText.setText(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button resetButton = (Button) mCustomView.findViewById(R.id.action_bar_reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 리셋 버튼 클릭 => mdnText 영역의 번호로 다회선 검색 후 mdnList에 다회선 번호들 넣기*/
                mdnAdapter.notifyDataSetChanged();
            }
        });

        apiList.addAll(CommonVariables.appApiName);
        apiPath.addAll(CommonVariables.appApiPath);
        apiSpinner = (Spinner) findViewById(R.id.main_api_spinner);
        apiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, apiList);
        apiAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        apiSpinner.setAdapter(apiAdapter);
        apiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button requestButton = (Button) findViewById(R.id.main_request_button);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 1) 해당 Fragment로 교체
                 * 2) apiList에서 API명, apiPath에서 API에 대한 URL 정보 가져와 http 통신 후 결과 값 Fragment로 전달
                 */
            }
        });


        // 새로운 fragment transaciton 시작
        FragmentTransaction ft = fm.beginTransaction();
        // 기본 fragment 를 transaction 에 add
        ft.add(R.id.fragment_container, new APP_SecurityCertificateFragment());
        // transaction 을 UI 큐에 추가한다
        ft.commit();

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (isSelectedTab != viewId) {
            apiList.clear();
            apiPath.clear();
            if (viewId == R.id.main_button_1) {
                apiList.addAll(CommonVariables.appApiName);
                apiPath.addAll(CommonVariables.appApiPath);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new APP_SecurityCertificateFragment());
                ft.commit();
            } else if (viewId == R.id.main_button_2) {
                apiList.addAll(CommonVariables.payApiName);
                apiPath.addAll(CommonVariables.payApiPath);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new PAY_VanPaymentFragment());
                ft.commit();
            } else if (viewId == R.id.main_button_3) {
                apiList.addAll(CommonVariables.oprApiName);
                apiPath.addAll(CommonVariables.oprApiPath);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new OPR_EntireServiceCheckFragment());
                ft.commit();
            } else if (viewId == R.id.main_button_4) {
                apiList.addAll(CommonVariables.contactApiName);
                apiPath.addAll(CommonVariables.contactApiPath);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new CONTACT_EntireFragment());
                ft.commit();
            }
            isSelectedTab = viewId;
            apiAdapter.notifyDataSetChanged();
            apiSpinner.setSelection(0);
        }
    }
}



