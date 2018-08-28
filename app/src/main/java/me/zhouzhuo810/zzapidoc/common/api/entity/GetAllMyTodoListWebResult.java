package me.zhouzhuo810.zzapidoc.common.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 *
 */
public class GetAllMyTodoListWebResult {

    private int indexPage;
    private int totalPage;
    private int totalRow;

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    private List<DataEntity> rows;

    public List<DataEntity> getRows() {
        return rows;
    }

    public void setRows(List<DataEntity> rows) {
        this.rows = rows;
    }

    public static class DataEntity implements Parcelable {
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

        private String isFinish;  //false

        public String getIsFinish() {
            return isFinish;
        }

        public void setIsFinish(String isFinish) {
            this.isFinish = isFinish;
        }

        private String content;  //

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.note);
            dest.writeString(this.handlePersonId);
            dest.writeString(this.createTime);
            dest.writeString(this.handlePersonName);
            dest.writeInt(this.progress);
            dest.writeString(this.createUserName);
            dest.writeString(this.id);
            dest.writeString(this.isFinish);
            dest.writeString(this.content);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.note = in.readString();
            this.handlePersonId = in.readString();
            this.createTime = in.readString();
            this.handlePersonName = in.readString();
            this.progress = in.readInt();
            this.createUserName = in.readString();
            this.id = in.readString();
            this.isFinish = in.readString();
            this.content = in.readString();
        }

        public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }
}