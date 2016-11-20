package com.jitesh.ratelimiter.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @version 1.0, 19-Nov-2016
 * @author jitesh
 */
public class RateLimiter {
    private int        noOfRequests;
    private int        timeInSecs;
    private long       lastBlockTime;
    private List<Long> callHistory = new LinkedList<>();

    public int getNoOfRequests() {
        return noOfRequests;
    }

    public void setNoOfRequests(int noOfRequests) {
        this.noOfRequests = noOfRequests;
    }

    public int getTimeInSecs() {
        return timeInSecs;
    }

    public void setTimeInSecs(int timeInSecs) {
        this.timeInSecs = timeInSecs;
    }

    public List<Long> getCallHistory() {
        return callHistory;
    }

    public void addCallHistory(Long reqTime) {
        this.callHistory.add(reqTime);
    }

    public long getLastBlockTime() {
        return lastBlockTime;
    }

    public void setLastBlockTime(long lastBlockTime) {
        this.lastBlockTime = lastBlockTime;
    }

    public void deleteOld(long currTime) {
        Iterator<Long> iterator = callHistory.iterator();
        while (iterator.hasNext()) {
            long accessedTime = iterator.next();
            if ((currTime - accessedTime) >= (timeInSecs * Constants.MILLI_SECS_IN_SECS)) {
                iterator.remove();
            } else {
                break;
            }
        }
        if ((currTime - this.lastBlockTime) >= (timeInSecs * Constants.MILLI_SECS_IN_SECS) && (callHistory.size() >= noOfRequests)) {
            this.lastBlockTime = currTime;
        }
    }

}
