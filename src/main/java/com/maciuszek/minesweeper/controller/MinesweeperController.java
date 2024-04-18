package com.maciuszek.minesweeper.controller;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.dto.CellIndexDto;
import com.maciuszek.minesweeper.service.MinesweeperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MinesweeperController {

    private final MinesweeperService minesweeperService;
    private static final String REDIRECT_TO_GAME = "redirect:/";

    @PostMapping("new-game")
    public String newGame() {
        minesweeperService.newGame();
        return REDIRECT_TO_GAME;
    }

    @GetMapping
    public String game(Model model) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        model.addAttribute("board", minesweeperBoard);
        return "minesweeper";
    }

    @PostMapping("click")
    public String clickCell(@Valid CellIndexDto cellIndexDto) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (!minesweeperBoard.isGameOver()) {
            minesweeperService.clickCell(cellIndexDto.row(), cellIndexDto.column());
        }
        return REDIRECT_TO_GAME;
    }

    @PostMapping("mark")
    public String markCell(@Valid CellIndexDto cellIndexDto) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (!minesweeperBoard.isGameOver()) {
            minesweeperService.markCell(cellIndexDto.row(), cellIndexDto.column());
        }
        return REDIRECT_TO_GAME;
    }

}
