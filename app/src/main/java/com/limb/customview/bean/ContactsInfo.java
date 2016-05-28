package com.limb.customview.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 联系人信息实体类
 * Created by limb on 2016/5/19.
 */
public class ContactsInfo implements Serializable {
    private String conName;//联系人姓名
    private ArrayList<String> conPhoneNum;//联系人电话号码
    private String conId;//联系人id

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public ArrayList<String> getConPhoneNum() {
        return conPhoneNum;
    }

    public void setConPhoneNum(ArrayList<String> conPhoneNum) {
        this.conPhoneNum = conPhoneNum;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    @Override
    public String toString() {
        return "ContactsInfo{" +
                "conName='" + conName + '\'' +
                ", conPhoneNum='" + conPhoneNum + '\'' +
                ", conId='" + conId + '\'' +
                '}';
    }
}
