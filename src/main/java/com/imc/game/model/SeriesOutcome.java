package com.imc.game.model;

import java.util.Optional;

public record SeriesOutcome(byte gamesPlayed, String winnerId) {

    public Optional<String> getWinnerId() {
        return Optional.ofNullable(winnerId);
    }

}
