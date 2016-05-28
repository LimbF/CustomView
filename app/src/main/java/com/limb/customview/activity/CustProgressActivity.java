package com.limb.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.limb.customview.R;

public class CustProgressActivity extends AppCompatActivity{

    private ProgressBar progress1;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_progress);
        initView();
        showData();
    }

    private void initView() {
        progress1 = (ProgressBar)findViewById(R.id.pb_cust_progress_progress1);
        text = ((TextView)findViewById(R.id.textView));
    }

    private void showData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i <3; i++) {
                        Thread.sleep(1000);
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(finalI + "");
                            }
                        });
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
