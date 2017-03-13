package com.example.js.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nshc.nfilter.NFilter;
import com.nshc.nfilter.command.view.NFilterOnClickListener;
import com.nshc.nfilter.command.view.NFilterTO;
import com.nshc.nfilter.util.NFilterLOG;
import com.nshc.nfilter.util.NFilterUtils;
import com.nshc.nfilter.util.SecurityHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017-03-08.
 */

public class NativeCodeWayViewTest extends Activity {

    private NFilter nfilter = null;
    private EditText et1, et2, et3 = null;
    Button btn1, btn2, btn3 = null;
    TextView tv, tmpTv = null;
    ProgressDialog dialog = null;

    String encdata_num = null;
    int plndatalength_num = 0;
    String dummydata_num = null;
    String plainNormalData_num = "";
    String plaindata_num = null;
    byte[] plainDataByte_num = null;

    String encdata_char = "";
    int plndatalength_char = 0;
    String dummydata_char = "";
    String plainNormalData_char = "";
    String plaindata_char = "";
    byte[] plainDataByte_char = null;

    public static final int DIALOG_CHAR = 33;
    public static final int DIALOG_NUM = 43;
    public static final int DIALOG_SERVER = 53;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case DIALOG_CHAR:
                    showDialog(DIALOG_CHAR);
                    //handler.sendEmptyMessageDelayed(DIALOG_CHAR, 1000);
                    break;
                case DIALOG_NUM:
                    showDialog(DIALOG_NUM);
                    //handler.sendEmptyMessageDelayed(DIALOG_NUM, 1000);
                    break;
                case DIALOG_SERVER:
                    showDialog(DIALOG_SERVER);
                    //handler.sendEmptyMessageDelayed(DIALOG_SERVER, 1000);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    String pubkey1 = "";
    String pubkey2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.nfilter_sample_native_view);
        nfilter = new NFilter(this);
        NFilterLOG.debug = true;
        nfilter.setNoPadding(true);
        nfilter.setPlainDataEnable(true);
        nfilter.registerReceiver();
        //공개키를 서버에서 받아오는 경우(상세사항은 메뉴얼 참고)
