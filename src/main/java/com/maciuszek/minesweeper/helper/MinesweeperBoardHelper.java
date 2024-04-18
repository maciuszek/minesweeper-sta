package com.maciuszek.minesweeper.helper;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.domain.dto.CellIndexDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MinesweeperBoardHelper {

    public static List<CellIndexDto> getSurroundingIndexes(MinesweeperBoard minesweeperBoard, int r, int c) {
        List<CellIndexDto> surroundingIndexes = new ArrayList<>();

        for (int i = r - 1; i <= r + 1; i++) {
            if (i < 0 || i >= minesweeperBoard.getBoardSize()) {
                continue;
            }
            for (int j = c - 1; j <= c + 1; j++) {
                if (j < 0 || j >= minesweeperBoard.getBoardSize()) {
                    continue;
                }
                if (i != r || j != c) {
                    surroundingIndexes.add(new CellIndexDto(i, j));
                }
            }
        }

        return surroundingIndexes;
    }

    public static List<MinesweeperCell> getSurroundingCells(MinesweeperBoard minesweeperBoard, int r, int c) {
        return getSurroundingIndexes(minesweeperBoard, r, c).stream()
                .map(cellIndexDto -> minesweeperBoard.getMinesweeperCells()[cellIndexDto.row()][cellIndexDto.column()])
                .toList();
    }

    public static List<String> getRowsAsListOfString(MinesweeperBoard minesweeperBoard, boolean unhidden) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < minesweeperBoard.getBoardSize(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < minesweeperBoard.getBoardSize(); y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                sb.append(minesweeperCell.getValue(unhidden)).append(' ');
            }
            rows.add(sb.replace(sb.length() - 1, sb.length(), "").toString());
        }
        return rows;
    }

    public static void revealCells(MinesweeperBoard minesweeperBoard, int r, int c) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();

        minesweeperCells[r][c].setHidden(false);

        if (isIndexDeducible(minesweeperBoard, r, c)) {
            for (CellIndexDto cellIndexDto : getSurroundingIndexes(minesweeperBoard, r, c)) {
                MinesweeperCell minesweeperCell = minesweeperCells[cellIndexDto.row()][cellIndexDto.column()];
                if (minesweeperCell.isHidden() && !minesweeperCell.isMarked()) {
                    revealCells(minesweeperBoard, cellIndexDto.row(), cellIndexDto.column());
                }
            }
        }
    }

    public static boolean isIndexDeducible(MinesweeperBoard minesweeperBoard, int r, int c) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        if (minesweeperCells[r][c].isEmpty()) {
            return true;
        }

        int unknownBombs = minesweeperCells[r][c].getSurroundingBombCount();
        for (MinesweeperCell surroundingCell : getSurroundingCells(minesweeperBoard, r, c)) {
            if (surroundingCell.isMarked()) {
                --unknownBombs;
            }
        }

        return unknownBombs <= 0;
    }

    public static void refreshStatus(MinesweeperBoard minesweeperBoard) {
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

        if (revealedCells + minesweeperBoard.getBombCount() == minesweeperBoard.getBoardSize() * minesweeperBoard.getBoardSize()) {
            minesweeperBoard.setStatus(MinesweeperBoard.Status.WIN);
        }
    }

    public static void toggleMark(MinesweeperBoard minesweeperBoard, int r, int c) {
        MinesweeperCell minesweeperCell = minesweeperBoard.getMinesweeperCells()[r][c];
        minesweeperCell.setMarked(!minesweeperCell.isMarked());
    }

    public static int countMarks(MinesweeperBoard minesweeperBoard) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        int marks = 0;

        for (int i = 0; i < minesweeperBoard.getBoardSize(); i++) {
            for (int y = 0; y < minesweeperBoard.getBoardSize(); y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                if (minesweeperCell.isMarked()) {
                    ++marks;
                }
            }
        }

        return marks;
    }

}
