package com.maciuszek.minesweeper.domain.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.maciuszek.minesweeper.domain.MinesweeperBoard;
import com.maciuszek.minesweeper.domain.MinesweeperCell;
import com.maciuszek.minesweeper.session.MinesweeperSession;

import java.io.IOException;

public class MinesweeperBoardSerializer extends StdSerializer<MinesweeperBoard> {

    public MinesweeperBoardSerializer() {
        this(null);
    }

    public MinesweeperBoardSerializer(Class<MinesweeperBoard> t) {
        super(t);
    }

    @Override
    public void serialize(MinesweeperBoard minesweeperBoard, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        MinesweeperCell[][] minesweeperCells = minesweeperBoard.getMinesweeperCells();
        for (int i = 0; i < MinesweeperSession.BOARD_SIZE; i ++){
            StringBuilder sb = new StringBuilder();
            MinesweeperCell[] minesweeperCellsAtI = minesweeperCells[i];
            for (int y = 0; y < MinesweeperSession.BOARD_SIZE; y++) {
                MinesweeperCell minesweeperCell = minesweeperCellsAtI[y];
                sb.append(minesweeperCell.isHidden() ? "?" : minesweeperCell.getValue().getDisplay());
            }
            jgen.writeStringField(String.valueOf(i), sb.toString());
        }

        jgen.writeBooleanField("gameOver", minesweeperBoard.isGameOver());
        jgen.writeEndObject();
    }

}