//		nfilter.setXmlURL("http://demo.nshc.net:8088/nfilter/static/keys/npublickey.xml");

        //샘플용 코드
        nfilter.setPublicKey("MDIwGhMABBYDBeMqMlebEzcxfXjbhvS73Ff+aNCtBBRvNh0rzMSq8OKxJoh15wDPqNZTNw==");

        // 문자 키패드 예제
        et1 = (EditText) findViewById(R.id.charEditText);
        et1.setInputType(0);
        et1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (nfilter.isNFilterViewVisibility() == View.VISIBLE)
                    nfilter.nFilterClose(View.GONE);
                nfilter.setFieldName("et1");   //EditText
                //입력값을 listener로 받는다.
                nfilter.setOnClickListener(new NFilterOnClickListener() {
                    @Override
                    public void onNFilterClick(NFilterTO nFilterTO) {
                        nFilterResult(nFilterTO);
                    }
                });
                //nFilter 실행 메서드
                //모든 옵션 설정이 끝나면 호출해준다.
                nfilter.onViewNFilter(NFilter.KEYPADCHAR);
            }
        });

        // 숫자 키패드 예제
        et2 = (EditText) findViewById(R.id.numEditText);
        et2.setInputType(0);
        et2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (nfilter.isNFilterViewVisibility() == View.VISIBLE)
                    nfilter.nFilterClose(View.GONE);
                nfilter.setFieldName("et2");
                //입력값을 listener로 받는다.
                nfilter.setOnClickListener(new NFilterOnClickListener() {
                    @Override
                    public void onNFilterClick(NFilterTO nFilterTO) {
                        nFilterResult(nFilterTO);
                    }
                });
                //nFilter 실행 메서드
                //모든 옵션 설정이 끝나면 호출해준다.
                nfilter.onViewNFilter(NFilter.KEYPADNUM);
            }
        });

        // 숫자 키패드 예제
        et3 = (EditText) findViewById(R.id.serialNumEditText);
        et3.setInputType(0);
        et3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (nfilter.isNFilterViewVisibility() == View.VISIBLE)
                    nfilter.nFilterClose(View.GONE);
                nfilter.setFieldName("et3");
                //입력값을 listener로 받는다.
                nfilter.setOnClickListener(new NFilterOnClickListener() {
                    @Override
                    public void onNFilterClick(NFilterTO nFilterTO) {
                        nFilterResult(nFilterTO);
                    }
                });
                //nFilter 실행 메서드
                //모든 옵션 설정이 끝나면 호출해준다.
                nfilter.onViewNFilter(NFilter.KEYPADSERIALNUM);
            }
        });

        btn1 = (Button) findViewById(R.id.num_conf);
        btn1.setOnClickListener(onClickListener);
        btn2 = (Button) findViewById(R.id.char_conf);
        btn2.setOnClickListener(onClickListener);
        btn3 = (Button) findViewById(R.id.server_conf);
        btn3.setOnClickListener(onClickListener);

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(view);
        switch (id) {
            case DIALOG_CHAR:
                builder.setTitle("문자 입력값 정보");
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        removeDialog(DIALOG_CHAR);
                        handler.removeMessages(DIALOG_CHAR);
                    }
                });

                handler.sendEmptyMessageDelayed(DIALOG_CHAR, 1000);
                return builder.create();

            case DIALOG_NUM:
                builder.setTitle("숫자 입력값 정보");
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        removeDialog(DIALOG_NUM);
                        handler.removeMessages(DIALOG_NUM);
                    }
                });

                handler.sendEmptyMessageDelayed(DIALOG_NUM, 1000);
                return builder.create();

            case DIALOG_SERVER:
                builder.setTitle("서버 복호화 정보");
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        removeDialog(DIALOG_SERVER);
                        handler.removeMessages(DIALOG_SERVER);
                    }
                });

                handler.sendEmptyMessageDelayed(DIALOG_SERVER, 1000);
                return builder.create();

            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        // TODO Auto-generated method stub
        tv = (TextView) dialog.findViewById(R.id.message);
        switch (id) {
            case DIALOG_CHAR:
                if (plndatalength_char != 0) {
                    tv.setText("\r\n" +
                            "문자 암호화 데이터 :: " + encdata_char + "\r\n\r\n" +
                            "문자 평문 데이터 :: " + plaindata_char + "\r\n\r\n" +
                            "문자 사용자 입력 데이터 :: " + new String(plainDataByte_char) + "\r\n");
                } else {
                    tv.setText("null");
                }
                break;
            case DIALOG_NUM:
                if (plndatalength_num != 0) {
                    tv.setText("\r\n" +
                            "숫자 암호화 데이터 :: " + encdata_num + "\r\n\r\n" +
                            "숫자 평문 데이터 :: " + plaindata_num + "\r\n\r\n" +
                            "숫자 사용자 입력 데이터 :: " + new String(plainDataByte_num) + "\r\n");
                } else {
                    tv.setText("null");
                }

                break;
            case DIALOG_SERVER:
                if (plndatalength_num != 0 && plndatalength_char != 0) {
                    new DecryptAsyncTask().execute("http://demo.nshc.net:8088/nfilter/static/nfilter_prop.jsp");
                } else {
                    tv.setText("문자/숫자 데이터를 입력해 주세요");
                }

                break;
            default:
                break;
        }
        super.onPrepareDialog(id, dialog);
    }

//    HttpClient httpclient =  getThreadSafeClient();
//    public String getDecryptData(String URL){
//        try {
//
//            HttpPost post = new HttpPost( URL );
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("encdata1", encdata_num));
//            params.add(new BasicNameValuePair("encdata2", encdata_char));
//            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//            HttpResponse response = null;
//            String endResult = null;
//            response = httpclient.execute(post);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//
//            endResult = convertStreamToString(instream);
//            return endResult;
//        } catch (HttpResponseException e) {
//            e.printStackTrace();
//            return "HttpResponseException";
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "IOException";
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return "Exception";
//        }
//    }

    @Override
    public Object getSystemService(String name) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            return SecurityHelper.getWrappedSystemService(super.getSystemService(name), name);
        } else {
            return super.getSystemService(name);
        }
    }

