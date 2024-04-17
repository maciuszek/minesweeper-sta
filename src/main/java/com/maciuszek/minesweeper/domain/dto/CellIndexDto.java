package com.maciuszek.minesweeper.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CellIndexDto {

    @NotNull
    private final Integer row;
    @NotNull
    private final Integer column;

}
