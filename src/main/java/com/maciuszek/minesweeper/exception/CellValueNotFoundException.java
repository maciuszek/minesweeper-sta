package com.maciuszek.minesweeper.exception;

public class CellValueNotFoundException extends RuntimeException {

    public CellValueNotFoundException(int value) {
        super(String.format("Cell value cannot be set to %d", value));
    }

}
