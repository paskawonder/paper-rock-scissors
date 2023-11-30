package com.imc.game.dto;

import com.imc.game.Move;
import java.util.Objects;

public record PlayerIdMove(String playerId, Move move) {

}
