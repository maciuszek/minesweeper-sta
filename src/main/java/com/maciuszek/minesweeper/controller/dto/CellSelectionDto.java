package com.maciuszek.minesweeper.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CellSelectionDto {

    @NotNull
    private Integer row;
    @NotNull
    private Integer column;

}
