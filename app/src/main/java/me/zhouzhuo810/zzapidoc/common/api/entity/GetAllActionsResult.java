package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 获取所有动作
 */
public class GetAllActionsResult {
    private int code;  //

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private String msg;  //

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
        private String id;  //

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        private String name;  //

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private int type;  //

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        private String okText;  //

        public void setOkText(String okText) {
            this.okText = okText;
        }

        public String getOkText() {
            return okText;
        }

        private String cancelText;  //

        public void setCancelText(String cancelText) {
            this.cancelText = cancelText;
        }

        public String getCancelText() {
            return cancelText;
        }

        private String defText;  //

        public void setDefText(String defText) {
            this.defText = defText;
        }

        public String getDefText() {
            return defText;
        }

        private String hintText;  //

        public void setHintText(String hintText) {
            this.hintText = hintText;
        }

        public String getHintText() {
            return hintText;
        }

        private String msg;  //

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        private String items;  //

        public void setItems(String items) {
            this.items = items;
        }

        public String getItems() {
            return items;
        }

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private String createTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        private String createPerson;

        public String getCreatePerson() {
            return createPerson;
        }

        public void setCreatePerson(String createPerson) {
            this.createPerson = createPerson;
        }
    }
}