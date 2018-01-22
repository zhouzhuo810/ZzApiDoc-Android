package me.zhouzhuo810.zzapidoc.common.api.entity;

/**
 * Created by zhouzhuo810 on 2018/1/22.
 */

public class GetActivityDetailResult {
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
        private String name;
        private String title;
        private boolean isFirst;
        private int type;
        private String appId;
        private String targetActId;
        private String targetActName;
        private boolean isLandscape;
        private boolean isFullScreen;
        private int splashSecond;
        private int guideImgCount;
        private String modifyTime;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst(boolean first) {
            isFirst = first;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

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

        public boolean isLandscape() {
            return isLandscape;
        }

        public void setLandscape(boolean landscape) {
            isLandscape = landscape;
        }

        public boolean isFullScreen() {
            return isFullScreen;
        }

        public void setFullScreen(boolean fullScreen) {
            isFullScreen = fullScreen;
        }

        public int getSplashSecond() {
            return splashSecond;
        }

        public void setSplashSecond(int splashSecond) {
            this.splashSecond = splashSecond;
        }

        public int getGuideImgCount() {
            return guideImgCount;
        }

        public void setGuideImgCount(int guideImgCount) {
            this.guideImgCount = guideImgCount;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }
    }
}
