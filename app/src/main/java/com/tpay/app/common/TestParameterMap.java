package com.tpay.app.common;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017-03-23.
 */

public class TestParameterMap {
    final HashMap<String,Object> testMap = new HashMap<String,Object>();
    final ArrayList<String> testApiName = new ArrayList<String>();
    String noParameterApi = "App-MultiLineSearch,App-TmoneyInfoSearch,App-PushListSearch,App-MDNSearch,App-PaymentInfoSearch,App-MainNoticeSearch,App-PaymentInfoSearch,App-CustomerMainSearch,App-SubAssociation,App-MDNUsimCheck,App-PrePaymentAmountSearch,App-NoticeListSearch,App-QNAListSearch,App-TmoneyCPURLSearch,App-SvcMgmtNumSearch";
    String noMagicSEApi = "";
    final ArrayList<Boolean> returnCheckList = new ArrayList<Boolean>();

    public HashMap<String,Object> getTestMap(){
        setApi();
        for(int i=0;i<testApiName.size();i++){
            returnCheckList.add(new Boolean(false));
            setApiValue(testApiName.get(i),i);
        }
//        int checkIdx = 0;
//        while(true) {
//            checkIdx++;
//            boolean checkFlag = true;
//            for (int i = 0; i < returnCheckList.size(); i++) {
//                if (!returnCheckList.get(i)) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    checkFlag = false;
//                    break;
//                }
//                d("Tag","checkIdx"+checkIdx+" ==> returnCheckList["+i+"] : "+returnCheckList.get(i));
//            }
//            if(checkFlag || checkIdx>10){
//                break;
//            }
//        }
        return testMap;
    }

    /**
     * 테스트할 API명칭을 LIST안에 담는다
     */
    public void setApi(){
        testApiName.add("App-MultiLineSearch");
        testApiName.add("App-SubAssociation");
        testApiName.add("App-MDNSearch");
//        testApiName.add("App-DeviceAppCheck");--App정보 강제 셋팅 문제 발생으로 제외
        testApiName.add("App-CustomerExist");
        testApiName.add("App-CustomerJoinSearch");
        testApiName.add("App-TOSSearch");
//        testApiName.add("App-CPINCheck"); --  대상자 문자 발송으로 제외
//        testApiName.add("App-ServiceClose"); --탈퇴처리 제외
        testApiName.add("App-OTBProvide");
        testApiName.add("App-CustomerMainSearch");
        testApiName.add("App-MainNoticeSearch");
        testApiName.add("App-PushListSearch");
        testApiName.add("App-PaymentListSearch");
        testApiName.add("App-PaymentInfoSearch");
        testApiName.add("App-PrePaymentAmountSearch");
        testApiName.add("App-PWValidCheck");
        testApiName.add("App-MDNUsimCheck");
//        testApiName.add("App-ServiceJoin"); -- 임의 서비스 가입 문제 발생 높아서 제외
        testApiName.add("App-TmoneyInfoSearch");
//        testApiName.add("App-PWChange"); -- 비밀번호 강제 업데이트시 사용자 정보 문제 발생으로 제외
//        testApiName.add("App-PaymentTOSJoin");--테스트중 부과서비스 가입 확률 높아서 보류
        testApiName.add("App-WidgetInfoSearch");
        testApiName.add("App-CPListSearch");
        testApiName.add("App-CorpListSearch");
        testApiName.add("App-EventURLSearch");
        testApiName.add("App-NoticeListSearch");
        testApiName.add("App-FAQURLSearch");
        testApiName.add("App-QNAListSearch");
        testApiName.add("App-TmoneyCPURLSearch");
        testApiName.add("App-SvcMgmtNumSearch");
        testApiName.add("App-CommonMessage");
        testApiName.add("App-CommonResource");
        testApiName.add("App-SystemInspectSearch");
    }

