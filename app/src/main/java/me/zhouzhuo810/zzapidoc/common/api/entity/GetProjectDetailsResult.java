package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 查询项目详情
 */
public class GetProjectDetailsResult {
    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String note;

        public void setNote(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private String property;

        public void setProperty(String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        private String createUserName;

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        private String id;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}