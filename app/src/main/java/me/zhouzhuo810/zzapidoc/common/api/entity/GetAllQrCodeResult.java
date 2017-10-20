package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 获取所有二维码
 */
public class GetAllQrCodeResult {
    private int code;  //

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private String msg;  //

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String url;  //

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        private String content;  //

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        private String title;  //

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        private boolean isPrivate;  //

        public void setIsPrivate(boolean isPrivate) {
            this.isPrivate = isPrivate;
        }

        public boolean getIsPrivate() {
            return isPrivate;
        }
    }
}