package com.wzluo.util;

/**
 * 高并发同步类
 */

import java.util.concurrent.atomic.LongAdder;

public class LongModCounter{
    private LongAdder adder;
    private int mod;

    public LongModCounter(int mod) {
        this.adder = new LongAdder();
        this.mod = mod;
    }
    public int get(){
        return this.adder.intValue();
    }
    public synchronized int getAndIncrease(){
            int val=adder.intValue();
            int val1=val+1;
            if(val1>=mod){
                adder.reset();
            }else {
                adder.increment();
            }
            return val;
    }
}
