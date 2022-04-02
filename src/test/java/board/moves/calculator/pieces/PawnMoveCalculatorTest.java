package board.moves.calculator.pieces;

import board.Board;
import board.ColorEnum;
import board.Position;
import board.PositionEnum;
import board.moves.MoveConvertor;
import board.pieces.Pawn;
import board.pieces.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.PieceFactory;

import static board.ColorEnum.BLACK;
import static board.ColorEnum.WHITE;
import static board.PositionEnum.*;
import static board.pieces.PieceType.PAWN;
import static org.junit.jupiter.api.Assertions.*;
import static pieces.PieceFactory.createPiece;

class PawnMoveCalculatorTest
{
    Board board;

    PawnMoveCalculator pawnMoveCalculator;

    @BeforeEach
    public void setUp()
    {
        board = new Board();
        pawnMoveCalculator = PawnMoveCalculator.getInstance();
    }

    @Test
    void enPassant()
    {
        board.addPiece(D5, createPiece(WHITE, PAWN));
        board.addPiece(C7, createPiece(BLACK, PAWN));

        board.setWhiteToMove(false);
        board.move(MoveConvertor.moveFrom(C7, C5));
        board.setWhiteToMove(true);
        int result = pawnMoveCalculator.computeMoves(board, D5.getPosition()).size();

        assertEquals(2, result);

        board.move(MoveConvertor.stringToMove("d5c6"));

        assertEquals(0, board.getTakenPiecesMap().size());
        assertEquals(1, board.getMovingPiecesMap().size());
    }
}