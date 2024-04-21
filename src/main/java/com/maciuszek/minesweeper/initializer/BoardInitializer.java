package com.maciuszek.minesweeper.initializer;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardInitializer implements CommandLineRunner {

    private final MinesweeperSession minesweeperSession;

    // create a new game session on spring start
    public void run(String... args) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        minesweeperBoard.setMinesweeperCells(new MinesweeperCell[minesweeperBoard.getBoardHeight()][minesweeperBoard.getBoardWidth()]);

        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        for (int i = 0; i < minesweeperBoard.getBoardHeight(); i++) {
            for (int y = 0; y < minesweeperBoard.getBoardWidth(); y++) {
                minesweeperCells[i][y] = new MinesweeperCell();
            }
        }

        minesweeperBoard.setStatus(MinesweeperBoard.Status.NOT_INITIALIZED);
    }

}
