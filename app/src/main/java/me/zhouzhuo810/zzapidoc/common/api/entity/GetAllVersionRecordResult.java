package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 过去所有版本记录
 */
public class GetAllVersionRecordResult {
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
        private String id;  //

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        private String note;  //

        public void setNote(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }

        private String userName;  //

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

        private String createTime;  //

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }
    }
}