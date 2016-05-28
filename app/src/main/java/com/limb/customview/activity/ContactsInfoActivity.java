package com.limb.customview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.limb.customview.OrderAdapter;
import com.limb.customview.R;
import com.limb.customview.bean.ContactsInfo;
import com.limb.customview.utils.ContactsInfoListUtils;
import com.limb.customview.utils.HanziToPinyin;

import java.util.ArrayList;

public class ContactsInfoActivity extends AppCompatActivity {

    private ListView contactList;

    private ArrayList<ContactsInfo> conData = new ArrayList<>();

    private OrderAdapter adapter;

    private TextView showTop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_info);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        contactList = (ListView)findViewById(R.id.lv_contacts_info_list);
        showTop = (TextView)findViewById(R.id.showbar);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        conData = ContactsInfoListUtils.getContacts(this, true);
        adapter = new OrderAdapter(this, conData);
        contactList.setAdapter(adapter);
        contactList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int firstP = view.getFirstVisiblePosition();
                ContactsInfo con = (ContactsInfo)view.getItemAtPosition(firstP);
                showTop.setVisibility(View.VISIBLE);
                showTop.setText(HanziToPinyin.toPinYin(con.getConName().charAt(0)).charAt(0) + "");
                showTop.setBackgroundColor(Color.GRAY);
            }
        });
    }
}
