package com.example.js.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nshc.nfilter.NFilter;
import com.tpay.app.common.Config;
import com.tpay.app.common.MagicSE;
import com.tpay.app.common.NetworkThread;
import com.tpay.app.common.NfilterOBJ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity{
    ArrayAdapter<CharSequence> adapter;
    String guideType = "";
    String apiName= "App-ServiceGuideSearch";
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        Spinner spinner = (Spinner) findViewById(R.id.spinner_ServiceGuideSearch);
        adapter = ArrayAdapter.createFromResource(this, R.array.ServiceGuideSearch, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                <item>11 : 서비스소개</item>
                <item>21 : 결제안내-바코드</item>
                <item>22 : 결제안내-핸즈프리</item>
                <item>23 : 결제안내-온라인</item>
                <item>31 : 결제방법</item>
                */
                switch (position) {
                    case 0:
                        guideType = "11";
                        break;
                    case 1:
                        guideType = "21";
                        break;
                    case 2:
                        guideType = "22";
                        break;
                    case 3:
                        guideType = "23";
                        break;
                    case 4:
                        guideType = "31";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        webview = (WebView) findViewById(R.id.webView_ServiceGuideSearch);
        webview.setWebViewClient(new MyWebClient());
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        final AppCompatActivity appCompatActivity = this;
        Button button = (Button) findViewById(R.id.button_ServiceGuideSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ServiceGuideSearchHTTP().execute();
            }
        });

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

    class ServiceGuideSearchHTTP extends AsyncTask<Void, Void, String> {
        private String uri = "http://61.250.22.44:8001/app/handler/";
        private String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 처리를 하는 메소드
        @Override
        protected String doInBackground(Void... params) {
            final HttpURLConnection urlConnection;
            try {
                URL url = new URL(uri+apiName);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("_X2_IDENTIFIER_KEY_", "_JSON_MESSAGE_");
                urlConnection.setRequestProperty("_DEVICE_TYPE_", "001");
                urlConnection.setRequestProperty("_TRANSACTION_ID_", "201703011200");
                urlConnection.setRequestProperty("_DEVICE_ID_", "357144072326528");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write("GUIDE_TYPE" + "=" + guideType);
                bw.flush();
                bw.close();
                os.close();

            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
            try {
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    d("TAG",urlConnection.getHeaderFields().toString());
                    while ((line = reader.readLine()) != null) {
                        d("Result : ", line + "\n");
                        response += line;
                    }
                }
            } catch (IOException e) {
                return null;
            } finally {
                urlConnection.disconnect();
            }
            if (TextUtils.isEmpty(response)) {
                return null;
            }
            return response;
        }

        // 처리가 모두 끝나면 불리는 메소드
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            // 통신 실패로 처리
            if (response == null) {
                d("TAG", "result: ERROR");
            } else {
                /*
                // Android 4.0 이하 버전
                webview.loadData(str,  "text/html", "UTF-8");
                // Android 4.1 이상 버전
                webview.loadData(str,  "text/html; charset=UTF-8", null);
                */

                webview.loadData(response, "text/html; charset=UTF-8", null);
                d("TAG", "result: " + response.toString());
                // 통신 결과를 표시
            }
        }
    }

    class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
}



