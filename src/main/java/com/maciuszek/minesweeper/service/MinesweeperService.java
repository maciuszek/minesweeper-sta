package com.maciuszek.minesweeper.service;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.initializer.BoardInitializer;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinesweeperService {

    private final MinesweeperSession minesweeperSession;
    private final BoardInitializer boardInitializer;

    public MinesweeperBoard newGame() {
        boardInitializer.run();
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        minesweeperBoard.setGameOver(false);
        return minesweeperBoard;
    }

    public MinesweeperBoard getCurrentSessionsBoard() {
        return minesweeperSession.getMinesweeperBoard();
    }

    public MinesweeperBoard clickCell(int x, int y) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        if (minesweeperBoard.isGameOver()) {
            return minesweeperBoard;
        }

        MinesweeperCell minesweeperCell = minesweeperBoard.getMinesweeperCells()[x][y];
        revealCells(minesweeperBoard, x, y);

        if (minesweeperCell.isBomb()) {
            minesweeperBoard.setGameOver(true);
        }

        return minesweeperBoard;
    }

    private void revealCells(MinesweeperBoard minesweeperBoard, int x, int y) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();

        minesweeperCells[x][y].setHidden(false);

        for (int i = x - 1; i <= x + 1; i++) {
            if (i < 0 || i >= MinesweeperSession.BOARD_SIZE) {
                continue;
            }
            for (int j = y - 1; j <= y + 1; j++) {
                if (j < 0 || j >= MinesweeperSession.BOARD_SIZE) {
                    continue;
                }
                MinesweeperCell minesweeperCell = minesweeperCells[i][j];
                if (minesweeperCell.isHidden() && minesweeperCell.isEmpty()) {
                    revealCells(minesweeperBoard, i, j);
                }
            }
        }
    }

}
