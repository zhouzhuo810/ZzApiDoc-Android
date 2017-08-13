package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * Created by admin on 2017/8/13.
 */

public class GetRequestHeaderResult {
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
        private String value;
        private String note;
        private String projectId;
        private String interfaceId;
        private String createTime;
        private String createUserName;
        private boolean isGlobal;

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getInterfaceId() {
            return interfaceId;
        }

        public void setInterfaceId(String interfaceId) {
            this.interfaceId = interfaceId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public boolean isGlobal() {
            return isGlobal;
        }

        public void setGlobal(boolean global) {
            isGlobal = global;
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
