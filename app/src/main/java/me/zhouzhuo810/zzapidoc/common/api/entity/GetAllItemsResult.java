package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * getAllItems
 */
public class GetAllItemsResult {
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

        private int type;  //

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        private String name;  //

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        private String widgetName;  //

        public void setWidgetName(String widgetName) {
            this.widgetName = widgetName;
        }

        public String getWidgetName() {
            return widgetName;
        }

        private String parentWidgetName;  //

        public String getParentWidgetName() {
            return parentWidgetName;
        }

        public void setParentWidgetName(String parentWidgetName) {
            this.parentWidgetName = parentWidgetName;
        }

        private String createTime;  //

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        private String createPerson;  //

        public void setCreatePerson(String createPerson) {
            this.createPerson = createPerson;
        }

        public String getCreatePerson() {
            return createPerson;
        }
    }
}