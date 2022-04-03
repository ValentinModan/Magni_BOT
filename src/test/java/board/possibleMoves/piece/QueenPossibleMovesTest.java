package board.possibleMoves.piece;

import board.Board;
import board.Position;
import board.PositionEnum;
import board.moves.Movement;
import board.moves.calculator.pieces.QueenMoveCalculator;
import board.pieces.Piece;
import board.pieces.Queen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static board.PositionEnum.D4;

public class QueenPossibleMovesTest
{
    Board board;
    Piece whiteQueen;
    Piece blackQueen;
    Piece secondWhiteQueen;
    QueenMoveCalculator queenMoveCalculator = QueenMoveCalculator.getInstance();

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
        Assertions.assertEquals(24, result);

        result = queenMoveCalculator.computeMoves(board, whiteQueenPosition).size();
        Assertions.assertEquals(24, result);

    }

    @Test
    public void surroundedQueen() throws Exception
    {

        //maybe use queen direction movements instead
        board.addPiece(D4, whiteQueen);
        board.addPiece(blackQueen,
                       D4.move(Movement.UP),
                       D4.move(Movement.UP_LEFT),
                       D4.move(Movement.LEFT),
                       D4.move(Movement.LEFT_DOWN),
                       D4.move(Movement.DOWN),
                       D4.move(Movement.DOWN_RIGHT),
                       D4.move(Movement.RIGHT),
                       D4.move(Movement.UP_RIGHT));

        Assertions.assertEquals(8, queenMoveCalculator.computeMoves(board, D4.getPosition()).size());
    }
}
