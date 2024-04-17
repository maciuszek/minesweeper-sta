package com.maciuszek.minesweeper.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MinesweeperCell {

    private boolean bomb;
    private boolean marked;
    private int surroundingBombCount;
    private boolean hidden = true;

    public void incSurroundingBombCount () {
        surroundingBombCount = surroundingBombCount + 1;
    }

    public boolean isEmpty() {
        return surroundingBombCount == 0;
    }

    public String getValue(boolean cheat) {
        if (marked) {
            return "X";
        }
        if (!cheat && hidden) {
            return "?";
        }
        if (bomb) {
            return "B";
        }
        if (this.isEmpty()) {
            return "-";
        }
        return String.valueOf(surroundingBombCount);
    }

}
