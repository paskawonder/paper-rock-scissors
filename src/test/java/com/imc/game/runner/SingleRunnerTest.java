package com.imc.game.runner;

import com.imc.game.model.Move;
import com.imc.game.model.PlayerIdMove;
import com.imc.game.model.Player;
import com.imc.queue.MessageQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SingleRunnerTest {

    private static final String PLAYER_1_ID = "PLAYER_1";
    private static final String PLAYER_2_ID = "PLAYER_2";

    private final Player player1;
    private final Player player2;
    private final MessageQueue messageQueue;
    private final SingleRunner subject;

    SingleRunnerTest() {
        player1 = Mockito.mock(Player.class);
        player2 = Mockito.mock(Player.class);
        Mockito.when(player1.getId()).thenReturn(PLAYER_1_ID);
        Mockito.when(player2.getId()).thenReturn(PLAYER_2_ID);
        this.messageQueue = Mockito.mock(MessageQueue.class);
        this.subject = new SingleRunner(messageQueue);
    }

    @Test
    void playTest() {
        Mockito.when(player1.nextMove()).thenReturn(Move.SCISSORS, Move.ROCK);
        Mockito.when(player2.nextMove()).thenReturn(Move.SCISSORS, Move.SCISSORS);
        Assertions.assertEquals(PLAYER_1_ID, subject.play(player1, player2));
        Mockito.verify(player1, Mockito.times(2)).nextMove();
        Mockito.verify(player2, Mockito.times(2)).nextMove();
        Mockito.verify(messageQueue, Mockito.times(1)).addMsg(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, new PlayerIdMove(PLAYER_1_ID, Move.SCISSORS));
        Mockito.verify(messageQueue, Mockito.times(2)).addMsg(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, new PlayerIdMove(PLAYER_2_ID, Move.SCISSORS));
        Mockito.verify(messageQueue, Mockito.times(1)).addMsg(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, new PlayerIdMove(PLAYER_1_ID, Move.ROCK));
    }

    @Test
    void playTestRSSR() {
        Mockito.when(player1.nextMove()).thenReturn(Move.ROCK, Move.SCISSORS);
        Mockito.when(player2.nextMove()).thenReturn(Move.SCISSORS, Move.ROCK);
        Assertions.assertEquals(PLAYER_1_ID, subject.play(player1, player2));
        Assertions.assertEquals(PLAYER_2_ID, subject.play(player1, player2));
        Mockito.verify(player1, Mockito.times(2)).nextMove();
        Mockito.verify(player2, Mockito.times(2)).nextMove();
    }

    @Test
    void playTestPRRP() {
        Mockito.when(player1.nextMove()).thenReturn(Move.PAPER, Move.ROCK);
        Mockito.when(player2.nextMove()).thenReturn(Move.ROCK, Move.PAPER);
        Assertions.assertEquals(PLAYER_1_ID, subject.play(player1, player2));
        Assertions.assertEquals(PLAYER_2_ID, subject.play(player1, player2));
        Mockito.verify(player1, Mockito.times(2)).nextMove();
        Mockito.verify(player2, Mockito.times(2)).nextMove();
    }

    @Test
    void playTestSPPS() {
        Mockito.when(player1.nextMove()).thenReturn(Move.SCISSORS, Move.PAPER);
        Mockito.when(player2.nextMove()).thenReturn(Move.PAPER, Move.SCISSORS);
        Assertions.assertEquals(PLAYER_1_ID, subject.play(player1, player2));
        Assertions.assertEquals(PLAYER_2_ID, subject.play(player1, player2));
        Mockito.verify(player1, Mockito.times(2)).nextMove();
        Mockito.verify(player2, Mockito.times(2)).nextMove();
    }

}
