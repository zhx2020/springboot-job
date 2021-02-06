package com.zwl.job;

import java.util.Random;

public class MessageTest {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(900000) + 100000);
        }
    }
}
