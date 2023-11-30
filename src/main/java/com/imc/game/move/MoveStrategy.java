package com.imc.game.move;

import com.imc.game.Move;

public interface MoveStrategy {

    void consumeGameMoves(String playerId);

    Move getNextMove(String playerId);

}
