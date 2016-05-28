package com.limb.customview.utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by limb on 2016/5/26.
 */
public final class SmsObserver extends ContentObserver {

    private final Cursor cursor;

    public SmsObserver(Handler handler, Context context) {
        super(handler);
        cursor = context.getContentResolver().query(Uri.parse("content://sms/outbox"), null, null, null, null);
    }

    @Override
    public void onChange(boolean selfChange) {// 查询发送箱中的短信(处于正在发送状态的短信放在发送箱)
        super.onChange(selfChange);
        Log.d("SmsObserver", "fdsafdsafa");
    }
}
