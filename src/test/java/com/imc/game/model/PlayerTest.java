package com.imc.game.model;

import com.imc.game.strategy.MoveStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerTest {

    private static final String PLAYER_ID = "PLAYER_ID";

    private final MoveStrategy moveStrategy;
    private final Player subject;

    PlayerTest() {
        this.moveStrategy = Mockito.mock(MoveStrategy.class);
        this.subject = new Player(PLAYER_ID, moveStrategy);
    }

    @Test
    void nextMoveTest() {
        Mockito.when(moveStrategy.getNextMove(PLAYER_ID)).thenReturn(Move.ROCK);
        Assertions.assertEquals(Move.ROCK, subject.nextMove());
    }

    @Test
    void notifyOfMoveTest() {
        subject.notifyOfMove();
        Mockito.verify(moveStrategy, Mockito.times(1)).consumeGameMoves(PLAYER_ID);
    }

}
