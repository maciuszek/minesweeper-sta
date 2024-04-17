package com.maciuszek.minesweeper.domain.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.maciuszek.minesweeper.domain.MinesweeperBoard;

import java.io.IOException;
import java.util.List;

public class MinesweeperBoardJsonSerializer extends JsonSerializer<MinesweeperBoard> {

    @Override
    public void serialize(MinesweeperBoard minesweeperBoard, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        List<String> rows = minesweeperBoard.boardRowsAsStringList(false);
        for (int i = 0; i < rows.size(); i++) {
            jgen.writeStringField("row-" + i, rows.get(i));
        }

        jgen.writeNumberField("marks", minesweeperBoard.totalMarks());
        jgen.writeNumberField("bombCount", minesweeperBoard.getBombCount());
        jgen.writeStringField("status", minesweeperBoard.getStatus().toString());
        jgen.writeEndObject();
    }

    @Override
    public Class<MinesweeperBoard> handledType() {
        return MinesweeperBoard.class;
    }

}
