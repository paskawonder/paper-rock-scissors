package com.imc.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MessageQueue {

    public static final String TOPIC_GAME_SINGLE_PLAYER_MOVE = "GAME_SINGLE_PLAYER_MOVE";

    private final Map<String, Topic<?>> topicMap = new HashMap<>();

    public void addTopic(String name) {
        topicMap.put(name, new Topic<>());
    }

    @SuppressWarnings("unchecked")
    public <T> void addMsg(String topic, T msg) {
        Topic<T> t = (Topic<T>) topicMap.get(topic);
        t.add(msg);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> readMsgs(String topic, String consumerId) {
        Topic<T> t = (Topic<T>) topicMap.get(topic);
        return t.get(consumerId);
    }

    public static final class Topic<T> {

        private final List<T> messages = new ArrayList<>();

        private final Map<String, Integer> consumerOffset = new HashMap<>();

        public void add(T message) {
            messages.add(message);
        }

        public List<T> get(String consumerId) {
            int offset = consumerOffset.getOrDefault(consumerId, 0);
            if (offset == messages.size()) {
                return Collections.emptyList();
            }
            int nextOffset = messages.size();
            consumerOffset.put(consumerId, nextOffset);
            return messages.subList(offset, nextOffset);
        }

    }

}
