package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * Created by zhouzhuo810 on 2017/8/18.
 */

public class GetAllApplicationResult {

    /**
     * code : 1
     * msg : ok
     * data : [{"minifyEnabled":"false","minSDK":"15","colorMain":"#438cff","appName":"Myapplication","multiDex":"null","logo":"F:\\javaEE\\ZzApiDoc\\classes\\artifacts\\ZzApiDoc\\image\\ic_launcher.png","targetSDK":"25","packageName":"com.example.myapplication","versionName":"1.0","compileSDK":"25","versionCode":"1","apiId":"4028803f5dea335e015dea4834c80000"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * minifyEnabled : false
         * minSDK : 15
         * colorMain : #438cff
         * appName : Myapplication
         * multiDex : null
         * logo : F:\javaEE\ZzApiDoc\classes\artifacts\ZzApiDoc\image\ic_launcher.png
         * targetSDK : 25
         * packageName : com.example.myapplication
         * versionName : 1.0
         * compileSDK : 25
         * versionCode : 1
         * apiId : 4028803f5dea335e015dea4834c80000
         */
        private String id;
        private String minifyEnabled;
        private String minSDK;
        private String colorMain;
        private String appName;
        private String multiDex;
        private String logo;
        private String targetSDK;
        private String packageName;
        private String versionName;
        private String compileSDK;
        private String versionCode;
        private String apiId;
        private String createTime;
        private String chName;
        private int actCount;
        private int fgmCount;

        public int getActCount() {
            return actCount;
        }

        public void setActCount(int actCount) {
            this.actCount = actCount;
        }

        public int getFgmCount() {
            return fgmCount;
        }

        public void setFgmCount(int fgmCount) {
            this.fgmCount = fgmCount;
        }

        public String getChName() {
            return chName;
        }

        public void setChName(String chName) {
            this.chName = chName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMinifyEnabled() {
            return minifyEnabled;
        }

        public void setMinifyEnabled(String minifyEnabled) {
            this.minifyEnabled = minifyEnabled;
        }

        public String getMinSDK() {
            return minSDK;
        }

        public void setMinSDK(String minSDK) {
            this.minSDK = minSDK;
        }

        public String getColorMain() {
            return colorMain;
        }

        public void setColorMain(String colorMain) {
            this.colorMain = colorMain;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getMultiDex() {
            return multiDex;
        }

        public void setMultiDex(String multiDex) {
            this.multiDex = multiDex;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTargetSDK() {
            return targetSDK;
        }

        public void setTargetSDK(String targetSDK) {
            this.targetSDK = targetSDK;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getCompileSDK() {
            return compileSDK;
        }

        public void setCompileSDK(String compileSDK) {
            this.compileSDK = compileSDK;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getApiId() {
            return apiId;
        }

        public void setApiId(String apiId) {
            this.apiId = apiId;
        }
    }
}
