package board.possibleMoves.piece;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.calculator.pieces.QueenMoveCalculator;
import board.pieces.Piece;
import board.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueenPossibleMovesTest
{
    Board board;
    Piece whiteQueen;
    Piece               blackQueen;
    Piece               secondWhiteQueen;
    QueenMoveCalculator queenMoveCalculator = new QueenMoveCalculator();

    @BeforeEach
    private void setUp()
    {
        board = new Board();
        whiteQueen = new Queen(true);
        blackQueen = new Queen(false);
        secondWhiteQueen = new Queen(true);
    }

    @Test
    public void singleWhiteQueen() throws Exception
    {
        Position queenPosition = new Position('d', 4);

        board.addPiece(queenPosition, whiteQueen);
        int result = queenMoveCalculator.computeMoves(board, queenPosition).size();

        Assertions.assertEquals(27, result);

    }

    @Test
    public void twoOppositeQueens() throws Exception
    {
        Position whiteQueenPosition = new Position('d', 4);
        Position blackQueenPosition = new Position('e', 5);
        board.addPiece(whiteQueenPosition, whiteQueen);
        board.addPiece(blackQueenPosition, blackQueen);

        int result = queenMoveCalculator.computeMoves(board, whiteQueenPosition).size();
        Assertions.assertEquals(24,result);

        result = queenMoveCalculator.computeMoves(board, whiteQueenPosition).size();
        Assertions.assertEquals(24,result);

    }

    @Test
    public void surroundedQueen() throws Exception
    {

        //maybe use queen direction movements instead
        board.addPiece(new Position('d', 4), whiteQueen);
        board.addPiece(new Position('d', 4).move(Movement.UP), blackQueen);
        board.addPiece(new Position('d', 4).move(Movement.UP_LEFT), blackQueen);
        board.addPiece(new Position('d', 4).move(Movement.LEFT), blackQueen);
        board.addPiece(new Position('d', 4).move(Movement.LEFT_DOWN), blackQueen);
        board.addPiece(new Position('d', 4).move(Movement.DOWN), blackQueen);
        board.addPiece(new Position('d', 4).move(Movement.DOWN_RIGHT), blackQueen);
        board.addPiece(new Position('d', 4).move(Movement.RIGHT), blackQueen);
        board.addPiece(new Position('d', 4).move(Movement.UP_RIGHT), blackQueen);


       int result =  queenMoveCalculator.computeMoves(board, new Position('d', 4)).size();


        Assertions.assertEquals(8,result);
    }
}
