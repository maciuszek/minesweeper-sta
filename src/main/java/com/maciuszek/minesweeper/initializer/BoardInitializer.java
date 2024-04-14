package com.maciuszek.minesweeper.initializer;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardInitializer implements CommandLineRunner {

    private final MinesweeperSession minesweeperSession;

    // initialize the game session on spring start
    public void run(String... args) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        MinesweeperCell[][] minesweeperCells = new MinesweeperCell[MinesweeperSession.BOARD_SIZE][MinesweeperSession.BOARD_SIZE];
        for (int i = 0; i < MinesweeperSession.BOARD_SIZE; i++) {
            for (int y = 0; y < MinesweeperSession.BOARD_SIZE; y++) {
                minesweeperCells[i][y] = new MinesweeperCell();
            }
        }

        int bombsToAdd = MinesweeperSession.BOMB_COUNT;
        while (bombsToAdd > 0) {
            int randomX = (int) (Math.random() * (MinesweeperSession.BOARD_SIZE - 1));
            int randomY = (int) (Math.random() * (MinesweeperSession.BOARD_SIZE - 1));
            MinesweeperCell minesweeperCell = minesweeperCells[randomX][randomY];
            if (!minesweeperCell.isBomb()) {
                minesweeperCell.setBomb(true);
                --bombsToAdd;
                for (MinesweeperCell surroundCell : getSurroundingCells(randomX, randomY, minesweeperCells)) {
                    surroundCell.incSurroundingBombCount();
                }
            }
        }

        minesweeperBoard.setMinesweeperCells(minesweeperCells);

        if (log.isDebugEnabled()) {
            System.out.println("Generated board:");
            for (int i = 0; i < MinesweeperSession.BOARD_SIZE; i++) {
                for (int y = 0; y < MinesweeperSession.BOARD_SIZE; y++) {
                    System.out.print(minesweeperCells[i][y].getValue(true) + " ");
                }
                System.out.println();
            }
        }
    }

    private List<MinesweeperCell> getSurroundingCells(int x, int y, MinesweeperCell[][] minesweeperCells) {
        List<MinesweeperCell> surroundingCells = new ArrayList<>();

        for (int i = x - 1; i <= x + 1; i++) {
            if (i < 0 || i >= MinesweeperSession.BOARD_SIZE) {
                continue;
            }
            for (int j = y - 1; j <= y + 1; j++) {
                if (j < 0 || j >= MinesweeperSession.BOARD_SIZE) {
                    continue;
                }
                if (i != x || j != y) {
                    surroundingCells.add(minesweeperCells[i][j]);
                }
            }
        }

        return surroundingCells;
    }

}
