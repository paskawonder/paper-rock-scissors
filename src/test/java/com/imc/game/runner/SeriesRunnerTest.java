package com.imc.game.runner;

import com.imc.game.model.Player;
import com.imc.game.model.SeriesOutcome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SeriesRunnerTest {

    private static final String PLAYER_1_ID = "PLAYER_1";
    private static final String PLAYER_2_ID = "PLAYER_2";

    private final Player player1;
    private final Player player2;
    private final SingleRunner singleRunner;
    private final SeriesRunner subject;

    SeriesRunnerTest() {
        player1 = Mockito.mock(Player.class);
        player2 = Mockito.mock(Player.class);
        Mockito.when(player1.getId()).thenReturn(PLAYER_1_ID);
        Mockito.when(player2.getId()).thenReturn(PLAYER_2_ID);
        this.singleRunner = Mockito.mock(SingleRunner.class);
        this.subject = new SeriesRunner(singleRunner);
    }

    @Test
    void playTestWinner() {
        byte n = 1;
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID, PLAYER_2_ID);
        Assertions.assertEquals(new SeriesOutcome(n, PLAYER_1_ID), subject.play(n, player1, player2));
        Assertions.assertEquals(new SeriesOutcome(n, PLAYER_2_ID), subject.play(n, player1, player2));
        Mockito.verify(singleRunner, Mockito.times(n + n)).play(player1, player2);
    }

    @Test
    void playTestDraw() {
        byte n = 2;
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID, PLAYER_2_ID);
        Assertions.assertEquals(new SeriesOutcome(n, null), subject.play(n, player1, player2));
        Mockito.verify(singleRunner, Mockito.times(n)).play(player1, player2);
        Mockito.reset(singleRunner);
        n = 10;
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID, PLAYER_1_ID, PLAYER_1_ID, PLAYER_1_ID, PLAYER_2_ID, PLAYER_1_ID, PLAYER_2_ID);
        Assertions.assertEquals(new SeriesOutcome(n, null), subject.play(n, player1, player2));
        Mockito.verify(singleRunner, Mockito.times(n)).play(player1, player2);
        Mockito.reset(singleRunner);
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID, PLAYER_1_ID, PLAYER_2_ID, PLAYER_1_ID, PLAYER_2_ID, PLAYER_2_ID, PLAYER_2_ID, PLAYER_2_ID, PLAYER_1_ID, PLAYER_1_ID);
        Assertions.assertEquals(new SeriesOutcome(n, null), subject.play(n, player1, player2));
        Mockito.verify(singleRunner, Mockito.times(n)).play(player1, player2);
    }

    @Test
    void playTestInvalidArguments() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.play((byte) 0, player1, player2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.play((byte) 1, null, player2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.play((byte) 1, player1, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.play((byte) 1, Mockito.mock(Player.class), Mockito.mock(Player.class)));
    }

    @Test
    void playTestEarlyWinner() {
        byte n = 5;
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID, PLAYER_1_ID, PLAYER_1_ID);
        Assertions.assertEquals(new SeriesOutcome((byte) 3, PLAYER_1_ID), subject.play(n, player1, player2));
        Mockito.verify(singleRunner, Mockito.times(3)).play(player1, player2);
        Mockito.reset(singleRunner);
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID, PLAYER_1_ID, PLAYER_2_ID, PLAYER_1_ID);
        Assertions.assertEquals(new SeriesOutcome((byte) 4, PLAYER_1_ID), subject.play(n, player1, player2));
        Mockito.verify(singleRunner, Mockito.times(4)).play(player1, player2);
        Mockito.reset(singleRunner);
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID, PLAYER_1_ID, PLAYER_2_ID, PLAYER_1_ID, PLAYER_2_ID);
        Assertions.assertEquals(new SeriesOutcome((byte) 9, PLAYER_2_ID), subject.play((byte) 10, player1, player2));
        Mockito.verify(singleRunner, Mockito.times(9)).play(player1, player2);
        Mockito.reset(singleRunner);
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_2_ID, PLAYER_2_ID, PLAYER_1_ID, PLAYER_1_ID, PLAYER_2_ID);
        Assertions.assertEquals(new SeriesOutcome((byte) 8, PLAYER_2_ID), subject.play((byte) 10, player1, player2));
        Mockito.verify(singleRunner, Mockito.times(8)).play(player1, player2);
    }

}
