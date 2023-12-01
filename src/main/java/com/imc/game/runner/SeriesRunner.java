package com.imc.game.runner;

import com.imc.game.model.Player;
import com.imc.game.model.SeriesOutcome;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SeriesRunner {

    private static final Logger LOGGER = Logger.getLogger(SeriesRunner.class.getName());

    private final SingleRunner singleRunner;

    public SeriesRunner(SingleRunner singleRunner) {
        this.singleRunner = singleRunner;
    }

    public SeriesOutcome play(byte numberOfGames, Player player1, Player player2) {
        validateArgs(numberOfGames, player1, player2);
        byte[] table = {0, 0};
        byte playedGames = 0;
        while (playedGames < numberOfGames && Math.abs(table[0] - table[1]) <= numberOfGames - playedGames) {
            LOGGER.log(Level.INFO, "Game number {0}", playedGames);
            String winnerId = singleRunner.play(player1, player2);
            LOGGER.log(Level.INFO, "Game number {0} winner is Player {1}", new Object[] {playedGames, winnerId});
            table[winnerId.equals(player1.getId()) ? 0 : 1]++;
            playedGames++;
        }
        String winnerId = null;
        if (table[0] - table[1] != 0) {
            winnerId = table[0] > table[1] ? player1.getId() : player2.getId();
        }
        return new SeriesOutcome(playedGames, winnerId);
    }

    private void validateArgs(byte numberOfGames, Player player1, Player player2) {
        if (numberOfGames < 1) {
            throw new IllegalArgumentException("Invalid number of games to play");
        } else if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Non-existing player");
        } else if (Objects.equals(player1.getId(), player2.getId())) {
            throw new IllegalArgumentException("Players Ids collision");
        }
    }

}
