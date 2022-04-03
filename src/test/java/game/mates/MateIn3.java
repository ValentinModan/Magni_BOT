package game.mates;

import board.Board;
import board.Position;
import board.pieces.King;
import board.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import pieces.PieceFactory;

import static board.ColorEnum.BLACK;
import static board.ColorEnum.WHITE;
import static board.PositionEnum.*;
import static board.PositionEnum.A6;
import static board.pieces.PieceType.KING;
import static board.pieces.PieceType.ROOK;

public class MateIn3
{
    Board board;

    @BeforeEach
    public void setUp()
    {
        board = new Board();

        board.addPiece(A1, PieceFactory.createPiece(WHITE, KING));
        board.addPiece(G7, PieceFactory.createPiece(BLACK, KING));
        board.addPiece(B5, PieceFactory.createPiece(WHITE, ROOK));
        board.addPiece(A6, PieceFactory.createPiece(WHITE, ROOK));
    }


}
