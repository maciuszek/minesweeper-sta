package com.maciuszek.minesweeper.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maciuszek.minesweeper.domain.serializer.MinesweeperBoardSerializer;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@JsonSerialize(using = MinesweeperBoardSerializer.class)
public class MinesweeperBoard {

    @RequiredArgsConstructor
    @Getter
    public enum Status {
        WIN("Won"),
        LOSE("Lost"),
        INPLAY("In-Play");

        private final String display;
    }

    private MinesweeperCell[][] minesweeperCells;
    private Status status = Status.INPLAY;

    public boolean isGameOver() {
        return !Status.INPLAY.equals(status);
    }
    public int totalMarks() {
        int marks = 0;

        for (int i = 0; i < MinesweeperSession.BOARD_SIZE; i++) {
            for (int y = 0; y < MinesweeperSession.BOARD_SIZE; y++) {
                MinesweeperCell minesweeperCell = minesweeperCells[i][y];
                if (minesweeperCell.isMarked()) {
                    ++marks;
                }
            }
        }

        return marks;
    }

}
