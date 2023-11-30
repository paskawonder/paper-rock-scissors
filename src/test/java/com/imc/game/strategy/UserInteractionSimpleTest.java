package com.imc.game.strategy;

import com.imc.game.model.Move;
import com.imc.game.model.PlayerIdMove;
import com.imc.queue.MessageQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Scanner;

class UserInteractionSimpleTest {

    private static final String PLAYER_ID = "PLAYER_ID";

    private final MessageQueue messageQueue;
    private final Scanner scanner;
    private final UserInteractionSimple subject;

    UserInteractionSimpleTest() {
        this.messageQueue = Mockito.mock(MessageQueue.class);
        this.scanner = Mockito.mock(Scanner.class);
        this.subject = new UserInteractionSimple(messageQueue, scanner, null);
    }

    @Test
    void consumeGameMovesTest() {
        Mockito.when(messageQueue.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, PLAYER_ID)).thenReturn(List.of(
                new PlayerIdMove(PLAYER_ID, Move.ROCK), new PlayerIdMove("1", Move.PAPER)
        ));
        subject.consumeGameMoves(PLAYER_ID);
        Mockito.when(messageQueue.readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, PLAYER_ID)).thenReturn(List.of(
                new PlayerIdMove(PLAYER_ID, Move.ROCK), new PlayerIdMove("1", Move.PAPER), new PlayerIdMove("1", Move.PAPER)
        ));
        Assertions.assertThrows(IllegalStateException.class, () -> subject.consumeGameMoves(PLAYER_ID));
    }

    @Test
    void getNextMoveTest() {
        Mockito.when(scanner.nextLine()).thenReturn("1");
        Assertions.assertSame(Move.ROCK, subject.getNextMove(PLAYER_ID));
        Mockito.when(scanner.nextLine()).thenReturn("2");
        Assertions.assertSame(Move.PAPER, subject.getNextMove(PLAYER_ID));
        Mockito.when(scanner.nextLine()).thenReturn("3");
        Assertions.assertSame(Move.SCISSORS, subject.getNextMove(PLAYER_ID));
        Mockito.when(scanner.nextLine()).thenReturn("4");
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.getNextMove(PLAYER_ID));
    }

}
