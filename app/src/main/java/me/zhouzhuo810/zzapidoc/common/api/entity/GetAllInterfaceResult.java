package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

import me.zhouzhuo810.zzapidoc.common.rule.SearchAble;

/**
 * 查询接口列表
 */
public class GetAllInterfaceResult {
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

    public static class DataEntity implements SearchAble {
        private String method;

        public void setMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        private String ip;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        private String id;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        private String note;

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        private String group;

        public void setGroup(String group) {
            this.group = group;
        }

        public String getGroup() {
            return group;
        }

        private int requestParamsNo;

        private int responseParamsNo;

        private int requestHeadersNo;

        private String createTime;

        private String createUserName;

        private String testUserName;

        private boolean isTest;

        public String getTestUserName() {
            return testUserName;
        }

        public void setTestUserName(String testUserName) {
            this.testUserName = testUserName;
        }

        public boolean isTest() {
            return isTest;
        }

        public void setTest(boolean test) {
            isTest = test;
        }

        public int getRequestHeadersNo() {
            return requestHeadersNo;
        }

        public void setRequestHeadersNo(int requestHeadersNo) {
            this.requestHeadersNo = requestHeadersNo;
        }

        public int getRequestParamsNo() {
            return requestParamsNo;
        }

        public void setRequestParamsNo(int requestParamsNo) {
            this.requestParamsNo = requestParamsNo;
        }

        public int getResponseParamsNo() {
            return responseParamsNo;
        }

        public void setResponseParamsNo(int responseParamsNo) {
            this.responseParamsNo = responseParamsNo;
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

        @Override
        public String toSearch() {
            return name + note + path;
        }
    }
}