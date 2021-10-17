package com.training.task4;

import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;
import java.util.Queue;

@Slf4j
public class BlockingObjectPool {

    private final Queue<Object> pool;
    private final int size;

    public BlockingObjectPool(int size) {
        this.size = size;
        this.pool = new PriorityQueue<>();
    }

    //Gets object from pool or blocks if pool is empty
    public synchronized Object get() {
        if (pool.isEmpty()) {
            try {
                log.info("Pool is empty. Get is waiting");
                this.wait();
            } catch (InterruptedException e) {
                log.error("InterruptedException:{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        Object object = pool.poll();
        log.info("Get object: {}", object);
        if (pool.size() == size - 1) {
            this.notifyAll();
        }
        return object;
    }

    //Puts object to pool or blocks if pool is full
    public synchronized void take(Object object) {
        int capacity = pool.size();
        if (capacity == size) {
            try {
                log.info("Size : {}, Put is waiting", capacity);
                this.wait();
            } catch (InterruptedException e) {
                log.error("InterruptedException:{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        log.info("Put object: {}", object);
        pool.add(object);
        if (pool.size() == 1) {
            this.notifyAll();
        }
    }

}
