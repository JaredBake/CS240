package Server.Adapters;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

public class Deserializer {
    public static Gson createGsonDeserializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // This line should only be needed if your board class is using a Map to store chess pieces instead of a 2D array.
        gsonBuilder.enableComplexMapKeySerialization();

        gsonBuilder.registerTypeAdapter(ChessGame.class,
                (JsonDeserializer<ChessGame>) (el, type, ctx) -> ctx.deserialize(el, ChessGameIm.class));

        gsonBuilder.registerTypeAdapter(ChessBoard.class,
                (JsonDeserializer<ChessBoard>) (el, type, ctx) -> ctx.deserialize(el, ChessBoardIm.class));

        gsonBuilder.registerTypeAdapter(ChessPiece.class,
                (JsonDeserializer<ChessPiece>) (el, type, ctx) -> ctx.deserialize(el, ChessPieceIm.class));

        gsonBuilder.registerTypeAdapter(ChessMove.class,
                (JsonDeserializer<ChessMove>) (el, type, ctx) -> ctx.deserialize(el, ChessMoveIm.class));

        gsonBuilder.registerTypeAdapter(ChessPosition.class,
                (JsonDeserializer<ChessPosition>) (el, type, ctx) -> ctx.deserialize(el, ChessPositionIm.class));

        gsonBuilder.registerTypeAdapter(ChessPieceIm.class,
                (JsonDeserializer<ChessPieceIm>) (el, type, ctx) -> {
                    ChessPieceIm chessPiece = null;
                    if (el.isJsonObject()) {
                        chessPiece = ctx.deserialize(el, ChessPiece.class);
//                        String pieceType = el.getAsJsonObject().get("type").getAsString();
//                        switch (ChessPiece.PieceType.valueOf(pieceType)) {
//                            case PAWN -> chessPiece = ctx.deserialize(el, Pawn.class);
//                            case ROOK -> chessPiece = ctx.deserialize(el, Rook.class);
//                            case KNIGHT -> chessPiece = ctx.deserialize(el, Knight.class);
//                            case BISHOP -> chessPiece = ctx.deserialize(el, Bishop.class);
//                            case QUEEN -> chessPiece = ctx.deserialize(el, Queen.class);
//                            case KING -> chessPiece = ctx.deserialize(el, King.class);
//                        }
                    }
                    return chessPiece;
                });

        return gsonBuilder.create();
    }
}
