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
    private Long startTime;
    private Long endTime;

    public void setStatus(Status status) {
        if (Status.IN_PLAY == status) {
            this.startTime = System.currentTimeMillis();
        } else if (Status.WIN == status || Status.LOSE == status) {
            this.endTime = System.currentTimeMillis();
        }

        this.status = status;
    }

    public int marksMade() {
        return MinesweeperBoardHelper.countMarks(this);
    }

    public boolean isGameOver() {
        return !Status.IN_PLAY.equals(status);
    }

    public long gameTime() {
        return endTime - startTime;
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
