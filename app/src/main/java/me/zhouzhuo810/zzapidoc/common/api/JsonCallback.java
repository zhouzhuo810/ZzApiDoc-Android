package me.zhouzhuo810.zzapidoc.common.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;

/**
 * Created by zhouzhuo810 on 2017/8/18.
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Class<T> clazz;
    private Gson gson;
    private StringConvert convert;

    public JsonCallback(Class<T> clazz) {
        convert = new StringConvert();
        gson = new GsonBuilder().create();
        this.clazz = clazz;
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        String s = convert.convertResponse(response);
        response.close();
        return gson.fromJson(s, clazz);
    }
}
