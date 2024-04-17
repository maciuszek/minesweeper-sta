package com.maciuszek.minesweeper.controller;

import com.maciuszek.minesweeper.controller.dto.CellSelectionDto;
import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.service.MinesweeperService;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MinesweeperController {

    private final MinesweeperService minesweeperService;

    @PostMapping("new-game")
    public String newGame() {
        minesweeperService.newGame();
        return "redirect:/";
    }

    @GetMapping
    public String getGame(Model model) {
        MinesweeperBoard board = minesweeperService.getCurrentSessionsBoard();
        model.addAttribute("board", board);
        model.addAttribute("bombCount", MinesweeperSession.BOMB_COUNT);
        return "minesweeper";
    }

    @PostMapping("click")
    public String clickCell(@Valid CellSelectionDto cellSelectionDto, RedirectAttributes attributes) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (minesweeperBoard.isGameOver()) {
            attributes.addFlashAttribute("error", "Game Over");
        } else {
            minesweeperService.clickCell(cellSelectionDto.getRow(), cellSelectionDto.getColumn());
        }
        return "redirect:/";
    }

    @PostMapping("mark")
    public String markCell(@Valid CellSelectionDto cellSelectionDto, RedirectAttributes attributes) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (minesweeperBoard.isGameOver()) {
            attributes.addFlashAttribute("error", "Game Over");
        } else {
            minesweeperService.toggleMark(cellSelectionDto.getRow(), cellSelectionDto.getColumn());
        }
        return "redirect:/";
    }

}
