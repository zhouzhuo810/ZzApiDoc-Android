package me.zhouzhuo810.zzapidoc.common.api.entity;

/**
 * Created by zhouzhuo810 on 2018/1/22.
 */

public class GetWidgetDetailResult {
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
        private String resId;
        private String defValue;
        private int type;
        private String hint;
        private String leftTitleText;
        private String rightTitleText;
        private boolean showLeftTitleImg;
        private boolean showRightTitleImg;
        private boolean showLeftTitleText;
        private boolean showRightTitleText;
        private boolean showLeftTitleLayout;
        private boolean showRightTitleLayout;
        private String pid;
        private String background;
        private int width;
        private int height;
        private float weight;
        private int marginLeft;
        private int marginRight;
        private int marginTop;
        private int marginBottom;
        private int paddingLeft;
        private int paddingRight;
        private int paddingTop;
        private int paddingBottom;
        private String gravity;
        private String orientation;
        private String relativeId;
        private String appId;
        private String textColor;
        private int textSize;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getLeftTitleText() {
            return leftTitleText;
        }

        public void setLeftTitleText(String leftTitleText) {
            this.leftTitleText = leftTitleText;
        }

        public String getRightTitleText() {
            return rightTitleText;
        }

        public void setRightTitleText(String rightTitleText) {
            this.rightTitleText = rightTitleText;
        }

        public boolean isShowLeftTitleImg() {
            return showLeftTitleImg;
        }

        public void setShowLeftTitleImg(boolean showLeftTitleImg) {
            this.showLeftTitleImg = showLeftTitleImg;
        }

        public boolean isShowRightTitleImg() {
            return showRightTitleImg;
        }

        public void setShowRightTitleImg(boolean showRightTitleImg) {
            this.showRightTitleImg = showRightTitleImg;
        }

        public boolean isShowLeftTitleText() {
            return showLeftTitleText;
        }

        public void setShowLeftTitleText(boolean showLeftTitleText) {
            this.showLeftTitleText = showLeftTitleText;
        }

        public boolean isShowRightTitleText() {
            return showRightTitleText;
        }

        public void setShowRightTitleText(boolean showRightTitleText) {
            this.showRightTitleText = showRightTitleText;
        }

        public boolean isShowLeftTitleLayout() {
            return showLeftTitleLayout;
        }

        public void setShowLeftTitleLayout(boolean showLeftTitleLayout) {
            this.showLeftTitleLayout = showLeftTitleLayout;
        }

        public boolean isShowRightTitleLayout() {
            return showRightTitleLayout;
        }

        public void setShowRightTitleLayout(boolean showRightTitleLayout) {
            this.showRightTitleLayout = showRightTitleLayout;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public int getMarginLeft() {
            return marginLeft;
        }

        public void setMarginLeft(int marginLeft) {
            this.marginLeft = marginLeft;
        }

        public int getMarginRight() {
            return marginRight;
        }

        public void setMarginRight(int marginRight) {
            this.marginRight = marginRight;
        }

        public int getMarginTop() {
            return marginTop;
        }

        public void setMarginTop(int marginTop) {
            this.marginTop = marginTop;
        }

        public int getMarginBottom() {
            return marginBottom;
        }

        public void setMarginBottom(int marginBottom) {
            this.marginBottom = marginBottom;
        }

        public int getPaddingLeft() {
            return paddingLeft;
        }

        public void setPaddingLeft(int paddingLeft) {
            this.paddingLeft = paddingLeft;
        }

        public int getPaddingRight() {
            return paddingRight;
        }

        public void setPaddingRight(int paddingRight) {
            this.paddingRight = paddingRight;
        }

        public int getPaddingTop() {
            return paddingTop;
        }

        public void setPaddingTop(int paddingTop) {
            this.paddingTop = paddingTop;
        }

        public int getPaddingBottom() {
            return paddingBottom;
        }

        public void setPaddingBottom(int paddingBottom) {
            this.paddingBottom = paddingBottom;
        }

        public String getGravity() {
            return gravity;
        }

        public void setGravity(String gravity) {
            this.gravity = gravity;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getRelativeId() {
            return relativeId;
        }

        public void setRelativeId(String relativeId) {
            this.relativeId = relativeId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getTextColor() {
            return textColor;
        }

        public void setTextColor(String textColor) {
            this.textColor = textColor;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }
    }
}
