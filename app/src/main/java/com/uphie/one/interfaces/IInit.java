package com.uphie.one.interfaces;

import com.uphie.one.common.HttpData;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public interface IInit {
    int getLayoutId();

    HttpData getDataStructure();

    void init();
}
