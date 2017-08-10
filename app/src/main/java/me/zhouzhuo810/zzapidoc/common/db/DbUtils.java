package me.zhouzhuo810.zzapidoc.common.db;


import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.List;

import me.zhouzhuo810.zzapidoc.common.api.entity.PhoneEntity;

/**
 * Created by zz on 2017/7/5.
 */

public class DbUtils {


    /**
     * 保存没有保存的手机号
     */
    public static void savePhone(String phone) {
        DbManager dbManager = DbHelper
                .getInstance()
                .getDbManager();
        if (dbManager == null)
            return;

        try {
            long count = dbManager.selector(PhoneEntity.class).where("phone", "=", phone).count();
            PhoneEntity entity = new PhoneEntity();
            entity.setPhone(phone);
            if (count <= 0) {
                dbManager.save(entity);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有手机号
     *
     * @return
     */
    public static List<PhoneEntity> getAllPhones() {
        DbManager dbManager = DbHelper
                .getInstance()
                .getDbManager();
        if (dbManager == null)
            return null;
        try {
            return dbManager.findAll(PhoneEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

/*
    */
/**
 * 保存所有消息
 * @param msgs
 *//*

    public static void saveMsg(List<GetMessageResult.DataEntity> msgs) {
        DbManager dbManager = DbHelper
                .getInstance()
                .getDbManager();
        if (dbManager == null)
            return;
        try {
            dbManager.save(msgs);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    */
/**
 * 获取所有消息
 * @return
 *//*

    public static List<GetMessageResult.DataEntity> getAllMsgs() {
        DbManager dbManager = DbHelper
                .getInstance()
                .getDbManager();
        if (dbManager == null)
            return null;
        try {
            return dbManager.findAll(GetMessageResult.DataEntity.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

*/

}
