package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;

/**
 * 添加二维码
 */
public class AddQrCodeResult {
    private String msg;  //

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    private int code;  //code

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}