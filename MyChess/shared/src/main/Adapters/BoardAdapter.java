package Adapters;

import chess.ChessBoard;
import chess.ChessBoardIm;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class BoardAdapter implements JsonDeserializer<ChessBoard> {
    public ChessBoard deserialize(JsonElement el, JsonDeserializationContext ctx) throws JsonParseException {
        return ctx.deserialize(el, ChessBoardIm.class);
    }

    @Override
    public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}