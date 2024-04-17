package com.maciuszek.minesweeper.controller;

import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.service.MinesweeperService;
import com.maciuszek.minesweeper.session.MinesweeperSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MinesweeperViewController {
    private final MinesweeperService minesweeperService;

    public MinesweeperViewController(MinesweeperService minesweeperService) {
        this.minesweeperService = minesweeperService;
    }

    @PostMapping("new-game")
    public String newGame() {
        minesweeperService.newGame();
        return "redirect:/";
    }

    @GetMapping
    public String home(Model model) {
        MinesweeperBoard board = minesweeperService.getCurrentSessionsBoard();
        model.addAttribute("board", board);
        return "minesweeper";
    }

    @PostMapping("click")
    public String handleClick(@Valid MinesweeperController.CellSelection cellSelection, RedirectAttributes attributes) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (minesweeperBoard.isGameOver()) {
            attributes.addFlashAttribute("error", "Game Over");
        } else {
            int firstDimension = MinesweeperSession.BOARD_SIZE - 1 - cellSelection.getY();
            int secondDimension = cellSelection.getX();
            minesweeperService.clickCell(firstDimension, secondDimension);
        }
        return "redirect:/";
    }

    @PostMapping("mark")
    public String handleMark(@Valid MinesweeperController.CellSelection cellSelection, RedirectAttributes attributes) {
        MinesweeperBoard minesweeperBoard = minesweeperService.getCurrentSessionsBoard();
        if (minesweeperBoard.isGameOver()) {
            attributes.addFlashAttribute("error", "Game Over");
        } else {
            int firstDimension = MinesweeperSession.BOARD_SIZE - 1 - cellSelection.getY();
            int secondDimension = cellSelection.getX();
            minesweeperService.toggleMark(firstDimension, secondDimension);
        }
        return "redirect:/";
    }
}