    /**
     * API 명을 받아서 해당 API에 기본값 이외의 값이 필요할경우 추가 정보 셋팅
     * @param apiName
     */
    public void setApiValue(String apiName,int idx){
        HashMap<String,Object> tempMap = Config.getCustomerMap();
        if("App-WidgetInfoSearch".indexOf(apiName) >= 0){
            tempMap.put("PAYMENT_CODE","000040");
            tempMap.put("APP_TYPE","2");
            tempMap.put("AUTH_TYPE","0");
        }else if("App-PaymentListSearch".indexOf(apiName) >= 0){
            tempMap.put("PAYMENT_KIND","10");
            tempMap.put("SEARCH_DATE","201703");
            tempMap.put("ROW_COUNT","10");
            tempMap.put("PAGE","1");
        }else if("App-TOSSearch".indexOf(apiName) >= 0) {
            tempMap.put("SERVICE_TYPE","0");
            tempMap.put("ESSENTIAL_YN","A");
            tempMap.put("AGREE_YN","A");
        }else if("App-OTBProvide".indexOf(apiName) >= 0) {
            tempMap.put("PAYMENT_CODE","000040");
            tempMap.put("APP_TYPE","2");
            tempMap.put("AUTH_TYPE","0");
            tempMap.put("AUTH_KEY","");
        }else if("App-DeviceAppCheck".indexOf(apiName) >= 0) {
            tempMap.put("PAYMENT_CODE","000040");
            tempMap.put("APP_TYPE","2");
            tempMap.put("AUTH_TYPE","0");
            tempMap.put("AUTH_KEY","");
        }else if("App-PWValidCheck".indexOf(apiName) >= 0) {
            tempMap.put("XPT_PW","000040"); //무조건 불일치 뜸
        }else if("App-CPListSearch".indexOf(apiName) >= 0) {
            tempMap.put("CP_PAYMENT_KIND","10");
            tempMap.put("CP_CATEGORY_TYPE","00");
            tempMap.put("BLE_USE_YN","Y");
        }else if("App-CorpListSearch".indexOf(apiName) >= 0) {
            tempMap.put("CP_CODE","N111");
            tempMap.put("CUSTOMER_LOCATION_X","132.5");
            tempMap.put("CUSTOMER_LOCATION_Y","36.5");
        }else if("App-EventURLSearch".indexOf(apiName) >= 0) {
            tempMap.put("EVENT_STATUS","01");
        }else if("App-FAQURLSearch".indexOf(apiName) >= 0) {
            tempMap.put("FAQ_TYPE","01");
        }else if("App-CommonMessage".indexOf(apiName) >= 0) {
            tempMap.put("UPDATED_DATE","20170301000000");
        }else if("App-CommonResource".indexOf(apiName) >= 0) {
            tempMap.put("UPDATED_DATE","20170301000000");
        }
        threadRun(apiName,tempMap,idx);
//        testMap.put(apiName,tempMap);
    }

    public void threadRun(final String apiName, final HashMap<String,Object> param, final int idx){
        HashMap<String, Object> result;
        try {
            result = (HashMap<String, Object>) MagicSE.sendAPI(param, apiName, true);
            if(result.containsKey("RESULT_CODE")){
                setText(apiName,result.get("RESULT_CODE").toString());
            }else{
                setText(apiName,"LINKAGE_ERROR");
            }
        } catch (Exception e) {
            setText(apiName,"LINKAGE_ERROR");
        }
        setText2(idx);
        /*
        new Thread(new Runnable() {
            public void run() {
                HashMap<String, Object> result;
                try {
                    result = (HashMap<String, Object>) MagicSE.sendAPI(param, apiName, true);
                    if(result.containsKey("RESULT_CODE")){
                        setText(apiName,result.get("RESULT_CODE").toString());
                    }else{
                        setText(apiName,"LINKAGE_ERROR");
                    }
                } catch (Exception e) {
                    setText(apiName,"LINKAGE_ERROR");
                }
                setText2(idx);
            }
        }).start();
        */
    }

    final synchronized public void setText(String name, String value){
        testMap.put(name,"RESULT_CODE : "+value);
    }

    final synchronized public void setText2(int idx){
        returnCheckList.set(idx,new Boolean(true));
    }
}
