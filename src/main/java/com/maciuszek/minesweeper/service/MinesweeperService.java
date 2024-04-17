package com.maciuszek.minesweeper.service;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.domain.dto.CellIndexDto;
import com.maciuszek.minesweeper.helper.MinesweeperHelper;
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

    public MinesweeperBoard clickCell(int r, int c) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        MinesweeperCell minesweeperCell = minesweeperBoard.getMinesweeperCells()[r][c];
        if (minesweeperCell.isMarked()) {
            return minesweeperBoard;
        }

        revealCells(minesweeperBoard, r, c);
        updateStatus(minesweeperBoard);

        return minesweeperBoard;
    }

    public MinesweeperBoard toggleMark(int r, int c) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        MinesweeperCell minesweeperCell = minesweeperBoard.getMinesweeperCells()[r][c];
        if (!minesweeperCell.isHidden()) {
            return minesweeperBoard;
        }

        minesweeperCell.setMarked(!minesweeperCell.isMarked());

        return minesweeperBoard;
    }

    private void revealCells(MinesweeperBoard minesweeperBoard, int r, int c) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();

        minesweeperCells[r][c].setHidden(false);

        if (isDeducible(minesweeperBoard, r, c)) {
            for (CellIndexDto cellIndexDto : MinesweeperHelper.getSurroundingIndexes(minesweeperBoard, r, c)) {
                MinesweeperCell minesweeperCell = minesweeperCells[cellIndexDto.getRow()][cellIndexDto.getColumn()];
                if (minesweeperCell.isHidden() && !minesweeperCell.isMarked()) {
                    revealCells(minesweeperBoard, cellIndexDto.getRow(), cellIndexDto.getColumn());
                }

            }
        }
    }

    private boolean isDeducible(MinesweeperBoard minesweeperBoard, int r, int c) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        if (minesweeperCells[r][c].isEmpty()) {
            return true;
        }

        int unknownBombs = minesweeperCells[r][c].getSurroundingBombCount();
        for (MinesweeperCell surroundCell : MinesweeperHelper.getSurroundingCells(minesweeperBoard, r, c)) {
            if (surroundCell.isMarked()) {
                --unknownBombs;
            }
        }

        return unknownBombs <= 0;
    }

    private void updateStatus(MinesweeperBoard minesweeperBoard) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        int revealedCells = 0;
        for (int i = 0; i < minesweeperBoard.getBoardSize(); i++) {
            for (int y = 0; y < minesweeperBoard.getBoardSize(); y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                if (!minesweeperCell.isHidden()) {
                    if (minesweeperCell.isBomb()) {
                        minesweeperBoard.setStatus(MinesweeperBoard.Status.LOSE);
                        return;
                    }
                    ++revealedCells;
                }
            }
        }

        if (revealedCells + minesweeperBoard.getBombCount() == (minesweeperBoard.getBoardSize() * minesweeperBoard.getBoardSize())) {
            minesweeperBoard.setStatus(MinesweeperBoard.Status.WIN);
        }
    }

}
