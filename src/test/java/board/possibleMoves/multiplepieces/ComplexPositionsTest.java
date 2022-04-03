package board.possibleMoves.multiplepieces;

import board.Board;
import board.ColorEnum;
import board.Position;
import board.PositionEnum;
import board.moves.calculator.pieces.QueenMoveCalculator;
import board.pieces.PieceType;
import board.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.PieceFactory;

import static board.ColorEnum.BLACK;
import static board.ColorEnum.WHITE;
import static board.PositionEnum.*;
import static board.pieces.PieceType.QUEEN;


public class ComplexPositionsTest
{

    Board board;

    QueenMoveCalculator queenMoveCalculator = QueenMoveCalculator.getInstance();

    @BeforeEach
    public void setUp()
    {
        board = new Board();
    }

    //TODO: use fen position instead
    @Test
    public void threeQueensEach() throws Exception
    {
        board.addPiece(PieceFactory.createPiece(WHITE, QUEEN), B3, C5, G7);
        board.addPiece(PieceFactory.createPiece(BLACK, QUEEN), B7, D5, G2);

        Assertions.assertEquals(19, queenMoveCalculator.computeMoves(board, B3.getPosition()).size());
        Assertions.assertEquals(21, queenMoveCalculator.computeMoves(board, C5.getPosition()).size());
        Assertions.assertEquals(21, queenMoveCalculator.computeMoves(board, G7.getPosition()).size());
        Assertions.assertEquals(14, queenMoveCalculator.computeMoves(board, B7.getPosition()).size());
        Assertions.assertEquals(20, queenMoveCalculator.computeMoves(board, D5.getPosition()).size());
        Assertions.assertEquals(18, queenMoveCalculator.computeMoves(board, G2.getPosition()).size());


    }
}
