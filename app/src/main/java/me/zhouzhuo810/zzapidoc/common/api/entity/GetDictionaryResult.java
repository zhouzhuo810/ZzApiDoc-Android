package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 查询字典
 */
public class GetDictionaryResult {


    /**
     * code : 1
     * msg : ok
     * data : [{"name":"POST","pid":"0","id":"402885e65d6cb41a015d6cb5209c0002","type":"method"},{"name":"GET","pid":"0","id":"402885e65d6cb41a015d6cb539770003","type":"method"},{"name":"GET","pid":"0","id":"402885e65d6db8f0015d6db9b4200001","type":"method"}]
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
         * name : POST
         * pid : 0
         * id : 402885e65d6cb41a015d6cb5209c0002
         * type : method
         */

        private String name;
        private String pid;
        private String id;
        private String type;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}