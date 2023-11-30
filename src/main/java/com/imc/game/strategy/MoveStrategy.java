package com.imc.game.strategy;

import com.imc.game.model.Move;

public interface MoveStrategy {

    void consumeGameMoves(String playerId);

    Move getNextMove(String playerId);

}
