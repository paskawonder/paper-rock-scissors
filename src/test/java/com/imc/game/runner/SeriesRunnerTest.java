package com.imc.game.runner;

import com.imc.game.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;

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
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID);
        Assertions.assertEquals(Optional.of(PLAYER_1_ID), subject.play((byte) 1, player1, player2));
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_2_ID);
        Assertions.assertEquals(Optional.of(PLAYER_2_ID), subject.play((byte) 1, player1, player2));
    }

    @Test
    void playTestDraw() {
        Mockito.when(singleRunner.play(player1, player2)).thenReturn(PLAYER_1_ID).thenReturn(PLAYER_2_ID);
        Assertions.assertEquals(Optional.empty(), subject.play((byte) 2, player1, player2));
    }

    @Test
    void playTestInvalidArguments() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.play((byte) 0, player1, player2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.play((byte) 1, null, player2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.play((byte) 1, player1, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> subject.play((byte) 1, Mockito.mock(Player.class), Mockito.mock(Player.class)));
    }

}
