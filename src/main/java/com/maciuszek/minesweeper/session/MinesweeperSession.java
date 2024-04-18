package com.maciuszek.minesweeper.session;

import com.maciuszek.minesweeper.configuration.MinesweeperConfiguration;
import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MinesweeperSession {

    private final MinesweeperBoard minesweeperBoard; // single in-memory game session

    public MinesweeperSession(MinesweeperConfiguration minesweeperConfiguration) {
        minesweeperBoard = new MinesweeperBoard(minesweeperConfiguration.getBoardSize(), minesweeperConfiguration.getBombCount());
    }

}
