package com.limb.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.limb.customview.photo.DisplayImageUtils;
import com.limb.customview.photo.ImageItem;

import java.util.ArrayList;

/**
 * Created by limb on 2016/5/4.
 */
public class MyAdapter extends BaseAdapter{
    private DisplayImageUtils display;
    private Context context ;
    private ArrayList<ImageItem> arrayList;

    private DisplayImageUtils.ImageCallback imageCallback = new DisplayImageUtils.ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, ImageView img, Bitmap bitmap, Object... params) {
            if (imageView != null && bitmap != null) {
               (imageView).setImageBitmap(bitmap);
            }

        }
    };
    public MyAdapter(Context context , ArrayList<ImageItem> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        display = new DisplayImageUtils();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder= null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.mian2,parent,false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        display.displayBmp(mHolder.imag, null
                , arrayList.get(position).thumbnailPath
                , arrayList.get(position).imagePath
                , imageCallback);

        return convertView;
    }

    public static class ViewHolder{
        private ImageView imag;

        public ViewHolder(View view){
            imag = (ImageView) view.findViewById(R.id.image);
        }
    }
}
