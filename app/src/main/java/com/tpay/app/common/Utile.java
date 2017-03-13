package com.tpay.app.common;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2017-03-03.
 * Device ID등 폰 공통적으로 호출하는 부분
 */

public class Utile {

    public String getDeviceId(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
