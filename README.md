# Paper-Rock-Scissors game

## Game Rules
The winner of a single game is determined by the following schema:
1. paper beats (wraps) rock
2. rock beats (blunts) scissors
3. scissors beats (cuts) paper
4. matching combination on both sides leads to a replay; the number of possible replays is indefinite

The outcome of the series can be:
1. victory of one of the participants
2. parity if both players hold an equal number of wins

### Play
To start a series of games, build the application and run it, passing a desired number of games as the command line argument.

1. mvn package
2. java -jar target/game-1.0.jar {{number_of_games}}

### Version 1.0
* at most 127 games within the same series
* single-player mode against the computer AI
* the computer side does not cheat

### Design Thoughts
Conventionally, players immediately and simultaneously observe each other's hands.
However, players can be distant with a referee located halfway. In this case, it will take time to recognize the opponent's move and the game outcome may be heard first.
Players only need to synchronously make their moves. They can be asynchronously notified of the opponents' hand and results.
A game can publish moves and results, and players process them up to their speed. 
The quickest learner will benefit from learning the pattern, one who checks the result every ten minutes will lag but will save on the costly thinking process.
The same applies to computers and distributed systems.
