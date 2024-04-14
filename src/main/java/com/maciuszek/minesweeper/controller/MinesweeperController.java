package com.maciuszek.minesweeper.controller;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.service.MinesweeperService;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/minesweeper")
@RequiredArgsConstructor
public class MinesweeperController {

    private final MinesweeperService minesweeperService;

    @Data
    public static class CellSelection {

        @NotNull
        private Integer x;
        @NotNull
        private Integer y;

    }

    @PutMapping("new-game")
    public ResponseEntity<MinesweeperBoard> newGame() {
        return ResponseEntity.ok(minesweeperService.newGame());
    }

    @GetMapping
    public ResponseEntity<MinesweeperBoard> getBoard() {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (minesweeperBoard.isGameOver()) {
            return ResponseEntity.unprocessableEntity().body(minesweeperBoard);
        }
        return ResponseEntity.ok(minesweeperBoard);
    }

    @PostMapping("click")
    public ResponseEntity<MinesweeperBoard> clickCell(@Valid @RequestBody CellSelection cellSelection) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (minesweeperBoard.isGameOver()) {
            return ResponseEntity.unprocessableEntity().body(minesweeperBoard);
        }

        int firstDimension = MinesweeperSession.BOARD_SIZE - 1 - cellSelection.getY();
        int secondDimension = cellSelection.getX();
        return ResponseEntity.ok(minesweeperService.clickCell(firstDimension, secondDimension));
    }

    @PostMapping("mark")
    public ResponseEntity<MinesweeperBoard> markCell(@Valid @RequestBody CellSelection cellSelection) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (minesweeperBoard.isGameOver()) {
            return ResponseEntity.unprocessableEntity().body(minesweeperBoard);
        }

        int firstDimension = MinesweeperSession.BOARD_SIZE - 1 - cellSelection.getY();
        int secondDimension = cellSelection.getX();
        return ResponseEntity.ok(minesweeperService.toggleMark(firstDimension, secondDimension));
    }

}
