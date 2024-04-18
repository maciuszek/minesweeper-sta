package com.maciuszek.minesweeper.controller.api;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.dto.CellIndexDto;
import com.maciuszek.minesweeper.service.MinesweeperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/minesweeper")
@RequiredArgsConstructor
public class MinesweeperRestController {

    private final MinesweeperService minesweeperService;

    @PutMapping("new-game")
    public ResponseEntity<MinesweeperBoard> newGame() {
        return ResponseEntity.ok(minesweeperService.newGame());
    }

    @GetMapping
    public ResponseEntity<MinesweeperBoard> getGame() {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (minesweeperBoard.isGameOver()) {
            return ResponseEntity.unprocessableEntity().body(minesweeperBoard);
        }
        return ResponseEntity.ok(minesweeperBoard);
    }

    @PostMapping("click")
    public ResponseEntity<MinesweeperBoard> clickCell(@Valid @RequestBody CellIndexDto cellIndexDto) {
        MinesweeperBoard minesweeperBoard = minesweeperService.clickCell(cellIndexDto.row(), cellIndexDto.column());
        if (minesweeperBoard.isGameOver()) {
            return ResponseEntity.unprocessableEntity().body(minesweeperBoard);
        }
        return ResponseEntity.ok(minesweeperBoard);
    }

    @PostMapping("mark")
    public ResponseEntity<MinesweeperBoard> markCell(@Valid @RequestBody CellIndexDto cellIndexDto) {
        MinesweeperBoard minesweeperBoard = minesweeperService.markCell(cellIndexDto.row(), cellIndexDto.column());
        if (minesweeperBoard.isGameOver()) {
            return ResponseEntity.unprocessableEntity().body(minesweeperBoard);
        }
        return ResponseEntity.ok(minesweeperBoard);
    }

}
