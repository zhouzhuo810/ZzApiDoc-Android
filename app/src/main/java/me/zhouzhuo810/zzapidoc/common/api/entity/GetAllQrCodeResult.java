package me.zhouzhuo810.zzapidoc.common.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 获取所有二维码
 */
public class GetAllQrCodeResult {
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

    public static class DataEntity implements Parcelable {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String url;  //

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        private String content;  //

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        private String title;  //

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        private boolean isPrivate;  //

        public void setIsPrivate(boolean isPrivate) {
            this.isPrivate = isPrivate;
        }

        public boolean getIsPrivate() {
            return isPrivate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.url);
            dest.writeString(this.content);
            dest.writeString(this.title);
            dest.writeByte(this.isPrivate ? (byte) 1 : (byte) 0);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.id = in.readString();
            this.url = in.readString();
            this.content = in.readString();
            this.title = in.readString();
            this.isPrivate = in.readByte() != 0;
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