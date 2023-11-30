package com.imc.game.strategy;

import com.imc.game.model.Move;
import com.imc.game.model.PlayerIdMove;
import com.imc.queue.MessageQueue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AIEngine implements MoveStrategy {

    private final Map<Move, Integer> movesFreq = new HashMap<>();
    private Move nextMove;
    private final MessageQueue messageQueue;

    public AIEngine(Move defaultMove, MessageQueue messageQueue) {
        this.nextMove = defaultMove;
        movesFreq.put(nextMove, 0);
        this.messageQueue = messageQueue;
    }

    @Override
    public void consumeGameMoves(String playerId) {
        List<Move> otherPlayersMoves = messageQueue
                .<PlayerIdMove>readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, playerId)
                .stream().filter(pm -> !pm.playerId().equals(playerId)).map(PlayerIdMove::move).toList();
        movesFreq.compute(nextMove, (k, v) -> v + (int) otherPlayersMoves.stream().filter(m -> nextMove == m).count());
        for (Move move: otherPlayersMoves.stream().filter(m -> nextMove != m).toList()) {
            movesFreq.put(move, movesFreq.getOrDefault(move, 0) + 1);
            if (movesFreq.get(move) > movesFreq.get(nextMove)) {
                nextMove = move;
            }
        }
    }

    @Override
    public Move getNextMove(String playerId) {
        return nextMove;
    }

}
