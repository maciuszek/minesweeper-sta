package com.maciuszek.minesweeper.helper;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.domain.dto.CellIndexDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MinesweeperHelper {

    public static List<MinesweeperCell> getSurroundingCells(MinesweeperBoard minesweeperBoard, int r, int c) {
        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        List<MinesweeperCell> surroundingCells = new ArrayList<>();

        for (int i = r - 1; i <= r + 1; i++) {
            if (i < 0 || i >= minesweeperBoard.getBoardSize()) {
                continue;
            }
            for (int j = c - 1; j <= c + 1; j++) {
                if (j < 0 || j >= minesweeperBoard.getBoardSize()) {
                    continue;
                }
                if (i != r || j != c) {
                    surroundingCells.add(minesweeperCells[i][j]);
                }
            }
        }

        return surroundingCells;
    }

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

}
