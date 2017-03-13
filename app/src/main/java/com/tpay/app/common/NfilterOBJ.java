package com.tpay.app.common;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.nshc.nfilter.NFilter;
import com.nshc.nfilter.command.view.NFilterOnClickListener;
import com.nshc.nfilter.command.view.NFilterTO;
import com.nshc.nfilter.util.NFilterLOG;

import org.json.JSONObject;

import java.util.HashMap;

import static android.util.Log.d;
import static com.tpay.app.common.MagicSE.magicse;

/**
 * Nfilter Class
 * Created by Administrator on 2017-03-08.
 *
 */

public class NfilterOBJ {

    private static NFilter nfilter = null;
    private static NfilterOBJ nfilterObj = null;
    private static String publicKey = null;

    public static final int DIALOG_CHAR = 33;
    public static final int DIALOG_NUM = 43;
    public static final int DIALOG_SERVER = 53;

    public synchronized static NfilterOBJ getInstance(Context context, String nfilterKey){
        d("Info : ","NfilterOBJ Init nfilterKey = "+nfilterKey);
        if(nfilterObj == null){
            nfilterObj = new NfilterOBJ();
            nfilter = new NFilter(context);
            nfilter.setPublicKey(nfilterKey);
            publicKey = nfilterKey;
        }
        d("Info :",nfilterObj.toString());
        return  nfilterObj;
    }

    public void nFilterload(final View loadView,final int rID,final long KeyPad_Type,final int requestCode) {
        d("Info","nFilterload : "+KeyPad_Type);
        /***
         * Native - View 방식
         * Android TextFields를 이용하여, NFilter View를 호출하십시오
         */
        final EditText edittext = (EditText) loadView.findViewById(rID);
        edittext.setInputType(InputType.TYPE_NULL);
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d("Info","onClick : "+view);
                d("Debug","nfilter.isNFilterViewVisibility() : "+nfilter.isNFilterViewVisibility()+"/  "+View.VISIBLE);
                if( nfilter.isNFilterViewVisibility() == View.VISIBLE) nfilter.nFilterClose( View.GONE );
                d("Info","onClick 1 : "+view);
                nfilter.setFieldName("edittext");
                d("Info","onClick 2 : "+view);
                //입력값을 listener로 받는다.
                nfilter.setOnClickListener( new NFilterOnClickListener() {
                    @Override
                    public void onNFilterClick(NFilterTO nFilterTO) {
                        d("Info","onClick 3 : IN");
                        nFilterResult( nFilterTO,edittext );
                    }
                });
                d("Info","onClick 4 : "+view);
                //nFilter 실행 메서드
                //모든 옵션 설정이 끝나면 호출해준다.
                nfilter.onViewNFilter(NFilter.KEYPADNUM);
            }
        });
    }

    /**
     * nFilter 암호화
     * 미사용
     * @param data
     * @return
     */
    public String encnFilter(String data){

        return null;
    }

    /**
     * nFilter 복호화
     * 미사용
     * @param data
     * @return
     */
    public String decnFilter(String data){

        return null;
    }

    public void nFilterload(final Activity parent, final long keypadnum, final int textView) {
        /***
         * Native - Activity 방식
         * Android TextFields를 이용하여, NFilter Activity를 호출하십시오.
         */
        EditText edittext = (EditText)parent.findViewById(textView);
        edittext.setInputType(InputType.TYPE_NULL);
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nfilter.onNFilter(parent.findViewById(textView), keypadnum, textView);
                /**
                 * 숫자 키패드만 사용하므로 숫자로 픽스
                 */
                //nfilter.onNFilter(view, NFilter.KEYPADNUM,200);
            }
        });
    }

    /**
     * nFilter 리턴값 처리 메서드
     * @param nFilterTO
     */
    public void nFilterResult(NFilterTO nFilterTO,EditText edt) {
        if( nFilterTO.getFocus() == NFilter.NEXTFOCUS ){
            if( new String(  nFilterTO.getFieldName() ).equals("et1") ){
            }else if( new String(  nFilterTO.getFieldName() ).equals("et2") ){
            }

            nfilter.nFilterClose(View.GONE); //nFilter 닫기
        }else if( nFilterTO.getFocus() == NFilter.PREFOCUS ){
            if( new String(  nFilterTO.getFieldName() ).equals("et1") ){
            }else if( new String(  nFilterTO.getFieldName() ).equals("et2") ){
            }

            nfilter.nFilterClose(View.GONE);
        }else if( nFilterTO.getFocus() == NFilter.DONEFOCUS ){
            if( new String(  nFilterTO.getFieldName() ).equals("et1") ){
            }else if( new String(  nFilterTO.getFieldName() ).equals("et2") ){
            }

            nfilter.nFilterClose(View.GONE);
        }else {
            if (nFilterTO.getPlainLength() > 0) {
                d("Debug", "getFieldName : " + new String(nFilterTO.getFieldName()));
                NFilterLOG.i("padding getFieldName", "getFieldName : " + new String(nFilterTO.getFieldName()));
                if (new String(nFilterTO.getFieldName()).equals("edittext")) {
                    /**
                     * 텍스트 박스에 DummyData 설정
                     */
                    edt.setText(new String(nFilterTO.getDummyData()));
                    d("Debug", "publicKey : " + publicKey);
                    d("Debug", "getPlainLength : " + nFilterTO.getPlainLength());
                    d("Debug", "getDummyData : " + nFilterTO.getDummyData());
                    d("Debug", "getEncData : " + nFilterTO.getEncData());
                    d("Debug", "AES Enc Data : " + nFilterTO.getAESEncData());
                    d("Debug", "publicKey : " + publicKey+", EncData : "+nFilterTO.getEncData());
                    if(nFilterTO.getPlainLength()==4){
                        try {

                            d("TAG", "TEST Start");

                            JSONObject obj = new JSONObject();
                            HashMap<String,Object> map = new HashMap<String,Object>();
                            map.put("SESSION_ID", "2683f67bcb38cccddafbee013fa3d304af324c30");
                            d("TAG", "TEST obj : " + obj);
                            HashMap<String,Object> obj2 = magicse.sendAPI(map, "App-SessionInitialize", true);
                            obj.put("TEST","publicKey : MDIwGhMABBYCBwdyLJttNninBhpefKUXTjaggox0BBTsw60R7rE1uuKrCHHewl7S3IAbzw==, EncData : MDIwGhMABBYCBOy37nFhzS1C6xdQMhlUrp8yYhT8BBR7vnnsxFs28cWOgUHp0iPoX6Abdg==TkZfTlVN/CwGSKXTHkJqHmaOSLRy1icYfv+gD+3Ak90hYud8qsJAiX0=");
                            d("TEST1 : ", obj2.toString());
                            map = new HashMap<String,Object>();
                            map.put("XPT_PW",nFilterTO.getEncData());
                            obj2 = (HashMap<String, Object>) magicse.sendAPI(map, "App-PWValidCheck", true);
                            d("TEST2 : ", obj2.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        d("Debug", "nfilter plain data IN");
                        String plaindata_num = nFilterTO.getPlainData();
                        d("Debug", "nfilter plain data : " + plaindata_num);
                    } catch (Exception e) {
                        d("Error", "" + e.getMessage());
                    }
                }
            }
        }
    }
}
