package com.training.task3;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MessageFlow {

    private static final int TOPICS_NUMBER = 3;

    public void go() throws InterruptedException {

        Map<String, Queue<Integer>> messageBus = new HashMap<>();
        Map<TopicBus, List<Thread>> topics = new HashMap<>();

        for (int i = 0; i < TOPICS_NUMBER; i++) {
            String topicName = "topic" + i;
            messageBus.put(topicName, new PriorityQueue<>());
            TopicBus topicBus = new TopicBus(messageBus, topicName);
            Thread producer = new Thread(topicBus::produce);
            Thread consumer = new Thread(topicBus::consume);
            List<Thread> temp = new ArrayList<>();
            temp.add(0, producer);
            temp.add(1, consumer);
            topics.put(topicBus, temp);
            producer.start();
            consumer.start();
/*            Arrays.asList(new Thread(topicBus::produce), new Thread(topicBus::consume))
                    .forEach(Thread::start);*/

        }

        Thread.sleep(20);

        for (List<Thread> list: topics.values()
             ) {
            list.forEach(Thread::interrupt);
        }
    }
}
