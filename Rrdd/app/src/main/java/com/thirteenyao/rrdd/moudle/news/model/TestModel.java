package com.thirteenyao.rrdd.moudle.news.model;

import com.thirteenyao.rrdd.base.net.IBaseListener;
import com.thirteenyao.rrdd.moudle.news.model.impl.TestModelImpl;

import rx.Subscription;

/**
 * Created by ThirteenYao on 2017/4/17.
 */

public interface TestModel {
    Subscription test(String city, TestModelImpl.TestListener listener);
}
