package com.maciuszek.minesweeper.initializer;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.helper.MinesweeperBoardHelper;
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
        minesweeperBoard.setStatus(MinesweeperBoard.Status.IN_PLAY);
        minesweeperBoard.setMinesweeperCells(new MinesweeperCell[minesweeperBoard.getBoardHeight()][minesweeperBoard.getBoardWidth()]);

        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        for (int i = 0; i < minesweeperBoard.getBoardHeight(); i++) {
            for (int y = 0; y < minesweeperBoard.getBoardWidth(); y++) {
                minesweeperCells[i][y] = new MinesweeperCell();
            }
        }

        int bombsToAdd = minesweeperBoard.getBombCount();
        while (bombsToAdd > 0) {
            int randomRow = (int) (Math.random() * (minesweeperBoard.getBoardHeight() - 1));
            int randomColumn = (int) (Math.random() * (minesweeperBoard.getBoardWidth() - 1));
            MinesweeperCell minesweeperCell = minesweeperCells[randomRow][randomColumn];
            if (!minesweeperCell.isBomb()) {
                minesweeperCell.setBomb(true);
                --bombsToAdd;
                for (MinesweeperCell surroundingCell : MinesweeperBoardHelper.getSurroundingCells(minesweeperBoard, randomRow, randomColumn)) {
                    surroundingCell.incSurroundingBombCount();
                }
            }
        }

        minesweeperBoard.setMinesweeperCells(minesweeperCells);

        if (log.isDebugEnabled()) {
            log.debug("Generated board:");
            for (String row : MinesweeperBoardHelper.getRowsAsListOfString(minesweeperBoard, true)) {
                log.debug(row);
            }
        }
    }

}
