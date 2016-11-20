/**
 *  Copyright 2016 SuperHighway Labs (P) Limited . All Rights Reserved.
 *  SUPERHIGHWAY LABS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jitesh.ratelimiter.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0, 19-Nov-2016
 * @author jitesh
 */
public class Channel {
    private Map<String, RateLimiter> apiLimitMap = new HashMap<>();

    public void addApiLimit(String key, RateLimiter limiter) {
        this.apiLimitMap.put(key, limiter);
    }

    public RateLimiter getLimiterByKey(String key) {
        return apiLimitMap.get(key);
    }

}
