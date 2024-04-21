package com.maciuszek.minesweeper.service;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.helper.MinesweeperBoardHelper;
import com.maciuszek.minesweeper.initializer.BoardInitializer;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinesweeperService {

    private final MinesweeperSession minesweeperSession;
    private final BoardInitializer boardInitializer;

    public MinesweeperBoard newGame() {
        boardInitializer.run();
        return minesweeperSession.getMinesweeperBoard();
    }

    public MinesweeperBoard getCurrentSessionsBoard() {
        return minesweeperSession.getMinesweeperBoard();
    }

    public MinesweeperBoard clickCell(int r, int c) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();

        if (minesweeperBoard.getStatus() == MinesweeperBoard.Status.NOT_INITIALIZED) {
            MinesweeperBoardHelper.initialize(minesweeperBoard, r, c);
        }

        if (minesweeperBoard.isGameOver() || minesweeperBoard.getMinesweeperCells()[r][c].isMarked()) {
            return minesweeperBoard;
        }

        MinesweeperBoardHelper.revealCells(minesweeperBoard, r, c);
        MinesweeperBoardHelper.refreshStatus(minesweeperBoard);

        return minesweeperBoard;
    }

    public MinesweeperBoard markCell(int r, int c) {
        MinesweeperBoard minesweeperBoard = minesweeperSession.getMinesweeperBoard();
        if (minesweeperBoard.getStatus() == MinesweeperBoard.Status.NOT_INITIALIZED || minesweeperBoard.isGameOver() || !minesweeperBoard.getMinesweeperCells()[r][c].isHidden()) {
            return minesweeperBoard;
        }

        MinesweeperBoardHelper.toggleMark(minesweeperBoard, r, c);

        return minesweeperBoard;
    }

}
