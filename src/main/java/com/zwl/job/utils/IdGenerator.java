package com.zwl.job.utils;

import com.github.wujun234.uid.impl.CachedUidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    @Autowired
    private CachedUidGenerator cachedUidGenerator;

    // 获取uid
    public long nextId() {
        return cachedUidGenerator.getUID();
    }

    // 格式化传入的uid，方便查看其实际含义
    public String parse(long uid) {
        return cachedUidGenerator.parseUID(uid);
    }
}
