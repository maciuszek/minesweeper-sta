package com.maciuszek.minesweeper.domain.dto;

import jakarta.validation.constraints.NotNull;

public record CellIndexDto(@NotNull Integer row, @NotNull Integer column) { }
