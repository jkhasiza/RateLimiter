/**
 *  Copyright 2016 SuperHighway Labs (P) Limited . All Rights Reserved.
 *  SUPERHIGHWAY LABS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.jitesh.ratelimiter.service;

/**
 *  
 *  @version     1.0, 19-Nov-2016
 *  @author jitesh
 */
public interface KeyValidatorService {

    boolean isKeyValid(long reqTime, String apiKey);

}
