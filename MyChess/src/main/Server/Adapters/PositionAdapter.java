package Server.Adapters;

import chess.*;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PositionAdapter implements JsonDeserializer<ChessPosition> {
    public ChessPosition deserialize(JsonElement el, JsonDeserializationContext ctx) throws JsonParseException {
        return ctx.deserialize(el, ChessPositionIm.class);
    }

    @Override
    public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}
