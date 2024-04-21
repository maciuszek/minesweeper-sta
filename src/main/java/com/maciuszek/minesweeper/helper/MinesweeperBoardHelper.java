package com.maciuszek.minesweeper.helper;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.domain.dto.CellIndexDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class MinesweeperBoardHelper {

    /**
     * Initialize a board around a specific index i.e. avoiding setting the bomb on the index
     *
     * @param minesweeperBoard the board to populate
     * @param r the row of the index to skip
     * @param c the column of the index to skip
     */
    public static void initialize(MinesweeperBoard minesweeperBoard, int r, int c) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();

        int bombsToAdd = minesweeperBoard.getBombCount();
        while (bombsToAdd > 0) {
            int randomRow = (int) (Math.random() * (minesweeperBoard.getBoardHeight() - 1));
            int randomColumn = (int) (Math.random() * (minesweeperBoard.getBoardWidth() - 1));

            if (randomRow == r && randomColumn == c) {
                continue;
            }

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
        minesweeperBoard.setStatus(MinesweeperBoard.Status.IN_PLAY);

        if (log.isDebugEnabled()) {
            log.debug("Generated board:");
            for (String row : MinesweeperBoardHelper.getRowsAsListOfString(minesweeperBoard, true)) {
                log.debug(row);
            }
        }
    }

    public static List<CellIndexDto> getSurroundingIndexes(MinesweeperBoard minesweeperBoard, int r, int c) {
        List<CellIndexDto> surroundingIndexes = new ArrayList<>();

        for (int i = r - 1; i <= r + 1; i++) {
            if (i < 0 || i >= minesweeperBoard.getBoardHeight()) {
                continue;
            }
            for (int j = c - 1; j <= c + 1; j++) {
                if (j < 0 || j >= minesweeperBoard.getBoardWidth()) {
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
        for (int i = 0; i < minesweeperBoard.getBoardHeight(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < minesweeperBoard.getBoardWidth(); y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                sb.append(minesweeperCell.textValue(unhidden)).append(' ');
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
        for (int i = 0; i < minesweeperBoard.getBoardHeight(); i++) {
            for (int y = 0; y < minesweeperBoard.getBoardWidth(); y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                if (!minesweeperCell.isHidden()) {
                    if (minesweeperCell.isBomb()) {
                        minesweeperBoard.setStatus(MinesweeperBoard.Status.LOSE);
                        MinesweeperBoardHelper.revealBombs(minesweeperBoard);
                        return;
                    }
                    ++revealedCells;
                }
            }
        }

        if (revealedCells + minesweeperBoard.getBombCount() == minesweeperBoard.getBoardHeight() * minesweeperBoard.getBoardWidth()) {
            minesweeperBoard.setStatus(MinesweeperBoard.Status.WIN);
        }
    }

    public static void revealBombs(MinesweeperBoard minesweeperBoard) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        for (int i = 0; i < minesweeperBoard.getBoardHeight(); i++) {
            for (int y = 0; y < minesweeperBoard.getBoardWidth(); y++) {
                MinesweeperCell cell = minesweeperCells[i][y];
                if (cell.isBomb()) {
                    cell.setHidden(false);
                }
            }
        }
    }

    public static void toggleMark(MinesweeperBoard minesweeperBoard, int r, int c) {
        MinesweeperCell minesweeperCell = minesweeperBoard.getMinesweeperCells()[r][c];
        minesweeperCell.setMarked(!minesweeperCell.isMarked());
    }

    public static int countMarks(MinesweeperBoard minesweeperBoard) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        int marks = 0;

        for (int i = 0; i < minesweeperBoard.getBoardHeight(); i++) {
            for (int y = 0; y < minesweeperBoard.getBoardWidth(); y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                if (minesweeperCell.isMarked()) {
                    ++marks;
                }
            }
        }

        return marks;
    }

}
