package com.example.js.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceGuideSearch extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter;
    String guideType = "";
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_guide_search);

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
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyWebClient());
        settings.setBuiltInZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setDefaultTextEncodingName("UTF-8");

        Button button = (Button) findViewById(R.id.button_ServiceGuideSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ServiceGuideSearchHTTP().execute();
            }
        });

    }

    class ServiceGuideSearchHTTP extends AsyncTask<Void, Void, String> {
        private final String uri = "http://61.250.22.44:8001/app/handler/App-ServiceGuideSearch";
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
                URL url = new URL(uri);
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
                    while ((line = reader.readLine()) != null) {
                        Log.d("Result : ", line + "\n");
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
                Log.d("TAG", "result: ERROR");
            } else {
                /*
                // Android 4.0 이하 버전
                webview.loadData(str,  "text/html", "UTF-8");
                // Android 4.1 이상 버전
                webview.loadData(str,  "text/html; charset=UTF-8", null);
                */
                webview.loadData(response, "text/html; charset=UTF-8", null);
                Log.d("TAG", "result: " + response.toString());
                // 통신 결과를 표시
            }
        }
    }

    class MyWebClient extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            final Uri uri = Uri.parse(url);
            return handleUri(uri);
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final Uri uri = request.getUrl();
            return handleUri(uri);
        }

        private boolean handleUri(final Uri uri) {
            Log.i("TAG", "Uri =" + uri);
            final String host = uri.getHost();
            final String scheme = uri.getScheme();
            // Based on some condition you need to determine if you are going to load the url
            // in your web view itself or in a browser.
            // You can use `host` or `scheme` or any part of the `uri` to decide.
            if (true) {
                // Returning false means that you are going to load this url in the webView itself
                return false;
            } else {
                // Returning true means that you need to handle what to do with the url
                // e.g. open web page in a Browser
                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }
        }
    }
}



