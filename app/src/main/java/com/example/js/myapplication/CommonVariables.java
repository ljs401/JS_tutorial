package com.example.js.myapplication;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JS on 2017-03-09.
 */

public class CommonVariables {
    final static public String devServerURL = "http://61.250.22.44:8001";
    final static public String stgServerURL = "http://tpay.sktelecom.com:8002";
    final static public String stgBLEServerURL = "http://61.250.23.219:8003";
    final static public String prdServerURL = "https://tpay.sktelecom.com";
    final static public String prdIOSServerURL = "http://tpay.sktelecom.com:8001";
    final static public String prdBLEServerURL = "https://blepay.sktelecom.com";


    final static ArrayList<String> mdnList = new ArrayList<String>();

    final static ArrayList<String> appApiName = new ArrayList<String>() {{
        add("SecurityCertificate");
        add("SecuritySessionKey");
        add("SessionInitialize");
        add("MultiLineSearch");
        add("MultiLineSearch");
        add("SubAssociation");
        add("MDNSearch");
        add("DeviceAppCheck");
        add("CustomerExist");
        add("TOSSearch");
        add("CPINCheck");
        add("Asoociation");
        add("ServiceClose");
        add("OTBProvide");
        add("CustomerMainSearch");
        add("MainNoticeSearch");
        add("PushListSearch");
        add("PaymentListSearch");
        add("PaymentInfoSearch");
        add("PrePaymentAmountSearch");
        add("PWValidCheck");
        add("MDNUsimCheck");
        add("ServiceJoin");
        add("TmoneyInfoSearch");
        add("PWChange");
        add("PaymentTOSJoin");
        add("WidgetInfoSearch");
    }};

    final static ArrayList<String> appApiPath = new ArrayList<String>() {{
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

    final static ArrayList<Fragment> appApiFragment = new ArrayList<Fragment>() {{
        add(new APP_SecurityCertificateFragment());
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
        add(null);
        add(null);
    }};

    final static ArrayList<String> oprApiName = new ArrayList<String>() {{
        add("배포 후 전체 서비스 점검");
        add("TEST SMS 발송");
        add("TEST MMS 발송");
        add("TEST PUSH 발송");
        add("SvcMgmtNumSearch");
        add("SimpleAuthPaymentInfoSearch");
        add("SimpleAuthPaymentConfirm");
        add("SystemInspectSearch");
    }};

    final static ArrayList<String> oprApiPath = new ArrayList<String>() {{
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
    }};

    final static ArrayList<String> oprApiFragment = new ArrayList<String>() {{
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
    }};

    final static ArrayList<String> payApiName = new ArrayList<String>() {{
        add("VAN 결제");
        add("VAN 취소");
        add("POS 결제");
        add("POS 취소");
    }};
    final static ArrayList<String> payApiPath = new ArrayList<String>() {{
        add("");
        add("");
        add("");
        add("");
    }};

    final static ArrayList<String> contactApiName = new ArrayList<String>() {{
        add("전체");
        add("T페이 개발팀");
        add("CSP");
        add("TRBS");
        add("인프라");
    }};

    final static ArrayList<String> contactApiPath = new ArrayList<String>() {{
        add("");
        add("");
        add("");
        add("");
        add("");
    }};

/*
    final static HashMap<String, Fragment> mngApiList = new HashMap<String, Fragment>(){{
        put("배포 후 전체 서비스 점검",);
        put("TEST SMS 발송","");
        put("TEST MMS 발송","");
        put("TEST PUSH 발송","");
        put("SvcMgmtNumSearch","");
        put("SimpleAuthPaymentInfoSearch","");
        put("SimpleAuthPaymentConfirm","");
        put("SystemInspectSearch","");
    }};

    final static HashMap<String, String> payApiList = new HashMap<String, String>(){{
        put("VAN 결제","");
        put("VAN 취소","");
        put("POS 결제","");
        put("POS 취소","");
    }};

    final static HashMap<String, String> appApiList = new HashMap<String, String>(){{
        put("SecurityCertificate","");
        put("SecuritySessionKey","");
        put("SessionInitialize","");
        put("MultiLineSearch","");
        put("MultiLineSearch","");
        put("SubAssociation","");
        put("MDNSearch","");
        put("DeviceAppCheck","");
        put("CustomerExist","");
        put("TOSSearch","");
        put("CPINCheck","");
        put("Asoociation","");
        put("ServiceClose","");
        put("OTBProvide","");
        put("CustomerMainSearch","");
        put("MainNoticeSearch","");
        put("PushListSearch","");
        put("PaymentListSearch","");
        put("PaymentInfoSearch","");
        put("PrePaymentAmountSearch","");
        put("PWValidCheck","");
        put("MDNUsimCheck","");
        put("ServiceJoin","");
        put("TmoneyInfoSearch","");
        put("PWChange","");
        put("PaymentTOSJoin","");
        put("WidgetInfoSearch","");
    }};

    final static HashMap<String, String> contactApiList = new HashMap<String, String>(){{
        put("주요 운영자 연락처","");
    }};*/


}
