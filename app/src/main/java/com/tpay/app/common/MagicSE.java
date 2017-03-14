package com.tpay.app.common;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dreamsecurity.e2e.MagicSE2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.util.Log.d;

/**
 * Created by Administrator on 2017-03-03.
 * MagicSE2객체
 * Session 암복호화에서 사용함
 */

public class MagicSE {

    /** Called when the activity is first created. */
    final static byte[]	byRootCert = {
            (byte)0x30,(byte)0x82,(byte)0x03,(byte)0x5B,(byte)0x30,(byte)0x82,(byte)0x02,(byte)0x43,(byte)0xA0,(byte)0x03,(byte)0x02,(byte)0x01,(byte)0x02,(byte)0x02,(byte)0x01,(byte)0x01,
            (byte)0x30,(byte)0x0D,(byte)0x06,(byte)0x09,(byte)0x2A,(byte)0x86,(byte)0x48,(byte)0x86,(byte)0xF7,(byte)0x0D,(byte)0x01,(byte)0x01,(byte)0x0B,(byte)0x05,(byte)0x00,(byte)0x30,
            (byte)0x5D,(byte)0x31,(byte)0x0B,(byte)0x30,(byte)0x09,(byte)0x06,(byte)0x03,(byte)0x55,(byte)0x04,(byte)0x06,(byte)0x13,(byte)0x02,(byte)0x4B,(byte)0x52,(byte)0x31,(byte)0x16,
            (byte)0x30,(byte)0x14,(byte)0x06,(byte)0x03,(byte)0x55,(byte)0x04,(byte)0x0A,(byte)0x0C,(byte)0x0D,(byte)0x44,(byte)0x72,(byte)0x65,(byte)0x61,(byte)0x6D,(byte)0x53,(byte)0x65,
            (byte)0x63,(byte)0x75,(byte)0x72,(byte)0x69,(byte)0x74,(byte)0x79,(byte)0x31,(byte)0x12,(byte)0x30,(byte)0x10,(byte)0x06,(byte)0x03,(byte)0x55,(byte)0x04,(byte)0x0B,(byte)0x0C,
            (byte)0x09,(byte)0x4D,(byte)0x61,(byte)0x67,(byte)0x69,(byte)0x63,(byte)0x53,(byte)0x45,(byte)0x76,(byte)0x32,(byte)0x31,(byte)0x22,(byte)0x30,(byte)0x20,(byte)0x06,(byte)0x03,
            (byte)0x55,(byte)0x04,(byte)0x03,(byte)0x0C,(byte)0x19,(byte)0x44,(byte)0x72,(byte)0x65,(byte)0x61,(byte)0x6D,(byte)0x53,(byte)0x65,(byte)0x63,(byte)0x75,(byte)0x72,(byte)0x69,
            (byte)0x74,(byte)0x79,(byte)0x20,(byte)0x72,(byte)0x6F,(byte)0x6F,(byte)0x74,(byte)0x43,(byte)0x41,(byte)0x20,(byte)0x32,(byte)0x30,(byte)0x34,(byte)0x38,(byte)0x30,(byte)0x1E,
            (byte)0x17,(byte)0x0D,(byte)0x31,(byte)0x31,(byte)0x30,(byte)0x38,(byte)0x32,(byte)0x39,(byte)0x30,(byte)0x32,(byte)0x33,(byte)0x31,(byte)0x31,(byte)0x30,(byte)0x5A,(byte)0x17,
            (byte)0x0D,(byte)0x33,(byte)0x36,(byte)0x30,(byte)0x38,(byte)0x32,(byte)0x39,(byte)0x30,(byte)0x32,(byte)0x33,(byte)0x31,(byte)0x31,(byte)0x30,(byte)0x5A,(byte)0x30,(byte)0x5D,
            (byte)0x31,(byte)0x0B,(byte)0x30,(byte)0x09,(byte)0x06,(byte)0x03,(byte)0x55,(byte)0x04,(byte)0x06,(byte)0x13,(byte)0x02,(byte)0x4B,(byte)0x52,(byte)0x31,(byte)0x16,(byte)0x30,
            (byte)0x14,(byte)0x06,(byte)0x03,(byte)0x55,(byte)0x04,(byte)0x0A,(byte)0x0C,(byte)0x0D,(byte)0x44,(byte)0x72,(byte)0x65,(byte)0x61,(byte)0x6D,(byte)0x53,(byte)0x65,(byte)0x63,
            (byte)0x75,(byte)0x72,(byte)0x69,(byte)0x74,(byte)0x79,(byte)0x31,(byte)0x12,(byte)0x30,(byte)0x10,(byte)0x06,(byte)0x03,(byte)0x55,(byte)0x04,(byte)0x0B,(byte)0x0C,(byte)0x09,
            (byte)0x4D,(byte)0x61,(byte)0x67,(byte)0x69,(byte)0x63,(byte)0x53,(byte)0x45,(byte)0x76,(byte)0x32,(byte)0x31,(byte)0x22,(byte)0x30,(byte)0x20,(byte)0x06,(byte)0x03,(byte)0x55,
            (byte)0x04,(byte)0x03,(byte)0x0C,(byte)0x19,(byte)0x44,(byte)0x72,(byte)0x65,(byte)0x61,(byte)0x6D,(byte)0x53,(byte)0x65,(byte)0x63,(byte)0x75,(byte)0x72,(byte)0x69,(byte)0x74,
            (byte)0x79,(byte)0x20,(byte)0x72,(byte)0x6F,(byte)0x6F,(byte)0x74,(byte)0x43,(byte)0x41,(byte)0x20,(byte)0x32,(byte)0x30,(byte)0x34,(byte)0x38,(byte)0x30,(byte)0x82,(byte)0x01,
            (byte)0x22,(byte)0x30,(byte)0x0D,(byte)0x06,(byte)0x09,(byte)0x2A,(byte)0x86,(byte)0x48,(byte)0x86,(byte)0xF7,(byte)0x0D,(byte)0x01,(byte)0x01,(byte)0x01,(byte)0x05,(byte)0x00,
            (byte)0x03,(byte)0x82,(byte)0x01,(byte)0x0F,(byte)0x00,(byte)0x30,(byte)0x82,(byte)0x01,(byte)0x0A,(byte)0x02,(byte)0x82,(byte)0x01,(byte)0x01,(byte)0x00,(byte)0xD3,(byte)0xB7,
            (byte)0x6D,(byte)0x74,(byte)0x8F,(byte)0x09,(byte)0x63,(byte)0x3D,(byte)0xD7,(byte)0x8B,(byte)0x51,(byte)0x29,(byte)0xE1,(byte)0x16,(byte)0x8D,(byte)0x86,(byte)0x21,(byte)0x1F,
            (byte)0x13,(byte)0x95,(byte)0x7B,(byte)0x3E,(byte)0x9B,(byte)0xC1,(byte)0xE8,(byte)0xC4,(byte)0x7D,(byte)0xD1,(byte)0x64,(byte)0xE1,(byte)0x88,(byte)0x66,(byte)0x3C,(byte)0xA6,
            (byte)0x14,(byte)0xA9,(byte)0xCA,(byte)0xF4,(byte)0xE6,(byte)0x88,(byte)0xB9,(byte)0xC2,(byte)0x98,(byte)0x84,(byte)0x4D,(byte)0xD4,(byte)0xA9,(byte)0x4E,(byte)0xBD,(byte)0x07,
            (byte)0x82,(byte)0x2D,(byte)0xE4,(byte)0x3F,(byte)0x0D,(byte)0x02,(byte)0x07,(byte)0x7E,(byte)0x43,(byte)0xBB,(byte)0x7F,(byte)0x59,(byte)0xBC,(byte)0x2C,(byte)0xC0,(byte)0x6F,
            (byte)0x6F,(byte)0xF6,(byte)0x9E,(byte)0xBF,(byte)0xD9,(byte)0x57,(byte)0x22,(byte)0x38,(byte)0x6E,(byte)0x94,(byte)0x1A,(byte)0x55,(byte)0xE4,(byte)0x95,(byte)0x68,(byte)0xB6,
            (byte)0x0A,(byte)0xDF,(byte)0x60,(byte)0xA7,(byte)0xEF,(byte)0x1C,(byte)0x79,(byte)0x90,(byte)0xE0,(byte)0xDB,(byte)0x8B,(byte)0x2B,(byte)0x1E,(byte)0xC6,(byte)0x4E,(byte)0x01,
            (byte)0xC9,(byte)0xEF,(byte)0xBF,(byte)0x08,(byte)0xB4,(byte)0x74,(byte)0xDE,(byte)0xBE,(byte)0x73,(byte)0xCD,(byte)0x09,(byte)0xB3,(byte)0x2B,(byte)0x4E,(byte)0xB7,(byte)0x5C,
            (byte)0x95,(byte)0xBF,(byte)0xC1,(byte)0x58,(byte)0xFD,(byte)0x73,(byte)0xAF,(byte)0x61,(byte)0x44,(byte)0x8C,(byte)0x5B,(byte)0x42,(byte)0xBA,(byte)0x21,(byte)0x84,(byte)0x00,
            (byte)0xBC,(byte)0x77,(byte)0x42,(byte)0xF3,(byte)0xFA,(byte)0xDF,(byte)0xA1,(byte)0xD1,(byte)0xAA,(byte)0x38,(byte)0x3C,(byte)0x0F,(byte)0xC8,(byte)0x89,(byte)0xF4,(byte)0xAE,
            (byte)0x84,(byte)0xE2,(byte)0xF8,(byte)0x0C,(byte)0xC1,(byte)0x7C,(byte)0x2F,(byte)0x7C,(byte)0x43,(byte)0xFD,(byte)0x1C,(byte)0xF2,(byte)0x53,(byte)0x9D,(byte)0x6A,(byte)0xBB,
            (byte)0x0B,(byte)0xC1,(byte)0xDA,(byte)0x37,(byte)0xF0,(byte)0x36,(byte)0xBB,(byte)0x73,(byte)0x1D,(byte)0x7A,(byte)0x73,(byte)0x61,(byte)0x6C,(byte)0x95,(byte)0x4D,(byte)0xA7,
            (byte)0xF3,(byte)0xA3,(byte)0xE7,(byte)0x2E,(byte)0xEB,(byte)0x35,(byte)0x88,(byte)0x96,(byte)0xDE,(byte)0xA3,(byte)0x34,(byte)0x62,(byte)0xD4,(byte)0x8D,(byte)0x6D,(byte)0x6C,
            (byte)0x1D,(byte)0x38,(byte)0x6C,(byte)0x54,(byte)0xB0,(byte)0x5F,(byte)0xF6,(byte)0x99,(byte)0xB9,(byte)0x63,(byte)0x2E,(byte)0x15,(byte)0x3D,(byte)0xB5,(byte)0x9B,(byte)0x98,
            (byte)0xF3,(byte)0xC0,(byte)0x37,(byte)0x7D,(byte)0xFF,(byte)0x61,(byte)0x19,(byte)0x20,(byte)0xF1,(byte)0x04,(byte)0xA3,(byte)0x0A,(byte)0xA9,(byte)0x24,(byte)0xBF,(byte)0xDE,
            (byte)0x46,(byte)0xA5,(byte)0xFD,(byte)0x54,(byte)0x49,(byte)0xF7,(byte)0x1A,(byte)0x13,(byte)0xBD,(byte)0xEB,(byte)0xF9,(byte)0x97,(byte)0xD6,(byte)0x2F,(byte)0xE5,(byte)0x01,
            (byte)0x3B,(byte)0x4D,(byte)0x27,(byte)0x84,(byte)0x40,(byte)0x1F,(byte)0xB1,(byte)0xE6,(byte)0xF6,(byte)0x75,(byte)0x6A,(byte)0xCC,(byte)0x2F,(byte)0x29,(byte)0x02,(byte)0x03,
            (byte)0x01,(byte)0x00,(byte)0x01,(byte)0xA3,(byte)0x26,(byte)0x30,(byte)0x24,(byte)0x30,(byte)0x12,(byte)0x06,(byte)0x03,(byte)0x55,(byte)0x1D,(byte)0x13,(byte)0x01,(byte)0x01,
            (byte)0xFF,(byte)0x04,(byte)0x08,(byte)0x30,(byte)0x06,(byte)0x01,(byte)0x01,(byte)0xFF,(byte)0x02,(byte)0x01,(byte)0x00,(byte)0x30,(byte)0x0E,(byte)0x06,(byte)0x03,(byte)0x55,
            (byte)0x1D,(byte)0x0F,(byte)0x01,(byte)0x01,(byte)0xFF,(byte)0x04,(byte)0x04,(byte)0x03,(byte)0x02,(byte)0x02,(byte)0x04,(byte)0x30,(byte)0x0D,(byte)0x06,(byte)0x09,(byte)0x2A,
            (byte)0x86,(byte)0x48,(byte)0x86,(byte)0xF7,(byte)0x0D,(byte)0x01,(byte)0x01,(byte)0x0B,(byte)0x05,(byte)0x00,(byte)0x03,(byte)0x82,(byte)0x01,(byte)0x01,(byte)0x00,(byte)0x33,
            (byte)0xFC,(byte)0xD1,(byte)0x52,(byte)0x03,(byte)0xBB,(byte)0x09,(byte)0xA0,(byte)0xF9,(byte)0x5C,(byte)0xCD,(byte)0x97,(byte)0x03,(byte)0x21,(byte)0xD7,(byte)0xB5,(byte)0x1C,
            (byte)0x52,(byte)0xFC,(byte)0x50,(byte)0x71,(byte)0x9B,(byte)0x01,(byte)0xD4,(byte)0xDC,(byte)0x96,(byte)0xF0,(byte)0x86,(byte)0x21,(byte)0x60,(byte)0x0F,(byte)0x61,(byte)0x46,
            (byte)0x00,(byte)0x85,(byte)0x0C,(byte)0x7E,(byte)0x18,(byte)0xAF,(byte)0x51,(byte)0x3E,(byte)0x7C,(byte)0xC6,(byte)0x06,(byte)0x24,(byte)0x8F,(byte)0x60,(byte)0x0A,(byte)0x6B,
            (byte)0xC9,(byte)0x87,(byte)0x48,(byte)0x34,(byte)0x6F,(byte)0xD6,(byte)0xAF,(byte)0x1E,(byte)0xA2,(byte)0xAB,(byte)0x5D,(byte)0x7C,(byte)0xD8,(byte)0xFD,(byte)0x73,(byte)0x87,
            (byte)0x68,(byte)0xB8,(byte)0x05,(byte)0xB9,(byte)0x4F,(byte)0x19,(byte)0xF5,(byte)0x12,(byte)0x04,(byte)0xF8,(byte)0xD4,(byte)0xBA,(byte)0xAB,(byte)0xD3,(byte)0xAA,(byte)0x84,
            (byte)0xEF,(byte)0xB1,(byte)0xA5,(byte)0x17,(byte)0xE5,(byte)0xF9,(byte)0xE4,(byte)0xAF,(byte)0x7B,(byte)0x0C,(byte)0x63,(byte)0x98,(byte)0xF1,(byte)0x40,(byte)0xA5,(byte)0x9D,
            (byte)0x8A,(byte)0x24,(byte)0x73,(byte)0xA9,(byte)0x87,(byte)0xEE,(byte)0xCD,(byte)0x9C,(byte)0x12,(byte)0x22,(byte)0x59,(byte)0xC5,(byte)0xE8,(byte)0x16,(byte)0xD9,(byte)0x6C,
            (byte)0xA3,(byte)0x57,(byte)0x00,(byte)0x50,(byte)0x10,(byte)0x3F,(byte)0x7F,(byte)0x7A,(byte)0xB0,(byte)0xA2,(byte)0xE7,(byte)0x09,(byte)0xCE,(byte)0xC5,(byte)0x9A,(byte)0xD1,
            (byte)0x3F,(byte)0xF5,(byte)0x06,(byte)0x0F,(byte)0x84,(byte)0xA4,(byte)0xE5,(byte)0xC2,(byte)0xF2,(byte)0x6E,(byte)0xA4,(byte)0x2D,(byte)0x9D,(byte)0x2E,(byte)0x5A,(byte)0xE0,
            (byte)0x00,(byte)0xED,(byte)0xC2,(byte)0x4B,(byte)0x43,(byte)0x27,(byte)0xC1,(byte)0x11,(byte)0x85,(byte)0x12,(byte)0xD2,(byte)0x6E,(byte)0xAE,(byte)0xC2,(byte)0xFB,(byte)0x13,
            (byte)0x8C,(byte)0x01,(byte)0x07,(byte)0xC0,(byte)0x4A,(byte)0xCE,(byte)0x90,(byte)0x39,(byte)0x87,(byte)0x1D,(byte)0x27,(byte)0xAB,(byte)0xC3,(byte)0x53,(byte)0x69,(byte)0x4C,
            (byte)0x43,(byte)0x2C,(byte)0xE2,(byte)0x2B,(byte)0x37,(byte)0x63,(byte)0x46,(byte)0x1F,(byte)0xF1,(byte)0xE0,(byte)0x20,(byte)0x05,(byte)0x21,(byte)0xCF,(byte)0xC1,(byte)0x1F,
            (byte)0x1F,(byte)0x56,(byte)0xD4,(byte)0x60,(byte)0x73,(byte)0x09,(byte)0x77,(byte)0x5A,(byte)0x54,(byte)0xE1,(byte)0x4F,(byte)0x54,(byte)0x21,(byte)0xD4,(byte)0xD3,(byte)0x23,
            (byte)0x3D,(byte)0xEC,(byte)0x75,(byte)0x29,(byte)0x91,(byte)0xCF,(byte)0xFA,(byte)0xA0,(byte)0xD9,(byte)0x59,(byte)0x31,(byte)0x69,(byte)0xA9,(byte)0x3E,(byte)0xD6,(byte)0x99,
            (byte)0xD2,(byte)0xF4,(byte)0x7F,(byte)0x37,(byte)0x87,(byte)0x34,(byte)0x62,(byte)0x99,(byte)0x9F,(byte)0x7C,(byte)0x47,(byte)0x36,(byte)0x64,(byte)0xBE,(byte)0xB9,(byte)0x02,
            (byte)0x6C,(byte)0x10,(byte)0xD6,(byte)0xEA,(byte)0x92,(byte)0x36,(byte)0xCC,(byte)0xD6,(byte)0xE2,(byte)0x24,(byte)0xC7,(byte)0x04,(byte)0x6A,(byte)0xC5,(byte)0x59
    };
    final static int timeout = 10000;
    /*
     * 최초 호출시에 단한번만 호출하고 재사용함
     */
    static MagicSE2 instance = null;
    static MagicSE  magicse = null;
    static String jsessionId = null;
    final static String deviceType = "001";
    static boolean iniFlag = false;
    static String sessionKey = null;
    static AppCompatActivity staticView = null;
    final static int checkNum = 20;
    /**
     * 초기화 관련 부분은 synchronized 처리해서 중복 호출 방지
     */
    public static synchronized MagicSE getInstance(AppCompatActivity view) throws Exception{
        d("Info", "MagicSE : getInstance");
        staticView = view;
        String apiName = "App-SecurityCertificate?_X2_IDENTIFIER_KEY_=_JSON_MESSAGE_";
        String response = "";
        HttpURLConnection urlConnection = null;

        if (instance == null && magicse == null) {
            iniFlag = true;
            try {
                magicse = new MagicSE();
                instance = new MagicSE2(staticView.getApplicationContext());

                Utile utile = new Utile();
                URL url = new URL(Config.serverIP + apiName);
                d("Debug", Config.serverIP + apiName);
                urlConnection = (HttpURLConnection) url.openConnection();
                setHeader(urlConnection);

                OutputStream os = urlConnection.getOutputStream();

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write("TEST=true");
                bw.flush();
                bw.close();
                os.close();

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                    d("Debug", urlConnection.getHeaderFields().toString());
                    String cookieStr = urlConnection.getHeaderField("Set-Cookie");
                    String cookie = cookieStr.substring(0, cookieStr.indexOf(";"));
                    //String cookieName = cookie.substring(0, cookie.indexOf("="));
                    /**
                     * jsessionId 읽어들여서 설정(Static)
                     */
                    jsessionId = cookie.substring(cookie.indexOf("=") + 1, cookie.length());

                    Log.d("resultJson", "jsessionId : " + jsessionId);

                    while ((line = reader.readLine()) != null) {
                        d("Result : ", line + "\n");
                        response += line;
                    }

                    if (response.indexOf("SECURITY_CERTIFICATE") >= 0) {
                        urlConnection.disconnect();
                        JSONObject resultJson = new JSONObject(response);
                        d("Debug", "resultJson : " + resultJson);
                        // 서버로부터 서버 인증서를 수신한다.
                        String security_certificate = (String) resultJson.get("SECURITY_CERTIFICATE");

                        // MagicSE 버전을 가져온다.
                        String version = instance.MagicSE_GetVersion();
                        Log.d("Debug", "version : " + version);
                        // 세션키를 만든다.
                        sessionKey = instance.MagicSE_HandShakeSessionKey(security_certificate, byRootCert);
                        Log.d("Debug", "sessionKey : " + sessionKey);
                        String encSessionKey = instance.MagicSE_GetEncSessionKey(sessionKey);
                        Log.d("Debug", "encSessionKey : " + encSessionKey);

                        /**
                         * SecuritySessionKey 진행
                         */
                        apiName = "App-SecuritySessionKey";
                        url = new URL(Config.serverIP + apiName);
                        d("Debug", Config.serverIP + apiName);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        setHeader(urlConnection);
                        urlConnection.setRequestProperty("Cookie", "JSESSIONID=" + jsessionId);

                        os = urlConnection.getOutputStream();

                        bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        String enSkey = java.net.URLEncoder.encode(encSessionKey);
                        d("Debug", "enSkey : " + enSkey);
                        bw.write("SECURITY_SESSION_KEY=" + enSkey);
                        bw.flush();
                        bw.close();
                        os.close();
                        response = "";
                        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                            d("Debug", urlConnection.getHeaderFields().toString());

                            while ((line = reader.readLine()) != null) {
                                d("Debug : ", line + "\n");
                                response += line;
                            }
                            resultJson = new JSONObject(response);
                            d("Debug", "resultJson : " + resultJson);
                            /**
                             * 암복호화 진행으로 정상 세션 성공여부 판단
                             */
                            String security_init_data = resultJson.getString("SECURITY_INIT_DATA");

                            d("Debug", "SECURITY_INIT_DATA : " + security_init_data);
                            byte[] DecData = instance.MagicSE_DecData(sessionKey, security_init_data);
                            d("Debug", "DecData : " + new String(DecData));
                            String nfilter_public_key = new String(instance.MagicSE_DecData(sessionKey, resultJson.getString("NFILTER_PUBLIC_KEY")));
                            d("Debug", "nfilter_public_key : " + new String(nfilter_public_key));
                            /**
                             * Nfilter 공개키를 Config Class에 셋팅
                             */
                            Config.setNfilterKey(nfilter_public_key);
                        }
                    } else {
                        throw new Exception("SECURITY_CERTIFICATE FALSE");
                    }
                } else {
                    d("TAG", "Connection Fail");
                }
                d("TAG", "Pass All");
                iniFlag = false;
            } catch (Exception ex) {
                d("TAG", "Exception : " + ex.getMessage());
                instance = null;
                iniFlag = false;
                throw ex;
            } finally {
                iniFlag = false;
                urlConnection.disconnect();
            }
        } else {
            d("TAG", "RECALL");
        }
        iniFlag = false;
        return magicse;
    }

    /**
     * 전달 받은 JSONObject 객체를 Thread를 이용해서 API요청 함수로 전달
     * @param jObj
     * @param apiName
     * @return
     */
    public HashMap<String,Object> sendAPI(final JSONObject jObj, final String apiName,final boolean flag) throws JSONException {
        d("Info","sendAPI Type JSONObject");
        final HashMap<String,Object> map = new HashMap<String,Object>();
        new Thread(new Runnable() {
            public void run() {
                try {
                    HashMap<String,Object> obj2 = (HashMap<String, Object>) magicse.sendAPIServer(jObj, apiName, flag);
                    map.put("RESULT",obj2);
                } catch (Exception e) {
                    HashMap<String,Object> obj2 = new HashMap<String, Object>();
                    obj2.put("RESULT_CODE","LINKAGE_ERROR");
                    map.put("RESULT",obj2);
                }
            }
        }).start();
        for(int i=0;i<checkNum;i++){
            try {
                Thread.sleep(timeout/checkNum);
                if(map.containsKey("RESULT")){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!map.containsKey("RESULT")){
            HashMap<String,Object> obj2 = new HashMap<>();
            obj2.put("RESULT_CODE","LINKAGE_ERROR");
            map.put("RESULT",obj2);
        }
        return (HashMap<String, Object>) map.get("RESULT");
    }

    /**
     * JSONObject 객체를 API로 서버측에 전달
     * @param jObj
     * @param apiName
     * @return
     */
    public Map<String, Object> sendAPIServer(JSONObject jObj,String apiName,boolean flag) throws JSONException {
        d("Info","sendAPI : "+flag);
        JSONObject result = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(Config.serverIP+apiName+"?_X2_IDENTIFIER_KEY_=_JSON_MESSAGE_");
            d("Debug", Config.serverIP+apiName+"?_X2_IDENTIFIER_KEY_=_JSON_MESSAGE_");
            urlConnection = (HttpURLConnection) url.openConnection();
            setHeader(urlConnection);
            urlConnection.setRequestProperty("Cookie", "JSESSIONID="+jsessionId);
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            if(flag){
                JSONObject obj = new JSONObject();
                obj.put("ENCRYPT_DATA",instance.MagicSE_EncData( sessionKey, jObj.toString().getBytes() ));
                bw.write("_JSON_MESSAGE_="+java.net.URLEncoder.encode(obj.toString()));
            }else{
                Iterator<?> keys = jObj.keys();
                while(keys.hasNext()){
                    String key = (String) keys.next();
                    bw.write(key+"="+java.net.URLEncoder.encode(jObj.getString(key)));
                }
            }
            bw.flush();
            bw.close();
            os.close();
            StringBuffer buffer = new StringBuffer();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                d("Debug", urlConnection.getHeaderFields().toString());
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                result = new JSONObject(buffer.toString());
                if(result != null && result.get("ENCRYPT_DATA") != null){
                    byte[] dec_resultByte = instance.MagicSE_DecData( sessionKey, result.getString("ENCRYPT_DATA"));
                    result = new JSONObject(new String(dec_resultByte,"UTF-8"));
                }
                d("Result : ", result.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            d("Error",""+e.getMessage());
        }finally {
            if(urlConnection != null)urlConnection.disconnect();
        }
        return toMap(result);
    }
    /**
     * API로 전달 받은 HashMap<String,Object> 객체를  JSONObject로 전환해서 Thread를 이용 API요청 함수로 전달
     * @param paramMap
     * @param apiName
     * @return
     */
    public HashMap<String,Object> sendAPI(HashMap<String,Object> paramMap, final String apiName,final boolean flag) throws JSONException {
        d("Info","sendAPI Type Map");
        final JSONObject jObj = new JSONObject(paramMap);
        final HashMap<String,Object> map = new HashMap<String,Object>();
        new Thread(new Runnable() {
            public void run() {
                try {
                    HashMap<String,Object> obj2 = (HashMap<String, Object>) magicse.sendAPIServer(jObj, apiName, flag);
                    map.put("RESULT",obj2);
                } catch (Exception e) {
                    HashMap<String,Object> obj2 = new HashMap<String, Object>();
                    obj2.put("RESULT_CODE","LINKAGE_ERROR");
                    map.put("RESULT",obj2);
                }
            }
        }).start();
        for(int i=0;i<checkNum;i++){
            try {
                Thread.sleep(timeout/checkNum);
                if(map.containsKey("RESULT")){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!map.containsKey("RESULT")){
            HashMap<String,Object> obj2 = new HashMap<>();
            obj2.put("RESULT_CODE","LINKAGE_ERROR");
            map.put("RESULT",obj2);
        }
        return (HashMap<String, Object>) map.get("RESULT");
    }
    /**
     * 초기 Header 공통 설정값
     * @param urlConnection
     * @throws ProtocolException
     */
    public static void setHeader(HttpURLConnection urlConnection) throws ProtocolException {
        d("Info","setHeader Start");
        Utile utile = new Utile();
        String device_id = utile.getDeviceId(staticView);
        d("Debug","device_id : "+device_id);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("_X2_IDENTIFIER_KEY_", "_JSON_MESSAGE_");
        urlConnection.setRequestProperty("_DEVICE_TYPE_", deviceType);
        urlConnection.setRequestProperty("_TRANSACTION_ID_", "201703011200");
        urlConnection.setRequestProperty("_DEVICE_ID_", device_id);
        urlConnection.setConnectTimeout(timeout);
        urlConnection.setReadTimeout(timeout);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        d("Info","setHeader End");
    }

    /**
     * JSONObject를 Map<String, Object>으로 변환
     * @param object
     * @return
     * @throws JSONException
     */
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * JSONArray 를 List<Object> 로 변환
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}
