package me.zhouzhuo810.zzapidoc.common.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/8/12.
 */

public class InterfaceTestRequestArgEntity implements Parcelable {
    private String name;
    private int type = 0;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public InterfaceTestRequestArgEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.type);
        dest.writeString(this.value);
    }

    protected InterfaceTestRequestArgEntity(Parcel in) {
        this.name = in.readString();
        this.type = in.readInt();
        this.value = in.readString();
    }

    public static final Creator<InterfaceTestRequestArgEntity> CREATOR = new Creator<InterfaceTestRequestArgEntity>() {
        @Override
        public InterfaceTestRequestArgEntity createFromParcel(Parcel source) {
            return new InterfaceTestRequestArgEntity(source);
        }

        @Override
        public InterfaceTestRequestArgEntity[] newArray(int size) {
            return new InterfaceTestRequestArgEntity[size];
        }
    };
}
