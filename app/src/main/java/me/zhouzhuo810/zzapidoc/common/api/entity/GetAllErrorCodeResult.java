package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 获取所有错误码
 * Created by zhouzhuo810 on 2017/12/29.
 */

public class GetAllErrorCodeResult {
    private int code;
    private String msg;
    private List<DataEntity> data;

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
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
        private String code;
        private String note;
        private boolean isGlobal;
        private boolean isGroup;
        private String projectId;
        private String interfaceId;
        private String groupId;
        private String createTime;
        private String createMan;

        public String getCreateMan() {
            return createMan;
        }

        public void setCreateMan(String createMan) {
            this.createMan = createMan;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public boolean isGlobal() {
            return isGlobal;
        }

        public void setGlobal(boolean global) {
            isGlobal = global;
        }

        public boolean isGroup() {
            return isGroup;
        }

        public void setGroup(boolean group) {
            isGroup = group;
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

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }
}
