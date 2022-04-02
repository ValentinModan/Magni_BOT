package board

import board.pieces.Piece
import board.pieces.PieceType

import java.util.stream.Collectors

class BoardHelper {
    static boolean opponentHas(Board board, PieceType pieceType) {
        if (board.getTakenPiecesMap().values().stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(pieceType)) {
            return true
        }
        return false
    }
}
