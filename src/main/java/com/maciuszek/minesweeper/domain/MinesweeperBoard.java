package com.maciuszek.minesweeper.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class MinesweeperBoard {

    @RequiredArgsConstructor
    @Getter
    public enum Status {
        WIN("Won"),
        LOSE("Lost"),
        INPLAY("In-Play");

        private final String display;
    }

    private final int boardSize;
    private final int bombCount;

    private MinesweeperCell[][] minesweeperCells;
    private Status status = Status.INPLAY;

    public boolean isGameOver() {
        return !Status.INPLAY.equals(status);
    }

    public int countMarks() {
        int marks = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int y = 0; y < boardSize; y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                if (minesweeperCell.isMarked()) {
                    ++marks;
                }
            }
        }

        return marks;
    }

    public List<String> boardRowsAsStringList(boolean unhidden) {
        List<String> rows = new LinkedList<>();
        for (int i = 0; i < boardSize; i++){
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < boardSize; y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                sb.append(minesweeperCell.getValue(unhidden)).append(" ");
            }
            rows.add(sb.replace(sb.length() - 1, sb.length(), "").toString());
        }
        return rows;
    }

}
