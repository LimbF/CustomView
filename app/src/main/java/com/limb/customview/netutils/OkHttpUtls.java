package com.limb.customview.netutils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by limb on 2016/6/16.
 */
public class OkHttpUtls {
    private static OkHttpUtls instance;
    private OkHttpClient okHttpClient;
    public ThreadPoolManager manager;

    private OkHttpUtls() {
        //此处配置OkHttpClient的基本信息,okhttp3在new对象并需要配置参数一般通过build这个方法来实现,类似的还有Request：
        //构建形式如:new XXX.Builder().xxx().xxx().build();
        okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        manager = ThreadPoolManager.getInstance();
    }

    //单例模式,为什么使用?有兴趣可参考：http://www.cnblogs.com/seesea125/archive/2012/04/05/2433463.html
    //个人认为：1.此类在应用中唯一,似乎没有再new的必要
    //2.此类类似于工具类,为了方便却需要提取类变量okHttpClient,因此不能作为工具类只提供静态方法(感觉不能表达清楚我的想法敲打)
    public static OkHttpUtls getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtls.class) {
                if (instance == null) {
                    instance = new OkHttpUtls();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @param url
     * @param params
     * @param callBack
     */
    public void getMethod(final Activity activity,final String url, final Map<String, String> params , final ServiceDataCallBack callBack) {
        Log.i("net_url", "NET_URL:" + url);
        manager.addTask(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                if (params != null && params.size() > 0) {
                    Set<Map.Entry<String, String>> entrySet = params.entrySet();
                    sb.append("?");
                    for (Map.Entry<String, String> entry : entrySet) {
                        sb.append(entry.getKey());
                        sb.append("=");
                        try {
                            sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        sb.append("&");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                Request request = new Request.Builder().url(url + sb.toString()).get().build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.failure();
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string();

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (TextUtils.isEmpty(result)) {
                                    Log.d("net_url", "OkHttpUtls_data:数据为空");
                                } else {
                                    Log.d("net_url", "OkHttpUtls_data:" + result);
                                    callBack.success(result);
                                }
                            }
                        });

                    }
                });
            }
        });

    }
    /**
     * 一般的post请求 对于一般的请求，我们希望给个url和封装参数的Map，然后取的返回的String。
     */
    public void postMethod(final Activity activity,final String url, final Map<String, String> params, final ServiceDataCallBack callBack) {
        Log.i("net_url","NET_URL:"+url);
        manager.addTask(new Runnable() {
            @Override
            public void run() {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                if (params != null && params.size() > 0) {
                    Set<Map.Entry<String, String>> entrySet = params.entrySet();
                    for (Map.Entry<String, String> entry : entrySet) {
                        formBodyBuilder.add(entry.getKey(), entry.getValue());
                    }
                }
                Request request = new Request.Builder().url(url).post(formBodyBuilder.build()).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.failure();
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String result = response.body().string();

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (TextUtils.isEmpty(result)) {
                                    Log.d("net_url", "OkHttpUtls_data:数据为空");
                                } else {
                                    Log.d("net_url", "OkHttpUtls_data:" + result);
                                    callBack.success(result);
                                }
                            }
                        });

                    }
                });
            }
        });
    }

    public interface ServiceDataCallBack{
        void failure();
        void success(String jsonData);
    }
}
