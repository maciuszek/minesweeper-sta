package com.maciuszek.minesweeper.initializer;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.helper.MinesweeperHelper;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardInitializer implements CommandLineRunner {

    private final MinesweeperSession minesweeperSession;

    // initialize a new game session on spring start
    public void run(String... args) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        minesweeperBoard.setMinesweeperCells(new MinesweeperCell[minesweeperBoard.getBoardSize()][minesweeperBoard.getBoardSize()]);

        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        for (int i = 0; i < minesweeperBoard.getBoardSize(); i++) {
            for (int y = 0; y < minesweeperBoard.getBoardSize(); y++) {
                minesweeperCells[i][y] = new MinesweeperCell();
            }
        }

        int bombsToAdd = minesweeperBoard.getBombCount();
        while (bombsToAdd > 0) {
            int randomRow = (int) (Math.random() * (minesweeperBoard.getBoardSize() - 1));
            int randomColumn = (int) (Math.random() * (minesweeperBoard.getBoardSize()- 1));
            MinesweeperCell minesweeperCell = minesweeperCells[randomRow][randomColumn];
            if (!minesweeperCell.isBomb()) {
                minesweeperCell.setBomb(true);
                --bombsToAdd;
                for (MinesweeperCell surroundCell : MinesweeperHelper.getSurroundingCells(minesweeperBoard, randomRow, randomColumn)) {
                    surroundCell.incSurroundingBombCount();
                }
            }
        }

        minesweeperBoard.setMinesweeperCells(minesweeperCells);

        if (log.isDebugEnabled()) {
            log.debug("Generated board:");
            for (String row :  minesweeperBoard.boardRowsAsStringList(true)) {
                log.debug(row);
            }
        }
    }

}