//    public DefaultHttpClient getThreadSafeClient()  {
//
//        DefaultHttpClient client = new DefaultHttpClient();
//        ClientConnectionManager mgr = client.getConnectionManager();
//        HttpParams params = client.getParams();
//        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
//        return client;
//    }


    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.num_conf:
                    showDialog(DIALOG_NUM);
                    break;

                case R.id.char_conf:
                    showDialog(DIALOG_CHAR);
                    break;

                case R.id.server_conf:
                    if (plndatalength_char != 0 && plndatalength_num != 0)
                        dialog = ProgressDialog.show(NativeCodeWayViewTest.this, "로딩 중...", "잠시만 기다려주세요", false);
                    showDialog(DIALOG_SERVER);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (nfilter.isNFilterViewVisibility() == View.VISIBLE) {
            nfilter.nFilterClose(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    /**
     * nFilter 리턴값 처리 메서드
     * sample 예제 이므로 상황에 맞게 변경하여 사용하시면됩니다.
     *
     * @param nFilterTO
     */
    public void nFilterResult(NFilterTO nFilterTO) {

        if (nFilterTO.getFocus() == NFilter.NEXTFOCUS) {
            if (new String(nFilterTO.getFieldName()).equals("et1")) {
            } else if (new String(nFilterTO.getFieldName()).equals("et2")) {
            }

            nfilter.nFilterClose(View.GONE); //nFilter 닫기
        } else if (nFilterTO.getFocus() == NFilter.PREFOCUS) {
            if (new String(nFilterTO.getFieldName()).equals("et1")) {
            } else if (new String(nFilterTO.getFieldName()).equals("et2")) {
            }

            nfilter.nFilterClose(View.GONE);
        } else if (nFilterTO.getFocus() == NFilter.DONEFOCUS) {
            if (new String(nFilterTO.getFieldName()).equals("et1")) {
            } else if (new String(nFilterTO.getFieldName()).equals("et2")) {
            }

            nfilter.nFilterClose(View.GONE);
        } else {
            if (nFilterTO.getPlainLength() > 0) {
                NFilterLOG.i("padding getFieldName", "getFieldName : " + new String(nFilterTO.getFieldName()));
                //리턴 값을 해당 TextView에 넣는다.
                if (new String(nFilterTO.getFieldName()).equals("et1")) {
                    et1.setText(new String(nFilterTO.getDummyData()));

                    encdata_char = nFilterTO.getEncData();
                    plndatalength_char = nFilterTO.getPlainLength();
                    dummydata_char = nFilterTO.getDummyData();
                    plainNormalData_char = nFilterTO.getPlainNormalData();
                    plaindata_char = nFilterTO.getPlainData();
                    plainDataByte_char = NFilterUtils.getInstance().nSaferDecryptWithBase64(plaindata_char);
                    NFilterLOG.i("padding getPlainLength", "getPlainLength : " + nFilterTO.getPlainLength());
                    NFilterLOG.i("padding getDummyData", "getDummyData : " + (nFilterTO.getDummyData()));
                    NFilterLOG.i("padding getEncData", "getEncData : " + (nFilterTO.getEncData()));
                    Log.e("nFilterResult", "AES Enc Data : " + nFilterTO.getAESEncData());
                    Log.e("nFilter", "nfilter plain data : " + new String(plainDataByte_char));
                    // 입력필드가 가상키보드에 가려서 보이지 않을 경우
                    // 임시로 값을 보여주는 editText
                    // nfilter_char_key_view.xml 32라인에서 직접 수정 가능

                    // ================================================================ //

                } else if (new String(nFilterTO.getFieldName()).equals("et2")) {
                    et2.setText(new String(nFilterTO.getDummyData()));

                    encdata_num = nFilterTO.getEncData();
                    plndatalength_num = nFilterTO.getPlainLength();
                    dummydata_num = nFilterTO.getDummyData();
                    plainNormalData_num = nFilterTO.getPlainNormalData();
                    plaindata_num = nFilterTO.getPlainData();
                    plainDataByte_num = NFilterUtils.getInstance().nSaferDecryptWithBase64(plaindata_num);
                    NFilterLOG.i("padding getPlainLength", "getPlainLength : " + nFilterTO.getPlainLength());
                    NFilterLOG.i("padding getDummyData", "getDummyData : " + (nFilterTO.getDummyData()));
                    NFilterLOG.i("padding getEncData", "getEncData : " + (nFilterTO.getEncData()));
                    Log.e("nFilterResult", "AES Enc Data : " + nFilterTO.getAESEncData());
                    Log.e("nFilter", "nfilter plain data : " + new String(plainDataByte_num));
                    // 입력필드가 가상키보드에 가려서 보이지 않을 경우
                    // 임시로 값을 보여주는 editText
                    // nfilter_num_key_view.xml 32라인에서 직접 수정 가능

                } else if (new String(nFilterTO.getFieldName()).equals("et3")) {
                    et3.setText(new String(nFilterTO.getDummyData()));

                    encdata_num = nFilterTO.getEncData();
                    plndatalength_num = nFilterTO.getPlainLength();
                    dummydata_num = nFilterTO.getDummyData();
                    plainNormalData_num = nFilterTO.getPlainNormalData();
                    plaindata_num = nFilterTO.getPlainData();
                    plainDataByte_num = NFilterUtils.getInstance().nSaferDecryptWithBase64(plaindata_num);
                    NFilterLOG.i("padding getPlainLength", "getPlainLength : " + nFilterTO.getPlainLength());
                    NFilterLOG.i("padding getDummyData", "getDummyData : " + (nFilterTO.getDummyData()));
                    NFilterLOG.i("padding getEncData", "getEncData : " + (nFilterTO.getEncData()));
                    Log.e("nFilterResult", "AES Enc Data : " + nFilterTO.getAESEncData());
                    Log.e("nFilter", "nfilter plain data : " + new String(plainDataByte_num));
                    // 입력필드가 가상키보드에 가려서 보이지 않을 경우
                    // 임시로 값을 보여주는 editText
                    // nfilter_num_key_view.xml 32라인에서 직접 수정 가능

                }

            } else {
                //리턴 값을 해당 TextView에 넣는다.
                if (new String(nFilterTO.getFieldName()).equals("et1")) {
                    et1.setText("");

                    // 입력필드가 가상키보드에 가려서 보이지 않을 경우
                    // 임시로 값을 보여주는 editText
                    // nfilter_char_key_view.xml 32라인에서 직접 수정 가능
                    // ================================================================ //

                } else if (new String(nFilterTO.getFieldName()).equals("et2")) {

                    et2.setText("");
                    // 입력필드가 가상키보드에 가려서 보이지 않을 경우
                    // 임시로 값을 보여주는 editText
                    // nfilter_num_key_view.xml 32라인에서 직접 수정 가능
                    // ================================================================ //

                }
            }

        }
    }

    //    public String setSyncCookie( String publicUrl ){
//        try {
//
//            HttpPost post = new HttpPost( publicUrl );
//            HttpResponse response = null;
//            String endResult = null;
//
//            response = httpclient.execute(post);
//            HttpEntity entity = response.getEntity();
//            InputStream instream = entity.getContent();
//
//            endResult = convertStreamToString(instream);
//
//            return endResult;
//        } catch (HttpResponseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return "";
//    }
    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    class DecryptAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            tv.setText(result);
            dialog.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected String doInBackground(String... params) {
//            String temp = getDecryptData(params[0]);
//            String data = temp.replaceAll("<br>", "\r\n");
//            return data;
            return null;
        }
    }

    class publicKeyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            nfilter.setPublicKey(result);
            super.onPostExecute(result);
        }

        @Override
        protected String doInBackground(String... params) {
//            String publicKey = setSyncCookie(params[0]);
//            System.out.println("@@@@@@@@@@@@@@@ publickey data : " + publicKey);
//            return publicKey;
            return null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        nfilter.configurationChanged();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        nfilter.unregisterReceiver();
        super.onDestroy();
    }


}