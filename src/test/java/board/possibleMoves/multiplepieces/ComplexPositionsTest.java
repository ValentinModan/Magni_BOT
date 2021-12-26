package board.possibleMoves.multiplepieces;

import board.Board;
import board.Position;
import board.moves.calculator.pieces.QueenMoveCalculator;
import board.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ComplexPositionsTest
{

    Board board;

    QueenMoveCalculator queenMoveCalculator = new QueenMoveCalculator();

    @BeforeEach
    public void setUp()
    {
        board = new Board();
    }

    @Test
    public void threeQueensEach() throws Exception
    {
        Queen firstWhiteQueen  = new Queen(true);
        Queen secondWhiteQueen = new Queen(true);
        Queen thirdWhiteQueen  = new Queen(true);

        Queen firstBlackQueen  = new Queen(false);
        Queen secondBlackQueen = new Queen(false);
        Queen thirdBlackQueen  = new Queen(false);

        Position firstWhiteQueenPosition  = new Position('b', 3);
        Position secondWhiteQueenPosition = new Position('c', 5);
        Position thirdWhiteQueenPosition  = new Position('g', 7);
        Position firstBlackQueenPosition  = new Position('b', 7);
        Position secondBlackQueenPosition = new Position('d', 5);
        Position thirdBlackQueenPosition  = new Position('g', 2);

        board.addPiece(firstWhiteQueenPosition, firstWhiteQueen);
        board.addPiece(secondWhiteQueenPosition, secondWhiteQueen);
        board.addPiece(thirdWhiteQueenPosition, thirdWhiteQueen);

        board.addPiece(firstBlackQueenPosition, firstBlackQueen);
        board.addPiece(secondBlackQueenPosition, secondBlackQueen);
        board.addPiece(thirdBlackQueenPosition, thirdBlackQueen);

        int result;

        result = queenMoveCalculator.computeMoves(board, firstWhiteQueenPosition).size();

        Assertions.assertEquals(19, result);

        result = queenMoveCalculator.computeMoves(board, secondWhiteQueenPosition).size();

        Assertions.assertEquals(21, result);

        result = queenMoveCalculator.computeMoves(board, thirdWhiteQueenPosition).size();

        Assertions.assertEquals(21, result);

        result = queenMoveCalculator.computeMoves(board, firstBlackQueenPosition).size();

        Assertions.assertEquals(14, result);

        result = queenMoveCalculator.computeMoves(board, secondBlackQueenPosition).size();

        Assertions.assertEquals(20, result);

        result = queenMoveCalculator.computeMoves(board, thirdBlackQueenPosition).size();

        Assertions.assertEquals(18, result);


    }
}
