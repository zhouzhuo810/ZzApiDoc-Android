package me.zhouzhuo810.zzapidoc.common.rule;

import java.util.List;

/**
 * Created by admin on 2017/8/14.
 */

public interface ISearch<T> {
    void startSearch(String content);

    void cancelSearch(List<T> list);

}
