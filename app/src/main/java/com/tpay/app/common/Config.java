package com.tpay.app.common;

import com.example.js.myapplication.APP_CustomerMainSearchFragment;
import com.example.js.myapplication.APP_SecurityCertificateFragment;
import com.example.js.myapplication.APP_SessionInitializeFragment;
import com.example.js.myapplication.CustomFragment;
import com.example.js.myapplication.OPR_EntireServiceCheckFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017-03-03.
 * 공통으로 관리할 소스 코드값 관리 클래스
 * 서버 아이피등 앱 버전별로 관리할 값들 정의
 */

public class Config {
    //    final static String serverIP="http://192.168.123.14/app/handler/";
//    final static String serverIP="https://tpay.sktelecom.com/app/handler/";
    static String serverIP = "http://61.250.22.44:8001/app/handler/";//추후 개발 사용 스테이징 변경 가능 예정으로 final 속성 제거

    final static public String devServerURL = "http://61.250.22.44:8001/app/handler/";
    final static public String stgServerURL = "http://tpay.sktelecom.com:8002/app/handler/";
    final static public String stgBLEServerURL = "http://61.250.23.219:8003/app/handler/";
    final static public String prdServerURL = "https://tpay.sktelecom.com/app/handler/";
    final static public String prdIOSServerURL = "http://tpay.sktelecom.com:8001/app/handler/";
    final static public String prdBLEServerURL = "https://blepay.sktelecom.com/app/handler/";

    /**
     * CustomerMainSearch 에서 받아온 값을 넣어둔다 추후 전체 API에서 사용자 정보 필요할경우 사용
     */
    static HashMap<String,Object> customerMap = null;

    static String nfilterKey = null;

    public static String getNfilterKey() {
        return nfilterKey;
    }

    public static void setNfilterKey(String nfilterKey) {
        Config.nfilterKey = nfilterKey;
    }

    final public static ArrayList<String> mdnList = new ArrayList<String>();

    final public static ArrayList<String> appApiName = new ArrayList<String>() {{
        add("App-SecurityCertificate");
        add("App-SecuritySessionKey");
        add("App-SessionInitialize");
        add("App-MultiLineSearch");
        add("App-SubAssociation");
        add("App-MDNSearch");
        add("App-DeviceAppCheck");
        add("App-CustomerExist");
        add("App-CustomerJoinSearch");
        add("App-TOSSearch");
        add("App-CPINCheck");
        add("App-Asoociation");
        add("App-ServiceClose");
        add("App-OTBProvide");
        add("App-CustomerMainSearch");
        add("App-MainNoticeSearch");
        add("App-PushListSearch");
        add("App-PaymentListSearch");
        add("App-PaymentInfoSearch");
        add("App-PrePaymentAmountSearch");
        add("App-PWValidCheck");
        add("App-MDNUsimCheck");
        add("App-ServiceJoin");
        add("App-TmoneyInfoSearch");
        add("App-PWChange");
        add("App-PaymentTOSJoin");
        add("App-WidgetInfoSearch");
    }};

    final public static ArrayList<String> appApiPath = new ArrayList<String>() {{
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
    }};

    final public static ArrayList<CustomFragment> appApiFragment = new ArrayList<CustomFragment>() {{
        add(new APP_SecurityCertificateFragment());
        add(null);
        add(new APP_SessionInitializeFragment());
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(new APP_CustomerMainSearchFragment());
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
    }};

    final public static ArrayList<String> oprApiName = new ArrayList<String>() {{
        add("배포 후 전체 서비스 점검");
        add("TEST SMS 발송");
        add("TEST MMS 발송");
        add("TEST PUSH 발송");
        add("App-SvcMgmtNumSearch");
        add("App-SimpleAuthPaymentInfoSearch");
        add("App-SimpleAuthPaymentConfirm");
        add("App-SystemInspectSearch");
    }};

    final public static ArrayList<String> oprApiPath = new ArrayList<String>() {{
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
    }};

    final public static ArrayList<CustomFragment> oprApiFragment = new ArrayList<CustomFragment>() {{
        add(new OPR_EntireServiceCheckFragment());
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
    }};

    final public static ArrayList<String> payApiName = new ArrayList<String>() {{
        add("VAN 결제");
        add("VAN 취소");
        add("POS 결제");
        add("POS 취소");
    }};
    final public static ArrayList<String> payApiPath = new ArrayList<String>() {{
        add("");
        add("");
        add("");
        add("");
    }};

    final public static ArrayList<CustomFragment> payApiFragment = new ArrayList<CustomFragment>() {{
        add(null);
        add(null);
        add(null);
        add(null);
    }};


    final public static ArrayList<String> contactApiName = new ArrayList<String>() {{
        add("전체");
        add("T페이 개발팀");
        add("CSP");
        add("TRBS");
        add("인프라");
    }};

    final public static ArrayList<String> contactApiPath = new ArrayList<String>() {{
        add("");
        add("");
        add("");
        add("");
        add("");
    }};

    final public static ArrayList<CustomFragment> contactApiFragment = new ArrayList<CustomFragment>() {{
        add(null);
        add(null);
        add(null);
        add(null);
        add(null);
    }};

    final public static String getPhoneNum(){
        return "01030118502";
    }

    public static HashMap<String, Object> getCustomerMap() {
        return customerMap;
    }

    public static void setCustomerMap(HashMap<String, Object> customerMap) {
        Config.customerMap = customerMap;
    }
}
