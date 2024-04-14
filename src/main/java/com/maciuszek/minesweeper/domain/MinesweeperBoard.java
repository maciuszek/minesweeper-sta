package com.maciuszek.minesweeper.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maciuszek.minesweeper.domain.serializer.MinesweeperBoardSerializer;
import lombok.Data;

@Data
@JsonSerialize(using = MinesweeperBoardSerializer.class)
public class MinesweeperBoard {

    public enum Status {
        WIN,
        LOSE,
        INPLAY
    }

    private MinesweeperCell[][] minesweeperCells;
    private Status status = Status.INPLAY;

    public boolean isGameOver() {
        return !Status.INPLAY.equals(status);
    }

}
