package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 *
 */
public class GetAllMyTodoListResult {
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
        private String note;  //

        public void setNote(String note) {
            this.note = note;
        }

        public String getNote() {
            return note;
        }

        private String handlePersonId;  //

        public void setHandlePersonId(String handlePersonId) {
            this.handlePersonId = handlePersonId;
        }

        public String getHandlePersonId() {
            return handlePersonId;
        }

        private String createTime;  //

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        private String handlePersonName;  //

        public void setHandlePersonName(String handlePersonName) {
            this.handlePersonName = handlePersonName;
        }

        public String getHandlePersonName() {
            return handlePersonName;
        }

        private int progress;  //0

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public int getProgress() {
            return progress;
        }

        private String createUserName;  //

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        private String id;  //

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        private int isFinish;  //0

        public void setIsFinish(int isFinish) {
            this.isFinish = isFinish;
        }

        public int getIsFinish() {
            return isFinish;
        }

        private String content;  //

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}