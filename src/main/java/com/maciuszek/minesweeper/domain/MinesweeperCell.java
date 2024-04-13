package com.maciuszek.minesweeper.domain;

import com.maciuszek.minesweeper.exception.CellValueNotFoundException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class MinesweeperCell {

    @RequiredArgsConstructor
    public enum Value {
        EMPTY("-"),
        ONE("1"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        BOMB("B");

        @Getter
        private final String display;

        private static Value valueOf(@NotNull @Min(1) @Max(8) Integer numericValue) {
            // todo make this more efficient by replacing it with a switch
            for (Value value : Value.values()) {
                if (value.display.equals(String.valueOf(numericValue))) {
                    return value;
                }
            }

            throw new CellValueNotFoundException(numericValue);
        }

    }

    private Value value = Value.EMPTY;
    private int surroundingBombCount = 0;
    private boolean hidden = true;

    public boolean isBomb() {
        return Value.BOMB.equals(value);
    }

    public boolean isEmpty() {
        return Value.EMPTY.equals(value);
    }

    public void incSurroundingBombCount () {
        surroundingBombCount = surroundingBombCount + 1;
        if (!this.isBomb()) {
            value = Value.valueOf(surroundingBombCount);
        }
    }

}
