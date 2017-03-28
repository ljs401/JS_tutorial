package com.tpay.app.common;

import android.app.Fragment;
import android.app.ProgressDialog;

import com.example.js.myapplication.APP_CustomerExistFragment;
import com.example.js.myapplication.APP_CustomerJoinSearchFragment;
import com.example.js.myapplication.APP_CustomerMainSearchFragment;
import com.example.js.myapplication.APP_DeviceAppCheckFragment;
import com.example.js.myapplication.APP_MDNSearchFragment;
import com.example.js.myapplication.APP_MultiLineSearchFragment;
import com.example.js.myapplication.APP_SecurityCertificateFragment;
import com.example.js.myapplication.APP_SecuritySessionKeyFragment;
import com.example.js.myapplication.APP_SessionInitializeFragment;
import com.example.js.myapplication.APP_SubAssociationFragment;
import com.example.js.myapplication.APP_TOSSearchFragment;
import com.example.js.myapplication.CustomFragment;
import com.example.js.myapplication.OPR_EntireServiceCheckFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    final static public String stgServerURL = "http://61.250.22.44:8001/app/handler/";
    final static public String prdServerURL = "http://61.250.22.44:8001/app/handler/";
    //final static public String stgServerURL = "http://tpay.sktelecom.com:8002/app/handler/";
    //final static public String prdServerURL = "https://tpay.sktelecom.com/app/handler/";

    final static public String stgBLEServerURL = "http://61.250.23.219:8003/app/handler/";
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
        add("App-Association");
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
        add(new APP_SecuritySessionKeyFragment());
        add(new APP_SessionInitializeFragment());
        add(new APP_MultiLineSearchFragment());
        add(new APP_SubAssociationFragment());
        add(new APP_MDNSearchFragment());
        add(new APP_DeviceAppCheckFragment());
        add(new APP_CustomerExistFragment());
        add(new APP_CustomerJoinSearchFragment());
        add(new APP_TOSSearchFragment());
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

    final public static HashMap<String, String> responseCode = new HashMap<String, String>() {{
        put("0", "성공");
        put("INVALID_USER_01", "T페이 미고객");
        put("INVALID_USER_02", "SKT 미고객");
        put("INVALID_USER_03", "명의 불일치");
        put("INVALID_USER_04", "법인명의 이용불가");
        put("INVALID_USER_05", "특수개인 이용불가");
        put("INVALID_USER_06", "특수기관 이용불가");
        put("INVALID_USER_07", "공공기관 이용불가");
        put("INVALID_USER_08", "미성년자 이용불가");
        put("INVALID_USER_09", "SKT 법인명의 실사용자 미등록");
        put("INVALID_USER_10", "SKT 법인명의 정기PM 사용불가");
        put("INVALID_USER_11", "SKT 임직원 정보 미등록 ");
        put("INVALID_PHONE_01", "회선 일시정지");
        put("INVALID_PHONE_02", "선불요금제 이용불가");
        put("INVALID_PHONE_03", "이용불가 요금제");
        put("INVALID_PHONE_04", "소액결제 이용제한");
        put("INVALID_PHONE_06", "휴대폰결제 이용동의 미가입");
        put("INVALID_PHONE_07", "휴대폰결제 이용동의 가입불가");
        put("INVALID_PHONE_08", "자동결제 차단");
        put("INVALID_PHONE_09", "장기 미사용");
        put("INVALID_PHONE_10", "미납/연체");
        put("INVALID_PHONE_11", "도난/분실");
        put("INVALID_PHONE_12", "강성고객");
        put("INVALID_PHONE_13", "PG RM");
        put("INVALID_TMEMBER_01", "T멤버십 미가입");
        put("INVALID_TMEMBER_02", "T멤버십 정지");
        put("INVALID_TMEMBER_03", "T멤버십 탈퇴");
        put("INVALID_CARD_01", "미등록/위변조  카드");
        put("INVALID_CARD_02", "정지/해지 카드");
        put("INVALID_CARD_03", "카드 번호 불일치");
        put("INVALID_CARD_04", "카드 CVC 불일치");
        put("INVALID_CARD_05", "카드 유효기간 불일치");
        put("INVALID_CARD_06", "카드 비밀번호 불일치");
        put("INVALID_CARD_07", "카드 주민등록번호 불일치");
        put("INVALID_CARD_08", "고객정보/카드정보가 미등록");
        put("INVALID_CARD_09", "기 등록된 카드");
        put("INVALID_CARD_10", "카드 사용불가(기타)");
        put("INVALID_CPIN", "CPIN 불일치");
        put("INVALID_CPIN5", "CPIN 불일치 5회");
        put("INVALID_CPIN_USER", "CPIN 미가입");
        put("INVALID_XPT_PW", "결제 비밀번호 불일치");
        put("INVALID_XPT_PW1", "결제 비밀번호 불일치(1회)");
        put("INVALID_XPT_PW2", "결제 비밀번호 불일치(2회)");
        put("INVALID_XPT_PW3", "결제 비밀번호 불일치(3회)");
        put("INVALID_XPT_PW4", "결제 비밀번호 불일치(4회)");
        put("INVALID_XPT_PW5", "결제 비밀번호 불일치(5회)");
        put("INVALID_XPT_PW_MDN", "유효하지 않은 결제 비밀번호(MDN)");
        put("INVALID_XPT_PW_USER", "유효하지 않은 결제 비밀번호(생년월일)");
        put("INVALID_XPT_PW_SAME", "유효하지 않은 결제 비밀번호(동일번호 3회)");
        put("INVALID_XPT_PW_SERIES", "유효하지 않은 결제 비밀번호(연속된 번호)");
        put("INVALID_XPT_PW_PRE", "유효하지 않은 결제 비밀번호(이전번호 동일)");
        put("INVALID_XPT_PW_DIFF", "유효하지 않은 결제 비밀번호(번호 불일치)");
        put("INVALID_SESSION_KEY", "유효하지 않은 세션키");
        put("INVALID_VERIFY_DATA", "유효하지 않은 검증 데이터");
        put("INVALID_REQUEST", "비정상 요청");
        put("INVALID_PARENT_XPT", "대표회선 XPT가 아님");
        put("INVALID_PRIVATE_KEY", "유효하지 않은 개인키");
        put("INVALID_OTB", "유효하지 않은 OTB");
        put("INVALID_USIM", "사용불가 USIM");
        put("INVALID_NICKNAME", "사용불가 별명");
        put("INVALID_RECOMMEND", "유효하지 않은 추천코드");
        put("LOCK_SEND_SMS", "인증번호 발송요청 10회 초과");
        put("UPGRADE_USER", "최초 고도화 버전업 사용자");
        put("CHANGED_DEVICE", "디바이스 변경");
        put("EXPIRED_SESSION", "세션 만료");
        put("INVALID_STANDARD", "규격 오류");
        put("GENERAL_DECLINE", "시스템 오류");
        put("LINKAGE_ERROR", "연동 시스템 오류");
        put("SYSTEM_INSPECT", "시스템 점검");
        put("INVALID_REQUEST",  "비정상 요청(SMS 인증실패)");
        put("INVALID_REQUEST2", "비정상 요청(IMEI 인증실패)");
        put("INVALID_REQUEST3", "비정상 요청(UICC 인증실패)");
        put("INVALID_REQUEST4", "비정상 요청(NAG 인증실패)");
        put("INVALID_REQUEST5", "비정상 요청(위변조 인증실패)");
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
