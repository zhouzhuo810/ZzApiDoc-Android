package me.zhouzhuo810.zzapidoc.common.api.entity;

/**
 * Created by zhouzhuo810 on 2017/12/29.
 */

public class DeleteErrorCodeResult {
    private int code;
    private String msg;

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
}
