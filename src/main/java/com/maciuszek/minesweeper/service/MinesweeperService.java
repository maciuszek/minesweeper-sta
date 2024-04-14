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
        minesweeperBoard.setStatus(MinesweeperBoard.Status.INPLAY);
        return minesweeperBoard;
    }

    public MinesweeperBoard getCurrentSessionsBoard() {
        return minesweeperSession.getMinesweeperBoard();
    }

    public MinesweeperBoard clickCell(int fd, int sd) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        MinesweeperCell minesweeperCell = minesweeperBoard.getMinesweeperCells()[fd][sd];
        if (minesweeperCell.isMarked()) {
            return minesweeperBoard;
        }

        revealCells(minesweeperBoard, fd, sd);

        if (!minesweeperBoard.isGameOver() && isWin(minesweeperBoard)) {
            minesweeperBoard.setStatus(MinesweeperBoard.Status.WIN);
        }

        return minesweeperBoard;
    }

    public MinesweeperBoard toggleMark(int fd, int sd) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();

        MinesweeperCell minesweeperCell = minesweeperBoard.getMinesweeperCells()[fd][sd];
        minesweeperCell.setMarked(!minesweeperCell.isMarked());

        return minesweeperBoard;
    }

    private void revealCells(MinesweeperBoard minesweeperBoard, int fd, int sd) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();

        minesweeperCells[fd][sd].setHidden(false);

        if (minesweeperCells[fd][sd].isBomb()) {
            minesweeperBoard.setStatus(MinesweeperBoard.Status.LOSE);
        }

        if (isDeducible(minesweeperBoard, fd, sd)) {
            for (int i = fd - 1; i <= fd + 1; i++) {
                if (i < 0 || i >= MinesweeperSession.BOARD_SIZE) {
                    continue;
                }
                for (int j = sd - 1; j <= sd + 1; j++) {
                    if (j < 0 || j >= MinesweeperSession.BOARD_SIZE) {
                        continue;
                    }
                    MinesweeperCell minesweeperCell = minesweeperCells[i][j];
                    if (minesweeperCell.isHidden() && !minesweeperCell.isMarked()) {
                        revealCells(minesweeperBoard, i, j);
                    }
                }
            }
        }
    }

    private boolean isDeducible(MinesweeperBoard minesweeperBoard, int fd, int sd) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        if (minesweeperCells[fd][sd].isEmpty()) {
            return true;
        }

        int unknownBombs = minesweeperCells[fd][sd].getSurroundingBombCount();
        for (int i = fd - 1; i <= fd + 1; i++) {
            if (i < 0 || i >= MinesweeperSession.BOARD_SIZE) {
                continue;
            }
            for (int j = sd - 1; j <= sd + 1; j++) {
                if (j < 0 || j >= MinesweeperSession.BOARD_SIZE) {
                    continue;
                }
                if (minesweeperCells[i][j].isMarked()) {
                    --unknownBombs;
                }
            }
        }

        return unknownBombs == 0;
    }

    private boolean isWin(MinesweeperBoard minesweeperBoard) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        int revealedCells = 0;
        for (int i = 0; i < MinesweeperSession.BOARD_SIZE; i++) {
            for (int y = 0; y < MinesweeperSession.BOARD_SIZE; y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                if (!minesweeperCell.isHidden()) {
                    ++revealedCells;
                }
            }
        }

        return (revealedCells + MinesweeperSession.BOMB_COUNT) == (MinesweeperSession.BOARD_SIZE * MinesweeperSession.BOARD_SIZE);
    }

}
