package me.zhouzhuo810.zzapidoc.common.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/8/13.
 */

public class InterfaceTestRequestHeaderEntity implements Parcelable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public InterfaceTestRequestHeaderEntity() {
    }

    protected InterfaceTestRequestHeaderEntity(Parcel in) {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<InterfaceTestRequestHeaderEntity> CREATOR = new Parcelable.Creator<InterfaceTestRequestHeaderEntity>() {
        @Override
        public InterfaceTestRequestHeaderEntity createFromParcel(Parcel source) {
            return new InterfaceTestRequestHeaderEntity(source);
        }

        @Override
        public InterfaceTestRequestHeaderEntity[] newArray(int size) {
            return new InterfaceTestRequestHeaderEntity[size];
        }
    };
}
