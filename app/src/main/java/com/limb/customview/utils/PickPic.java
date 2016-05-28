package com.limb.customview.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by limb on 2016/5/5.
 */
public class PickPic {
    static File hOrw = new File(Environment.getExternalStorageDirectory(),
    "hOrw" + ".jpg");

    public static void pickPic(Activity context, Uri uri, int requestCode,int size){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 是否保留比例
        intent.putExtra("scaleUpIfNeeded", true);// 黑边

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", size);
        intent.putExtra("aspectY", 282);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", 282);


        //4.3一下，剪切后正常又返回值，以上没有。
        //解决办法，让其返回值为false，然后将其保存在另一文件夹下，然后使用该保存的文件
        intent.putExtra("return-data", false);
        hOrw = new File(Environment.getExternalStorageDirectory(),
                "hOrw" + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(hOrw));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        context.startActivityForResult(intent, requestCode);
    }

}
