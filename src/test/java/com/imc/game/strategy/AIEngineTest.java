package com.imc.game.strategy;

import com.imc.game.model.Move;
import com.imc.game.model.PlayerIdMove;
import com.imc.queue.MessageQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

class AIEngineTest {

    private static final String PLAYER_ID = "PLAYER_ID";

    private final MessageQueue messageQueue;
    private final AIEngine subject;

    AIEngineTest() {
        this.messageQueue = Mockito.mock(MessageQueue.class);
        this.subject = new AIEngine(Move.SCISSORS, messageQueue);
    }

    @Test
    void consumeGameMovesGetNextMoveTest() {
        Assertions.assertSame(Move.SCISSORS, subject.getNextMove(PLAYER_ID));
        Mockito.when(messageQueue.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, PLAYER_ID)).thenReturn(
                List.of(new PlayerIdMove(PLAYER_ID, Move.ROCK), new PlayerIdMove(PLAYER_ID, Move.ROCK), new PlayerIdMove("1", Move.PAPER), new PlayerIdMove("2", Move.SCISSORS)),
                List.of(new PlayerIdMove(PLAYER_ID, Move.ROCK), new PlayerIdMove(PLAYER_ID, Move.ROCK), new PlayerIdMove("2", Move.PAPER)),
                List.of(new PlayerIdMove("1", Move.ROCK), new PlayerIdMove("1", Move.ROCK)),
                List.of(new PlayerIdMove("1", Move.ROCK), new PlayerIdMove("2", Move.ROCK), new PlayerIdMove("3", Move.PAPER))
        );
        subject.consumeGameMoves(PLAYER_ID);
        Assertions.assertSame(Move.SCISSORS, subject.getNextMove(PLAYER_ID));
        subject.consumeGameMoves(PLAYER_ID);
        Assertions.assertSame(Move.PAPER, subject.getNextMove(PLAYER_ID));
        subject.consumeGameMoves(PLAYER_ID);
        Assertions.assertSame(Move.PAPER, subject.getNextMove(PLAYER_ID));
        subject.consumeGameMoves(PLAYER_ID);
        Assertions.assertSame(Move.ROCK, subject.getNextMove(PLAYER_ID));
    }

}
