package com.example.js.myapplication;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.util.Log.d;

public class LoginActivity extends Activity {
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

                /* 사용자의 OS 버전이 마시멜로우 이상인지 체크한다. */
                d("TAG","Build.VERSION.SDK_INT : "+Build.VERSION.SDK_INT);
                d("TAG","Build.VERSION_CODES.SDK_INT : "+Build.VERSION_CODES.M);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    d("AAA","DDDD");
                    int permissionResult = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
                    /* READ_PHONE_STATE 권한이 없을 때 */
                    // 패키지는 안드로이드 어플리케이션의 아이디다.( 어플리케이션 구분자 )
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {

                        /* 사용자가 READ_PHONE_STATE 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                        * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                        */
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {

                            AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
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
                            requestPermissions(new String[]{Manifest.permission.INTERNET}, 1000);
                        }

                    }
                    /* CALL_PHONE의 권한이 있을 때 */
                    else {
                        startActivity(intent);
                    }

                }
                /* 사용자의 OS 버전이 마시멜로우 이하일 떄 */
                else {
                    startActivity(intent);
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
            /* 요청한 권한을 사용자가 "허용"했다면 인텐트를 띄움(원래 같으면 for문)*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "권한 요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
