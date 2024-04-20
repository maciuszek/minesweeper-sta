package com.maciuszek.minesweeper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MinesweeperCell {

    private boolean bomb;
    private boolean marked;
    private int surroundingBombCount;
    private boolean hidden = true;

    public void incSurroundingBombCount() {
        surroundingBombCount++;
    }

    public boolean isEmpty() {
        return surroundingBombCount == 0;
    }

    public char textValue(boolean unhidden) {
        if (marked)
            return 'X';
        if (!unhidden && hidden)
            return '?';
        if (bomb)
            return 'B';
        if (this.isEmpty())
            return '-';
        return Character.forDigit(surroundingBombCount, 10);
    }

}
