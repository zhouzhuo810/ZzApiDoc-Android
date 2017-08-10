package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 查询接口详情
 */
public class GetInterfaceDetailsResult {
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
        private String path;

        public void setPath(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        private String note;

        public void setNote(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }

        private String groupName;

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupName() {
            return groupName;
        }

        private String createTime;

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private RequestArgsEntity requestArgs;

        public void setRequestArgs(RequestArgsEntity requestArgs) {
            this.requestArgs = requestArgs;
        }

        public RequestArgsEntity getRequestArgs() {
            return requestArgs;
        }

        public static class RequestArgsEntity {
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

            private String pid;

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getPid() {
                return pid;
            }

            private String typeId;

            public void setTypeId(String typeId) {
                this.typeId = typeId;
            }

            public String getTypeId() {
                return typeId;
            }

            private String id;

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }
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

        private String httpMethod;

        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        private String projectName;

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectName() {
            return projectName;
        }

        private ResponseArgsEntity responseArgs;

        public void setResponseArgs(ResponseArgsEntity responseArgs) {
            this.responseArgs = responseArgs;
        }

        public ResponseArgsEntity getResponseArgs() {
            return responseArgs;
        }

        public static class ResponseArgsEntity {
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

            private String pid;

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getPid() {
                return pid;
            }

            private String typeId;

            public void setTypeId(String typeId) {
                this.typeId = typeId;
            }

            public String getTypeId() {
                return typeId;
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
}