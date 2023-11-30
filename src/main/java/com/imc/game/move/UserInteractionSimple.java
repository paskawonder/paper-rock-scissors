package com.imc.game.move;

import com.imc.game.Move;
import com.imc.game.dto.PlayerIdMove;
import com.imc.queue.MessageQueue;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInteractionSimple implements MoveStrategy {

    private static final Logger LOGGER = Logger.getLogger(UserInteractionSimple.class.getName());

    private final MessageQueue messageQueue;
    private final Scanner scanner;
    private final String yourTurnDisplayMessage;

    public UserInteractionSimple(MessageQueue messageQueue, Scanner scanner, String yourTurnDisplayMessage) {
        this.messageQueue = messageQueue;
        this.scanner = scanner;
        this.yourTurnDisplayMessage = yourTurnDisplayMessage;
    }

    @Override
    public void consumeGameMoves(String playerId) {
        List<Move> otherPlayersMoves = messageQueue
                .<PlayerIdMove>readMsgs(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE, playerId)
                .stream().filter(pm -> !pm.playerId().equals(playerId)).map(PlayerIdMove::move).toList();
        if (otherPlayersMoves.size() != 1) {
            throw new IllegalStateException("A player " + playerId + "is out of sync!");
        }
        LOGGER.log(Level.INFO, "Opponent went {0}", otherPlayersMoves);
    }

    @Override
    public Move getNextMove(String playerId) {
        LOGGER.log(Level.INFO, yourTurnDisplayMessage, playerId);
        return switch (scanner.nextLine()) {
            case "1" -> Move.ROCK;
            case "2" -> Move.PAPER;
            case "3" -> Move.SCISSORS;
            default -> throw new IllegalArgumentException();
        };
    }

}
