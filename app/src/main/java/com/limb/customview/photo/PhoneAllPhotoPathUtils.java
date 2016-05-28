package com.limb.customview.photo;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limb on 2016/5/4.
 */
public class PhoneAllPhotoPathUtils {
    private static ArrayList<String> photoPhthList = new ArrayList<>();
    // 指定获取的列
    private static String columns[] = new String[]{MediaStore.Images.Media.DATA
            , MediaStore.Images.Media._ID
            , MediaStore.Images.Media.TITLE
            , MediaStore.Images.Media.DISPLAY_NAME
            , MediaStore.Images.Media.SIZE};
    private static int photoIndex;
    private static int photoNameIndex;
    private static int photoIDIndex;
    private static int photoTitleIndex;
    private static int photoSizeIndex;
    private static int totalNum;
    private static Cursor cursor;
    private static int bucketDisplayNameIndex;


    /**
     * 获取手机中所有图片的路径集合（不分相册）
     *
     * @param context
     * @return
     */
    public static ArrayList<String> getAllPhotoPath(Context context) {
        // 得到一个游标
        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , columns, null, null, null);
        // 获取指定列的索引
        photoIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
        photoNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        photoIDIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        photoTitleIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
        photoSizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
        // 获取图片总数
        totalNum = cursor.getCount();
        while (cursor.moveToNext()) {
            setImage();
        }
        return photoPhthList;
    }

    /**
     * 获取图片的详细信息
     */
    private static void setImage() {
        // 获取图片的Name
        String name = cursor.getString(photoNameIndex);
        // 获取图片的ID
        String number = cursor.getString(photoIDIndex);
        // 获取图片的Title
        String title = cursor.getString(photoTitleIndex);
        // 获取图片的大小
        String size = cursor.getString(photoSizeIndex);
        // 获取图片存储路径
        String path = cursor.getString(photoIndex);
        photoPhthList.add(path);
    }

    /**
     * 获取相册列表，是否分相册显示
     *
     * @return
     */
    public static List<?> getPhotoListPath(Context context, boolean isNeedPhotoList) {
        AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(context);
        ArrayList<ImageItem> imageItem = new ArrayList<>();
        List<ImageBucket> imageBuckets = helper.getImagesBucketList(false);
        //部分相册，全部显示手机图片
        if (!isNeedPhotoList) {
            for (int i = 0; i < imageBuckets.size(); i++) {
                for (int j = 0; j < imageBuckets.get(i).imageList.size(); j++) {
                    imageItem.add(imageBuckets.get(i).imageList.get(j));
                }
            }
            //返回的所有图片对象
            return imageItem;
        }
        //返回的为相册对象
        return imageBuckets;
    }
}
