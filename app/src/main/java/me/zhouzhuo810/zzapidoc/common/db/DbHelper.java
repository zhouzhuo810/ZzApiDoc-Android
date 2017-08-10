package me.zhouzhuo810.zzapidoc.common.db;


import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

import me.zhouzhuo810.zzapidoc.common.Constants;

public class DbHelper {
    private static DbHelper helper;

    private DbHelper() {
    }

    public static DbHelper getInstance() {
        if (helper == null) {
            synchronized (DbHelper.class) {
                if (helper == null) {
                    helper = new DbHelper();
                }
            }
        }
        return helper;
    }

    public DbManager getDbManager() {
        File file = new File(Constants.DBPATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(Constants.DBNAME)
                .setDbDir(new File(Constants.DBPATH))
                .setDbVersion(Constants.DBVERSION)
                .setDbUpgradeListener(null);
        try {
            return x.getDb(daoConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
