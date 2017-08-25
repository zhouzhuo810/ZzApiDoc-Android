package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * Created by admin on 2017/8/19.
 */

public class GetAllMyActivityResult {
    private int code;
    private String msg;
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String name;
        private String type;
        private String splashImg;
        private String splashSecond;
        private String targetActId;
        private String targetActName;
        private String title;
        private boolean showTitle;
        private String appId;
        private String createTime;

        public String getTargetActId() {
            return targetActId;
        }

        public void setTargetActId(String targetActId) {
            this.targetActId = targetActId;
        }

        public String getTargetActName() {
            return targetActName;
        }

        public void setTargetActName(String targetActName) {
            this.targetActName = targetActName;
        }

        public String getSplashSecond() {
            return splashSecond;
        }

        public void setSplashSecond(String splashSecond) {
            this.splashSecond = splashSecond;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSplashImg() {
            return splashImg;
        }

        public void setSplashImg(String splashImg) {
            this.splashImg = splashImg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isShowTitle() {
            return showTitle;
        }

        public void setShowTitle(boolean showTitle) {
            this.showTitle = showTitle;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
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
}
