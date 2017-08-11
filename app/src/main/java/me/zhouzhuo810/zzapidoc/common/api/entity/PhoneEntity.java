package me.zhouzhuo810.zzapidoc.common.api.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zhouzhuo810 on 2017/8/10.
 */

@Table(name = "PhoneEntity")
public class PhoneEntity {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "phone")
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
