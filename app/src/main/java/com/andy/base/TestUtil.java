/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixn on 2017/1/6.
 */

public class TestUtil {
    public static List<String> getData(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("item-->"+(i));
        }
        return list;
    }
}
