package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

import me.zhouzhuo810.zzapidoc.common.rule.SearchAble;

/**
 * Created by admin on 2017/8/27.
 */

public class GetAllMyWidgetResult {

    /**
     * code : 1
     * msg : ok
     * data : [{"targetActivityId":"","showLeftTitleLayout":"false","rightTitleText":"","leftTitleImg":"","showRightTitleImg":"false","type":"2","title":"用户名","resId":"username","defValue":"","rightTitleImg":"","showLeftTitleImg":"true","showRightTitleLayout":"false","showLeftTitleText":"true","createTime":"2017-08-27 13:07:34","leftTitleText":"","hint":"","appId":"","name":"","showRightTitleText":"false","id":"402885e85e2211c2015e221683590000"}]
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

    public static class DataBean implements SearchAble {
        /**
         * targetActivityId :
         * showLeftTitleLayout : false
         * rightTitleText :
         * leftTitleImg :
         * showRightTitleImg : false
         * type : 2
         * title : 用户名
         * resId : username
         * defValue :
         * rightTitleImg :
         * showLeftTitleImg : true
         * showRightTitleLayout : false
         * showLeftTitleText : true
         * createTime : 2017-08-27 13:07:34
         * leftTitleText :
         * hint :
         * appId :
         * name :
         * showRightTitleText : false
         * id : 402885e85e2211c2015e221683590000
         */

        private String targetActivityId;
        private String showLeftTitleLayout;
        private String rightTitleText;
        private String leftTitleImg;
        private String showRightTitleImg;
        private int type;
        private String title;
        private String resId;
        private String defValue;
        private String rightTitleImg;
        private String showLeftTitleImg;
        private String showRightTitleLayout;
        private String showLeftTitleText;
        private String createTime;
        private String leftTitleText;
        private String hint;
        private String appId;
        private String name;
        private String showRightTitleText;
        private String id;
        private String createUserName;

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getTargetActivityId() {
            return targetActivityId;
        }

        public void setTargetActivityId(String targetActivityId) {
            this.targetActivityId = targetActivityId;
        }

        public String getShowLeftTitleLayout() {
            return showLeftTitleLayout;
        }

        public void setShowLeftTitleLayout(String showLeftTitleLayout) {
            this.showLeftTitleLayout = showLeftTitleLayout;
        }

        public String getRightTitleText() {
            return rightTitleText;
        }

        public void setRightTitleText(String rightTitleText) {
            this.rightTitleText = rightTitleText;
        }

        public String getLeftTitleImg() {
            return leftTitleImg;
        }

        public void setLeftTitleImg(String leftTitleImg) {
            this.leftTitleImg = leftTitleImg;
        }

        public String getShowRightTitleImg() {
            return showRightTitleImg;
        }

        public void setShowRightTitleImg(String showRightTitleImg) {
            this.showRightTitleImg = showRightTitleImg;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getResId() {
            return resId;
        }

        public void setResId(String resId) {
            this.resId = resId;
        }

        public String getDefValue() {
            return defValue;
        }

        public void setDefValue(String defValue) {
            this.defValue = defValue;
        }

        public String getRightTitleImg() {
            return rightTitleImg;
        }

        public void setRightTitleImg(String rightTitleImg) {
            this.rightTitleImg = rightTitleImg;
        }

        public String getShowLeftTitleImg() {
            return showLeftTitleImg;
        }

        public void setShowLeftTitleImg(String showLeftTitleImg) {
            this.showLeftTitleImg = showLeftTitleImg;
        }

        public String getShowRightTitleLayout() {
            return showRightTitleLayout;
        }

        public void setShowRightTitleLayout(String showRightTitleLayout) {
            this.showRightTitleLayout = showRightTitleLayout;
        }

        public String getShowLeftTitleText() {
            return showLeftTitleText;
        }

        public void setShowLeftTitleText(String showLeftTitleText) {
            this.showLeftTitleText = showLeftTitleText;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLeftTitleText() {
            return leftTitleText;
        }

        public void setLeftTitleText(String leftTitleText) {
            this.leftTitleText = leftTitleText;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShowRightTitleText() {
            return showRightTitleText;
        }

        public void setShowRightTitleText(String showRightTitleText) {
            this.showRightTitleText = showRightTitleText;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toSearch() {
            return name + title;
        }
    }
}
