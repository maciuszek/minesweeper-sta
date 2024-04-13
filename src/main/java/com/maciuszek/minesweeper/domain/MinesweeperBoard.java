package com.maciuszek.minesweeper.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maciuszek.minesweeper.domain.serializer.MinesweeperBoardSerializer;
import lombok.Data;

@Data
@JsonSerialize(using = MinesweeperBoardSerializer.class)
public class MinesweeperBoard {

    private MinesweeperCell[][] minesweeperCells; // todo this probably needs a custom serializer to display the data in a readable way
    private boolean gameOver;

}
