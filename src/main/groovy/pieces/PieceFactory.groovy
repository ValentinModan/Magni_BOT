package pieces

import board.ColorEnum
import board.pieces.Bishop
import board.pieces.King
import board.pieces.Knight
import board.pieces.Pawn
import board.pieces.Piece
import board.pieces.PieceType
import board.pieces.Queen
import board.pieces.Rook

import static board.pieces.PieceType.BISHOP
import static board.pieces.PieceType.KING
import static board.pieces.PieceType.KNIGHT
import static board.pieces.PieceType.PAWN
import static board.pieces.PieceType.QUEEN
import static board.pieces.PieceType.ROOK

class PieceFactory {
    private static Map<PieceType, Piece> whitePieceChache = new HashMap<>()
    private static Map<PieceType, Piece> blackPieceCache = new HashMap<>();

    static Piece createPiece(ColorEnum colorEnum, PieceType pieceType) {
        return createPiece(pieceType, colorEnum == ColorEnum.WHITE);
    }

    static Piece createPiece(PieceType pieceType, Boolean isWhite) {
        def cacheMap = isWhite ? whitePieceChache : blackPieceCache;

        if (cacheMap.containsKey(pieceType)) {
            return cacheMap.get(pieceType)
        }
        switch (pieceType) {
            case PAWN:
                cacheMap.put(PAWN, new Pawn(isWhite))
                break;
            case BISHOP:
                cacheMap.put(BISHOP, new Bishop(isWhite))
                break
            case KNIGHT:
                cacheMap.put(KNIGHT, new Knight(isWhite))
                break
            case ROOK:
                cacheMap.put(ROOK, new Rook(isWhite))
                break
            case QUEEN:
                cacheMap.put(QUEEN, new Queen(isWhite))
                break
            case KING:
                cacheMap.put(KING, new King(isWhite))
                break
            default:
                throw new Exception("Invalid type " + pieceType);
        }
        return cacheMap.get(pieceType)
    }
}
