package com.example.js.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tpay.app.common.Config;
import com.tpay.app.common.MagicSE;
import com.tpay.app.common.NetworkThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private BackPressCloseHandler backPressCloseHandler;

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

        String phoneNum = getIntent().getStringExtra(getResources().getString(R.string.mdn));
        if (phoneNum == null) {
            phoneNum = "01030118502";
        }
        mdnList = Config.mdnList;
        mdnList.clear();
        mdnList.add(phoneNum);
        mdnText.setText(phoneNum);
        mdnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d("TAG", "setOnClickListener");
                mdnSpinner.performClick();
            }
        });

//        final ArrayAdapter<String> mdnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mdnList);
//        mdnAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        mdnSpinner.setAdapter(mdnAdapter);
//        mdnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                d("TAG","onItemSelected");
////                String text = mdnAdapter.getItem(position);
////                d("TAG","onItemSelected : "+text);
////                mdnText.setText(text);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        Button resetButton = (Button) mCustomView.findViewById(R.id.action_bar_reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d("TAG", "리셋 버튼 클릭");
                /* * 리셋 버튼 클릭 => mdnText 영역의 번호로 다회선 검색 후 mdnList에 다회선 번호들 넣기*/
                try {
                    /**
                     * Step1
                     * mdnText 입력값을 이용해서
                     * MagicSE세션 초기화한후 MultiLineSearch API를 이용해서 MDN 정보를 받아온다
                     */
                    String mdn = mdnText.getText().toString();
                    //TODO Sesson ID 받아오는 API 신규로 만들어서 설정 필요함(보안상 문제나 이슈 발생할경우 사용자 세션 받아 오는 부분으로 대체)
                    HashMap<String, Object> sessionMap = new HashMap<String, Object>();
                    sessionMap.put("MDN", mdn);
                    d("TAG", "sessionMap : " + sessionMap);
                    HashMap<String, Object> sessionResultMap = MagicSE.sendAPI(sessionMap, "App-GetSessionId", true);
                    d("TAG", sessionResultMap.toString());
                    if (!"0".equalsIgnoreCase((String) sessionResultMap.get("RESULT_CODE"))) {
                        return;
                    }
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("SESSION_ID", sessionResultMap.get("SESSION_ID").toString());
                    //map.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");
                    HashMap<String, Object> resultMap = MagicSE.sendAPI(map, "App-SessionInitialize", true);
                    d("TAG", resultMap.toString());
                    if (!"0".equalsIgnoreCase((String) resultMap.get("RESULT_CODE"))) {
                        return;
                    }
                    resultMap = MagicSE.sendAPI(map, "App-MultiLineSearch", true);
                    d("TAG", resultMap.toString());
                    if (!"0".equalsIgnoreCase((String) resultMap.get("RESULT_CODE"))) {
                        return;
                    }
                    final List<String> svNumList = new ArrayList();
                    List resultMdnList = (List) resultMap.get("MDN_LIST");
                    if (resultMdnList != null) {
                        mdnList.clear();
                        svNumList.clear();
                        for (int i = 0; i < resultMdnList.size(); i++) {
                            Map<String, Object> dataMap = (Map<String, Object>) resultMdnList.get(i);
                            mdnList.add(dataMap.get("MDN").toString());
                            svNumList.add(dataMap.get("SVC_MGMT_NUM").toString());
                        }
//                        mdnList.add("01086231697");
//                        mdnList.add("01020345018");
//                        mdnList.add("01092809106");
//                        mdnList.add("01038324035");
                    } else {
                        mdnList.clear();
                        mdnList.add("MDN정보가 존재 하지 않습니다.");
                    }
//                    if(resultMdnList != null ){
//                        if(resultMdnList.size() == 1){
//
//                        }
//                    }
                    d("TAG", "mdnList : " + mdnList.toString());
                    /**
                     * mdnList를 재 정의 해서 사용할경우
                     * mdnSpinner.setOnItemSelectedListener를 신규로 재정의 하지 않으면 Android에서 정상적으로 인식하지 않음
                     * 추후 Android 개발자 오면 해당 방식 확인 필요
                     */
                    final ArrayAdapter<String> mdnAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mdnList);
                    mdnAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    mdnSpinner.setAdapter(mdnAdapter);
                    /**
                     * 선택된 사용자 정보로 Session 정보 조회후 SessionInitialize실행 응답 받은 정보를 Config customerMapd에 저장
                     */
                    mdnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            d("TAG", "onItemSelected");
                            String text = mdnAdapter.getItem(position);
                            String svg_mgmt_num = svNumList.get(position);
                            d("TAG", "onItemSelected --> MDN : " + text + ", svg_mgnt_num : " + svg_mgmt_num);
                            try {
                                //Step 1 선택된 MDN 으로 사용자 Session ID 정보를 조회
                                HashMap<String, Object> sessionMap = new HashMap<String, Object>();
                                mdnText.setText(text);
                                sessionMap.put("MDN", text);
                                d("TAG", "sessionMap : " + sessionMap);
                                HashMap<String, Object> sessionResultMap = MagicSE.sendAPI(sessionMap, "App-GetSessionId", true);
                                d("TAG", sessionResultMap.toString());
                                if (!"0".equalsIgnoreCase((String) sessionResultMap.get("RESULT_CODE"))) {
                                    return;
                                }
                                //Step 2 조회한 사용자 Session ID를 이용 세션 초기화
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("SESSION_ID", sessionResultMap.get("SESSION_ID"));
                                HashMap<String, Object> resultMap = MagicSE.sendAPI(map, "App-SessionInitialize", true);
                                if (!"0".equalsIgnoreCase((String) resultMap.get("RESULT_CODE"))) {
                                    return;
                                }
                                //Step 2 세션 초기화 후 CustomerMainSearch를 이용 사용자 정보를 받아서 Config CustomerMap 객체에 저장
                                resultMap = MagicSE.sendAPI(map, "App-CustomerMainSearch", true);
                                if (!"0".equalsIgnoreCase((String) resultMap.get("RESULT_CODE"))) {
                                    return;
                                }
                                resultMap.put("MDN", text);
                                resultMap.put("SESSION_ID", sessionResultMap.get("SESSION_ID"));
                                resultMap.put("SVC_MGMT_NUM", svg_mgmt_num);
                                d("TAG", resultMap.toString());

                                Config.setCustomerMap(resultMap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    d("ERROR", "" + e.getMessage());
                }
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
                if (currentFragment != null) {
                    currentFragment.setParameter(appCompatActivity, apiName);
                }
            }
        });

        // 새로운 fragment transaciton 시작
        FragmentTransaction ft = fm.beginTransaction();
        // 기본 fragment 를 transaction 에 add
        ft.add(R.id.fragment_container, new APP_SecurityCertificateFragment());
        // transaction 을 UI 큐에 추가한다
        ft.commit();

        backPressCloseHandler = new BackPressCloseHandler();
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
            currentFragment = apiFragment.get(0);
            isSelected = viewId;
            apiAdapter.notifyDataSetChanged();
            apiSpinner.setSelection(0);
        }
    }


    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    public class BackPressCloseHandler {
        private long backKeyPressedTime = 0;
        private Toast toast;

        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                MainActivity.this.finish();
                toast.cancel();
            }
        }

        public void showGuide() {
            toast = Toast.makeText(MainActivity.this, "뒤로 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
