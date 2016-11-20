/**
 *  Copyright 2016 SuperHighway Labs (P) Limited . All Rights Reserved.
 *  SUPERHIGHWAY LABS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jitesh.ratelimiter.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jitesh.ratelimiter.utils.Channel;
import com.jitesh.ratelimiter.utils.Constants;
import com.jitesh.ratelimiter.utils.RateLimiter;

/**
 * @version 1.0, 19-Nov-2016
 * @author jitesh
 */
@Service
public class KeyValidatorServiceImpl implements KeyValidatorService {
    private static final Logger LOG = LoggerFactory.getLogger(KeyValidatorServiceImpl.class);
    private Channel             channel;

    @Value("${default.request}")
    private Integer             defaultRequestCount;

    @Value("${default.timeinSecs}")
    private Integer             timeInSecs;

    @Value("${apikey.csv.file}")
    private String              filePath;

    /**
     * This function is called on bean initialization i.e. application startup. It will populate content of csv file of
     * api list to memory.
     */
    @PostConstruct
    public void init() {
        Channel channel = new Channel();
        RateLimiter limiter = new RateLimiter();
        limiter.setTimeInSecs(timeInSecs);
        limiter.setNoOfRequests(defaultRequestCount);
        limiter.setLastBlockTime(0);
        channel.addApiLimit(Constants.DEFAULT_KEY, limiter);
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(filePath));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                RateLimiter rL = new RateLimiter();
                String key = row[0];
                rL.setTimeInSecs(Integer.parseInt(row[2]));
                rL.setNoOfRequests(Integer.parseInt(row[1]));
                rL.setLastBlockTime(0);
                channel.addApiLimit(key, rL);
            }
        } catch (FileNotFoundException e) {
            LOG.error("File not found at {}", filePath);
        } catch (IOException e) {
            LOG.error("IOException occured ", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.channel = channel;
    }

    @Override
    public boolean isKeyValid(long reqTime, String apiKey) {
        RateLimiter limiter = this.channel.getLimiterByKey(apiKey);
        if (limiter == null) {
            limiter = this.channel.getLimiterByKey(Constants.DEFAULT_KEY);
        }
        if (limiter != null) {
            limiter.deleteOld(reqTime);
            if ((reqTime - limiter.getLastBlockTime()) <= 5 * Constants.MILLI_SECS_IN_MINS) {
                return false;
            }
            limiter.addCallHistory(reqTime);
            return true;
        }
        return false;
    }
}
