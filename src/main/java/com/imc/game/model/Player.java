package com.imc.game.model;

import com.imc.game.strategy.MoveStrategy;
import java.util.Objects;

public final class Player {

    private final String id;
    private final MoveStrategy moveStrategy;

    public Player(String id, MoveStrategy moveStrategy) {
        this.id = id;
        this.moveStrategy = moveStrategy;
    }

    public String getId() {
        return id;
    }

    public Move nextMove() {
        return moveStrategy.getNextMove(id);
    }

    public void notifyOfMove() {
        moveStrategy.consumeGameMoves(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(moveStrategy, player.moveStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, moveStrategy);
    }

}
