package com.example.js.myapplication;

import com.tpay.app.common.Config;
import com.tpay.app.common.MagicSE;
import com.tpay.app.common.NetworkThread;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String serverURL;
    private FragmentManager fm = getFragmentManager();
    private ArrayList<String> mdnList = new ArrayList<>();
    private ArrayList<String> apiList = new ArrayList<>();
    private ArrayList<String> apiPath = new ArrayList<>();
    private ArrayList<CustomFragment> apiFragment = new ArrayList<>();
    private ArrayAdapter<String> apiAdapter;
    private Spinner apiSpinner;
    private int isSelected;
    private String apiName = "";
    private CustomFragment currentFragment;
    final AppCompatActivity appCompatActivity = this;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            NetworkThread thread = new NetworkThread(this);
            thread.start();
        } catch (Exception e) {
            d("tag", "Exception : " + e.getMessage() + "");
        }

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
                        serverURL = Config.devServerURL;
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                        break;
                    case 1:
                        serverURL = Config.stgServerURL;
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                        break;
                    case 2:
                        serverURL = Config.prdServerURL;
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
        mdnList = Config.mdnList;
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
                try {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("SESSION_ID", "536a4832ce358f30575f58f0fdd44cb45e91da7f");
                    // map.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");
                    HashMap<String, Object> resultMap = MagicSE.getInstance(appCompatActivity).sendAPI(map, "App-MultiLineSearch", true);

                    d("TAG", resultMap.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mdnAdapter.notifyDataSetChanged();
            }
        });

        apiList.addAll(Config.appApiName);
        apiPath.addAll(Config.appApiPath);
        apiFragment.addAll(Config.appApiFragment);
        apiSpinner = (Spinner) findViewById(R.id.main_api_spinner);
        apiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, apiList);
        apiAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        apiSpinner.setAdapter(apiAdapter);
        apiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                apiName = apiList.get(position);
                if ((currentFragment = apiFragment.get(position)) != null) {
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, currentFragment);
                    ft.commit();
                } else {
                    Toast.makeText(getApplicationContext(), "API Fragment 미구현", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button requestButton = (Button) findViewById(R.id.main_request_button);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment.setParameter(appCompatActivity, apiName);
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
        if (isSelected != viewId) {
            apiList.clear();
            apiPath.clear();
            apiFragment.clear();
            if (viewId == R.id.main_button_1) {
                apiList.addAll(Config.appApiName);
                apiPath.addAll(Config.appApiPath);
                apiFragment.addAll(Config.appApiFragment);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new APP_SecurityCertificateFragment());
                ft.commit();
            } else if (viewId == R.id.main_button_2) {
                apiList.addAll(Config.payApiName);
                apiPath.addAll(Config.payApiPath);
                apiFragment.addAll(Config.payApiFragment);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new PAY_VanPaymentFragment());
                ft.commit();
            } else if (viewId == R.id.main_button_3) {
                apiList.addAll(Config.oprApiName);
                apiPath.addAll(Config.oprApiPath);
                apiFragment.addAll(Config.oprApiFragment);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new OPR_EntireServiceCheckFragment());
                ft.commit();
            } else if (viewId == R.id.main_button_4) {
                apiList.addAll(Config.contactApiName);
                apiPath.addAll(Config.contactApiPath);
                apiFragment.addAll(Config.contactApiFragment);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, new CONTACT_EntireFragment());
                ft.commit();
            }
            isSelected = viewId;
            apiAdapter.notifyDataSetChanged();
            apiSpinner.setSelection(0);
        }
    }
}



