package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * Created by zhouzhuo810 on 2017/8/11.
 */

public class GetAllInterfaceGroupResult {

    /**
     * code : 1
     * msg : ok
     * data : [{"createUserId":"4028805a5dceaaa5015dced0ee720000","interfaceNo":"0","createTime":1502429599000,"name":"默认分组","createUserName":"周卓","id":"4028805a5dcfc77c015dcfc855dd0000"}]
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
         * createUserId : 4028805a5dceaaa5015dced0ee720000
         * interfaceNo : 0
         * createTime : 1502429599000
         * name : 默认分组
         * createUserName : 周卓
         * id : 4028805a5dcfc77c015dcfc855dd0000
         */

        private String createUserId;
        private String interfaceNo;
        private String createTime;
        private String name;
        private String createUserName;
        private String id;

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getInterfaceNo() {
            return interfaceNo;
        }

        public void setInterfaceNo(String interfaceNo) {
            this.interfaceNo = interfaceNo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
