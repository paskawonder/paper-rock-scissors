package com.imc.game;

import com.imc.game.model.Move;
import com.imc.game.model.Player;
import com.imc.game.model.SeriesOutcome;
import com.imc.game.strategy.AIEngine;
import com.imc.game.strategy.MoveStrategy;
import com.imc.game.strategy.UserInteractionSimple;
import com.imc.game.runner.SingleRunner;
import com.imc.game.runner.SeriesRunner;
import com.imc.queue.MessageQueue;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws IOException {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s %n");
        Properties properties = new Properties();
        properties.load(Application.class.getClassLoader().getResourceAsStream("config.properties"));
        LOGGER.info(properties.getProperty("game.greeting"));
        MessageQueue messageQueue = configureMessageQueue();
        Scanner scanner = new Scanner(System.in);
        MoveStrategy simple = new UserInteractionSimple(messageQueue, scanner, properties.getProperty("game.user.your.turn.display.message"));
        Player user = new Player(scanner.nextLine(), simple);
        Move aiDefaultMove = Move.valueOf(properties.getProperty("game.ai.move.default"));
        MoveStrategy aiEngine = new AIEngine(aiDefaultMove, messageQueue);
        Player computer = new Player(properties.getProperty("game.ai.player.name"), aiEngine);
        SeriesRunner seriesRunner = new SeriesRunner(new SingleRunner(messageQueue));
        SeriesOutcome outcome = seriesRunner.play(Byte.parseByte(args[0]), user, computer);
        LOGGER.log(Level.INFO, "The Games Series finished after {0} game{1}", new Object[] {outcome.gamesPlayed(), outcome.gamesPlayed() == 1 ? "" : "s"});
        LOGGER.log(Level.INFO, "The Final Result is {0}", outcome.getWinnerId().map(id -> "Victory of " + id).orElse("Draw"));
    }

    private static MessageQueue configureMessageQueue() {
        MessageQueue messageQueue = new MessageQueue();
        messageQueue.addTopic(MessageQueue.TOPIC_GAME_SINGLE_PLAYER_MOVE);
        return messageQueue;
    }

}
