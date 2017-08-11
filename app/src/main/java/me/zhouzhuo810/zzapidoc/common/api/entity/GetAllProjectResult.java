package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 查询项目列表
 */
public class GetAllProjectResult {
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

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
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

        private String createTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        private int interfaceNo;

        public int getInterfaceNo() {
            return interfaceNo;
        }

        public void setInterfaceNo(int interfaceNo) {
            this.interfaceNo = interfaceNo;
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