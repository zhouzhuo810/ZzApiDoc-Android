package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

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

    public static class DataEntity {
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

        private String id;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
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

        private String createTime;

        private String createUserName;

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
    }
}