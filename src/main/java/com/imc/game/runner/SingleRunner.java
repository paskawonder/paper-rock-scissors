package com.imc.game.runner;

import com.imc.game.Move;
import com.imc.game.dto.PlayerIdMove;
import com.imc.game.player.Player;
import com.imc.queue.MessageQueue;
import java.util.Map;
import java.util.logging.Logger;

public final class SingleRunner {

    private static final Logger LOGGER = Logger.getLogger(SingleRunner.class.getName());

    private final MessageQueue messageQueue;

    public SingleRunner(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    private static final Map<Move, Move> WINNING_COMBINATION = Map.of(
            Move.ROCK, Move.SCISSORS,
            Move.PAPER, Move.ROCK,
            Move.SCISSORS, Move.PAPER
    );

    public String play(Player player1, Player player2) {
        Move move1 = null, move2 = null;
        while (move1 == move2) {
            move1 = player1.nextMove();
            move2 = player2.nextMove();
            messageQueue.addMsg(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, new PlayerIdMove(player1.getId(), move1));
            messageQueue.addMsg(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, new PlayerIdMove(player2.getId(), move2));
            player1.notifyOfMove();
            player2.notifyOfMove();
            if (move1 == move2) {
                LOGGER.info("Tie; replay");
            }
        }
        return WINNING_COMBINATION.get(move1) == move2 ? player1.getId() : player2.getId();
    }

}
