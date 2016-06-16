package com.limb.customview.bean;

/**
 * Created by limb on 2016/6/16.
 */
public class Test {
    private String total;
    private String status;
    private String msg;
    private String resData;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResData() {
        return resData;
    }

    public void setResData(String resData) {
        this.resData = resData;
    }

    @Override
    public String toString() {
        return "Test{" +
                "total='" + total + '\'' +
                ", status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", resData='" + resData + '\'' +
                '}';
    }
}
