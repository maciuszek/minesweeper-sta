package com.maciuszek.minesweeper.session;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Store the game sessions
 */
@Component
@Getter
public class MinesweeperSession {

    public static final int BOARD_SIZE = 10;
    public static final int BOMB_COUNT = 22;

    private final MinesweeperBoard minesweeperBoard = new MinesweeperBoard();

}
