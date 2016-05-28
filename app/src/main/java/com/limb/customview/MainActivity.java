package com.limb.customview;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import com.limb.customview.activity.ContactsInfoActivity;
import com.limb.customview.activity.CustProgressActivity;
import com.limb.customview.activity.CustViewDragLayoutActivity;
import com.limb.customview.activity.Custom3DViewActivity;
import com.limb.customview.activity.DynamicAddViewActivity;
import com.limb.customview.activity.FragmentActivity;
import com.limb.customview.activity.Main2Activity;
import com.limb.customview.activity.ThreedPoolManagerActivity;
import com.limb.customview.photo.ImageItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GridView gridView;
    ArrayList<ImageItem> arrayList = new ArrayList<>();
    private MyAdapter adapter;
    private Vibrator myVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        //测试高斯模糊
        findViewById(R.id.btn_test_gsmh).setOnClickListener(this);
        findViewById(R.id.btn_test_cust3dview).setOnClickListener(this);
        findViewById(R.id.btn_test_custviewdraglayout).setOnClickListener(this);
        findViewById(R.id.btn_test_zhendong).setOnClickListener(this);
        findViewById(R.id.btn_test_transition_drawable).setOnClickListener(this);
        findViewById(R.id.btn_test_fragment).setOnClickListener(this);
        findViewById(R.id.btn_test_contact_InfoShow).setOnClickListener(this);
        findViewById(R.id.btn_test_progress_style).setOnClickListener(this);
        findViewById(R.id.btn_test_threedPool_manager).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //测试高是模糊
            case R.id.btn_test_gsmh:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;
            case R.id.btn_test_cust3dview:
                startActivity(new Intent(MainActivity.this, Custom3DViewActivity.class));
                break;
            case R.id.btn_test_custviewdraglayout:
                startActivity(new Intent(MainActivity.this, CustViewDragLayoutActivity.class));
                break;
            case R.id.btn_test_zhendong:
                //别忘添加震动权限
                //取消震动
//                myVibrator.cancel();
                //长震动
                myVibrator.vibrate(new long[]{100, 100, 100, 1000}, 0);
                //节奏震动 时间间隔控制
//                myVibrator.vibrate(new long[]{500, 100, 500, 100}, 0);
                break;
            case R.id.btn_test_transition_drawable:
                startActivity(new Intent(MainActivity.this, DynamicAddViewActivity.class));
                break;
            case R.id.btn_test_fragment:
                startActivity(new Intent(MainActivity.this, FragmentActivity.class));
                break;
            //获取联系人信息，显示列表
            case R.id.btn_test_contact_InfoShow:
                startActivity(new Intent(MainActivity.this, ContactsInfoActivity.class));
                break;
            case R.id.btn_test_progress_style:
                startActivity(new Intent(MainActivity.this, CustProgressActivity.class));
                break;
            case R.id.btn_test_threedPool_manager:
                startActivity(new Intent(MainActivity.this, ThreedPoolManagerActivity.class));
                break;
        }
    }


//    private void init() {
//        gridView = (GridView) findViewById(R.id.gridview);
//        arrayList = (ArrayList<ImageItem>) PhoneAllPhotoPathUtils.getPhotoListPath(this, false);
//        adapter = new MyAdapter(this,arrayList);
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().
//                        appendPath(arrayList.get(position).imageId).build();
//                PickPic.pickPic(MainActivity.this, uri, 0x0010, getWindowManager().getDefaultDisplay().getWidth());
//            }
//        });
//    }
}
