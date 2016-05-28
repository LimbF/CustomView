package com.limb.customview.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by limb on 2016/5/4.
 */
public class DisplayImageUtils {
    private final int MAXMEMONRY;
    private LruCache<String, Bitmap> mMemoryCache;
    public Handler h = new Handler();

    public DisplayImageUtils(){
        MAXMEMONRY = (int) (Runtime.getRuntime() .maxMemory() / 1024);
    }
    //调用Lrucache的get 方法从内存缓存中去图片
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }
    public void LruCacheUtils() {
        if(mMemoryCache == null) {
            mMemoryCache = new LruCache<String, Bitmap>(MAXMEMONRY / 8) {

                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }

                @Override
                protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                }
            };
        }
    }
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null && bitmap!=null) {
            mMemoryCache.put(key, bitmap);
        }
    }
    /**
     *
     * @param iv 显示图片
     * @param img 是否选择图片
     * @param thumbPath
     * @param sourcePath
     * @param callback
     */
    public void displayBmp(final ImageView iv,final ImageView img,final String thumbPath,
                           final String sourcePath, final ImageCallback callback) {
        if (TextUtils.isEmpty(thumbPath) && TextUtils.isEmpty(sourcePath)) {
            return;
        }

        final String path;
        final boolean isThumbPath;
        if (!TextUtils.isEmpty(thumbPath)) {
            path = thumbPath;
            isThumbPath = true;
        } else if (!TextUtils.isEmpty(sourcePath)) {
            path = sourcePath;
            isThumbPath = false;
        } else {
            // iv.setImageBitmap(null);
            return;
        }
        LruCacheUtils();
        if(getBitmapFromMemoryCache(path)==null){
            new Thread() {
                Bitmap thumb;
                public void run() {

                    try {
                        if (isThumbPath) {
                            thumb = BitmapFactory.decodeFile(thumbPath);
                            if (thumb == null) {
                                thumb = revitionImageSize(sourcePath);
                            }
                        } else {
                            thumb = revitionImageSize(sourcePath);
                        }
                    } catch (Exception e) {

                    }
                    if (thumb == null) {
                    }
                    addBitmapToMemoryCache(path,thumb);

                    if (callback != null) {
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.imageLoad(iv, img,thumb, sourcePath);
                            }
                        });
                    }
                }
            }.start();
        }else {
            iv.setImageBitmap(getBitmapFromMemoryCache(path));
        }
    }

    public Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 256)
                    && (options.outHeight >> i <= 256)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    public interface ImageCallback {
        public void imageLoad(ImageView imageView, ImageView img,Bitmap bitmap,
                              Object... params);
    }
}
