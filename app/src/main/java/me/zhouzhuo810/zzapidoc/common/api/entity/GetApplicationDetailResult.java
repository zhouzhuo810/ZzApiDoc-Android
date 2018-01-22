package me.zhouzhuo810.zzapidoc.common.api.entity;

/**
 * Created by zhouzhuo810 on 2018/1/22.
 */

public class GetApplicationDetailResult {
    private int code;
    private String msg;

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataEntity {
        private String id;
        private String chName;
        private String appName;
        private String versionName;
        private String packageName;
        private String colorMain;
        private int minSDK;
        private int compileSDK;
        private int targetSDK;
        private int versionCode;
        private boolean enableQrCode;
        private boolean multiDex;
        private boolean minifyEnabled;
        private String apiId;
        private String apiName;

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChName() {
            return chName;
        }

        public void setChName(String chName) {
            this.chName = chName;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getColorMain() {
            return colorMain;
        }

        public void setColorMain(String colorMain) {
            this.colorMain = colorMain;
        }

        public int getMinSDK() {
            return minSDK;
        }

        public void setMinSDK(int minSDK) {
            this.minSDK = minSDK;
        }

        public int getCompileSDK() {
            return compileSDK;
        }

        public void setCompileSDK(int compileSDK) {
            this.compileSDK = compileSDK;
        }

        public int getTargetSDK() {
            return targetSDK;
        }

        public void setTargetSDK(int targetSDK) {
            this.targetSDK = targetSDK;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public boolean isEnableQrCode() {
            return enableQrCode;
        }

        public void setEnableQrCode(boolean enableQrCode) {
            this.enableQrCode = enableQrCode;
        }

        public boolean isMultiDex() {
            return multiDex;
        }

        public void setMultiDex(boolean multiDex) {
            this.multiDex = multiDex;
        }

        public boolean isMinifyEnabled() {
            return minifyEnabled;
        }

        public void setMinifyEnabled(boolean minifyEnabled) {
            this.minifyEnabled = minifyEnabled;
        }

        public String getApiId() {
            return apiId;
        }

        public void setApiId(String apiId) {
            this.apiId = apiId;
        }
    }
}
