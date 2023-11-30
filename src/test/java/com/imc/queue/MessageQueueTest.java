package com.imc.queue;

import com.imc.game.model.PlayerIdMove;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MessageQueueTest {

    @Test
    void addTopicTest() {
        MessageQueue subject = new MessageQueue();
        subject.addTopic(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE);
        Assertions.assertEquals(Collections.emptyList(), subject.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, ""));
    }

    @Test
    void addMsgReadMsgsTest() {
        MessageQueue subject = new MessageQueue();
        subject.addTopic(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE);
        List<PlayerIdMove> list = List.of(
                Mockito.mock(PlayerIdMove.class), Mockito.mock(PlayerIdMove.class), Mockito.mock(PlayerIdMove.class), Mockito.mock(PlayerIdMove.class)
        );
        list.forEach(e -> subject.addMsg(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, e));
        Assertions.assertEquals(list, subject.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, "0"));
        Assertions.assertEquals(Collections.emptyList(), subject.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, "0"));
        Assertions.assertEquals(list, subject.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, "2"));
        List<PlayerIdMove> list2 = List.of(
                Mockito.mock(PlayerIdMove.class), Mockito.mock(PlayerIdMove.class), Mockito.mock(PlayerIdMove.class)
        );
        list2.forEach(e -> subject.addMsg(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, e));
        Assertions.assertEquals(list2, subject.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, "0"));
        Assertions.assertEquals(list2, subject.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, "2"));
        List<PlayerIdMove> list3 = new ArrayList<>(list);
        list3.addAll(list2);
        Assertions.assertEquals(list3, subject.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, "3"));
    }

}
