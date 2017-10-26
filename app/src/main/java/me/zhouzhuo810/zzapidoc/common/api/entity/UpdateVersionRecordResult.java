package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;
/**
 * 更新版本记录
 */
public class UpdateVersionRecordResult {
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
}