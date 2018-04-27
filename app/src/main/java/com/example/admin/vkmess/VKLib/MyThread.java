package com.example.admin.vkmess.VKLib;

import java.util.concurrent.BlockingQueue;


public class MyThread extends Thread{
    BlockingQueue<Runnable> request;

    MyThread(BlockingQueue<Runnable> request){
        this.request = request;
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                request.take().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
