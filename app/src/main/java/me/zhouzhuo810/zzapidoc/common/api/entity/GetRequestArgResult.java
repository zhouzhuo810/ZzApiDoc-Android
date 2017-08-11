package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class GetRequestArgResult {

    /**
     * code : 1
     * msg : ok
     * data : [{"note":"","name":"code","pid":"0","interfaceId":"4028805a5dcfe8d1015dcffe5b780002","type":"1"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * note :
         * name : code
         * pid : 0
         * interfaceId : 4028805a5dcfe8d1015dcffe5b780002
         * type : 1
         */
        private String id;
        private String note;
        private String name;
        private String pid;
        private String interfaceId;
        private int type;
        private String createTime;
        private String createUserName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getInterfaceId() {
            return interfaceId;
        }

        public void setInterfaceId(String interfaceId) {
            this.interfaceId = interfaceId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
