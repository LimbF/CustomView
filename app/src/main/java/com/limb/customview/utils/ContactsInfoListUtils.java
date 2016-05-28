package com.limb.customview.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.limb.customview.bean.ContactsInfo;

import java.util.ArrayList;

/**
 * 获取联系人信息工具类
 * Created by limb on 2016/5/19.
 */
public class ContactsInfoListUtils {

    public static ArrayList<ContactsInfo> getContacts(Context context,boolean isSort){
        ArrayList<ContactsInfo> contactsInfos = new ArrayList<>();

        // 得到ContentResolver对象
        ContentResolver cr = context.getContentResolver();
        // 取得电话本中开始一项的光标,主要就是查询"contacts"表
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()){
            ContactsInfo contactsInfo = new ContactsInfo();
            // 取得联系人名字 (显示出来的名字)，实际内容在 ContactsContract.Contacts中
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameIndex);
            contactsInfo.setConName(name);

            // 取得联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            contactsInfo.setConId(contactId);

            // 根据联系人ID查询对应的电话号码
            Cursor phoneNumbers = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                    + contactId, null, null);
            // 取得电话号码(可能存在多个号码)
            ArrayList<String> pNumList = new ArrayList<>();
            while (phoneNumbers.moveToNext()){
                String strPhoneNumber = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                pNumList.add(strPhoneNumber);
            }
            contactsInfo.setConPhoneNum(pNumList);
            phoneNumbers.close();
            contactsInfos.add(contactsInfo);
        }
        cursor.close();
        //是否按拼音首字母排序
        if(isSort){
            contactsInfos = sortData(contactsInfos);
        }
        return contactsInfos;
    }

    /**
     * 排序
     * @param conData
     * @return
     */
    private static ArrayList<ContactsInfo> sortData(ArrayList<ContactsInfo> conData){
        ArrayList<ContactsInfo> cData = new ArrayList<>();

        for (int i = 'a'; i <= 'z'; i++) {
            for (int j = 0; j < conData.size(); j++) {
                if (HanziToPinyin.toPinYin(conData.get(j).getConName().charAt(0)).charAt(0) == i) {
                    cData.add(conData.get(j));
                }
            }
        }
        return cData;
    }
}
