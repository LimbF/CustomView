package com.limb.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.limb.customview.R;
import com.limb.customview.view.CustViewDragHelprLayout;

public class CustViewDragLayoutActivity extends AppCompatActivity {

    private CustViewDragHelprLayout cvdhlayout;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_view_drag_layout);
        cvdhlayout = (CustViewDragHelprLayout)findViewById(R.id.cvdhlayout);
        tv = (TextView)findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustViewDragLayoutActivity.this, "dinajile", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
