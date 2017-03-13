package com.tpay.app.common;

/**
 * Created by Administrator on 2017-03-03.
 * 공통으로 관리할 소스 코드값 관리 클래스
 * 서버 아이피등 앱 버전별로 관리할 값들 정의
 *
 */

public class Config {
//    final static String serverIP="http://192.168.123.14/app/handler/";
//    final static String serverIP="https://tpay.sktelecom.com/app/handler/";
    final static String serverIP="http://61.250.22.44:8001/app/handler/";

    static String nfilterKey = null;

    public static String getNfilterKey() {
        return nfilterKey;
    }

    public static void setNfilterKey(String nfilterKey) {
        Config.nfilterKey = nfilterKey;
    }
}
