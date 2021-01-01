package com.zwl.job.controller;

import java.util.Date;

public class MyRunnable implements Runnable {
    public void run() {
        System.out.println("MyRunnable.run()，" + new Date());
    }
}
