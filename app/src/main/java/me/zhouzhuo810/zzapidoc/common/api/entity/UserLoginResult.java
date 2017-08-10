package me.zhouzhuo810.zzapidoc.common.api.entity;

import java.util.List;
/**
 * 登录接口
 */
public class UserLoginResult {
       private int code; 
       public void setCode(int code) {
           this.code = code;
       }
       public int getCode() {
           return code;
       }
       private String msg; 
       public void setMsg(String msg) {
           this.msg = msg;
       }
       public String getMsg() {
           return msg;
       }
       private DataEntity data;
       public void setData(DataEntity data) {
           this.data = data;
       }
       public DataEntity getData() {
           return data;
       }
       public static class DataEntity {
       private String name; 
       public void setName(String name) {
           this.name = name;
       }
       public String getName() {
           return name;
       }
       private String id; 
       public void setId(String id) {
           this.id = id;
       }
       public String getId() {
           return id;
       }
       private String pic; 
       public void setPic(String pic) {
           this.pic = pic;
       }
       public String getPic() {
           return pic;
       }
       }
}