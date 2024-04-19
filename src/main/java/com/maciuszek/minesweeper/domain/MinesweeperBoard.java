package com.maciuszek.minesweeper.domain;

import com.maciuszek.minesweeper.helper.MinesweeperBoardHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class MinesweeperBoard {

    private final int bombCount;
    private final int boardHeight;
    private final int boardWidth;

    private MinesweeperCell[][] minesweeperCells;
    private Status status;

    public boolean isGameOver() {
        return !Status.IN_PLAY.equals(status);
    }

    public int marksMade() {
        return MinesweeperBoardHelper.countMarks(this);
    }

    @RequiredArgsConstructor
    @Getter
    public enum Status {
        WIN("Won"),
        LOSE("Lost"),
        IN_PLAY("In-Play");

        private final String display;
    }

}
