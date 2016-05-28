package com.limb.customview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.limb.customview.R;
import com.limb.customview.fragment.BlankFragment1;
import com.limb.customview.fragment.BlankFragment2;

public class FragmentActivity extends AppCompatActivity {

    private FragmentManager fManager;
    private BlankFragment2 fragment2;
    private BlankFragment1 fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        fManager = getSupportFragmentManager();
        final FragmentTransaction fTransaction = fManager.beginTransaction();
        fragment1 = new BlankFragment1();
        fTransaction.add(R.id.fl_fragment, fragment1);
        fTransaction.commit();


        findViewById(R.id.tv_bq2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fTransaction = fManager.beginTransaction();
                fragment2 = new BlankFragment2();
                fTransaction.replace(R.id.fl_fragment, fragment2);
                fTransaction.commit();
            }
        });
        findViewById(R.id.tv_bq1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fTransaction = fManager.beginTransaction();
                BlankFragment1 fragment1 = new BlankFragment1();
                fTransaction.replace(R.id.fl_fragment, fragment1);
                fTransaction.commit();
            }
        });
    }
}
