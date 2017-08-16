package me.zhouzhuo810.zzapidoc.common.api.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zhouzhuo810 on 2017/8/10.
 */

@Table(name = "IpEntity")
public class IpEntity {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "ip")
    private String ip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
