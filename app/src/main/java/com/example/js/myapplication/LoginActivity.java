package com.example.js.myapplication;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tpay.app.common.MagicSE;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private Intent intent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_login);

        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //사용자의 OS 버전이 마시멜로우 이상인지 체크한다.
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    int permissionResult = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
                    //READ_PHONE_STATE 권한이 없을 때
                    // 패키지는 안드로이드 어플리케이션의 아이디다.( 어플리케이션 구분자 )
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {

                        //사용자가 READ_PHONE_STATE 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                        //      * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("이 기능을 사용하기 위해서는 단말기의 \"번호읽기\" 권한이 필요합니다. 계속하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1000);
                                            }
                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getApplicationContext(), "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        //최초로 권한을 요청할 때
                        else {
                            // READ_PHONE_STATE 권한을 Android OS 에 요청한다.
                            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1000);
                        }

                    }
                    //CALL_PHONE의 권한이 있을 때
                    else {
                        new AsyncNetworkConnection().execute();
                    }

                }
                //사용자의 OS 버전이 마시멜로우 이하일 떄
                else {
                    new AsyncNetworkConnection().execute();
                }
            }
        });

        Button nFilterButton = (Button) findViewById(R.id.nFilter_button);
        nFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NfilterSampleActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            //요청한 권한을 사용자가 "허용" 했다면 인텐트를 띄움(원래 같으면 for문)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new AsyncNetworkConnection().execute();
            } else {
                Toast.makeText(getApplicationContext(), "권한 요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    class AsyncNetworkConnection extends AsyncTask<Void, Integer, HashMap> {
        ProgressDialog progressDialog;
        HashMap<String, Object> result;
        Resources resources = getApplicationContext().getResources();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "연결중", "잠시만 기다려주세요", false);
        }

        // 처리를 하는 메소드
        @Override
        protected HashMap doInBackground(Void... params) {
            try {
                result = MagicSE.getInstance(LoginActivity.this).sendAPI(new HashMap<String, Object>(), resources.getString(R.string.login_check_api), false);
                Log.d("TAG", result.toString());
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // 처리가 모두 끝나면 불리는 메소드
        @Override
        protected void onPostExecute(HashMap result) {
            super.onPostExecute(result);
            synchronized (this) {
                while (progressDialog.isShowing()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                }
            }
            if (result.get(resources.getString(R.string.result_code)).equals(resources.getString(R.string.result_value_zero))) {
                intent.putExtra(resources.getString(R.string.mdn), result.get(resources.getString(R.string.mdn)).toString());
                startActivity(intent);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage(resources.getString(R.string.login_comment_info) + "\n (에뮬레이터 테스트 위해 임시 통과 처리)")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                // 임시 통과 처리
                                //LoginActivity.this.finish();
                                startActivity(intent);
                            }
                        }).show();
            }
            return;
        }
    }
}
