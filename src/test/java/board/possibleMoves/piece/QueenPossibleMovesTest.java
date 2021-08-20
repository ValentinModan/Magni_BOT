package board.possibleMoves.piece;

import board.OptimizedBoard;
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
    OptimizedBoard      optimizedBoard;
    Piece               whiteQueen;
    Piece               blackQueen;
    Piece               secondWhiteQueen;
    QueenMoveCalculator queenMoveCalculator = new QueenMoveCalculator();

    @BeforeEach
    private void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        whiteQueen = new Queen(true);
        blackQueen = new Queen(false);
        secondWhiteQueen = new Queen(true);
    }

    @Test
    public void singleWhiteQueen()
    {
        Position queenPosition = new Position('d', 4);

        optimizedBoard.addPiece(queenPosition, whiteQueen);
        int result = queenMoveCalculator.computeMoves(optimizedBoard, queenPosition).size();

        Assertions.assertEquals(27, result);

    }

    @Test
    public void twoOppositeQueens()
    {
        Position whiteQueenPosition = new Position('d', 4);
        Position blackQueenPosition = new Position('e', 5);
        optimizedBoard.addPiece(whiteQueenPosition, whiteQueen);
        optimizedBoard.addPiece(blackQueenPosition, blackQueen);

        int result = queenMoveCalculator.computeMoves(optimizedBoard,whiteQueenPosition).size();
        Assertions.assertEquals(24,result);

        result = queenMoveCalculator.computeMoves(optimizedBoard,whiteQueenPosition).size();
        Assertions.assertEquals(24,result);

    }

    @Test
    public void surroundedQueen()
    {

        //maybe use queen direction movements instead
        optimizedBoard.addPiece(new Position('d', 4), whiteQueen);
        optimizedBoard.addPiece(new Position('d', 4).move(Movement.UP), blackQueen);
        optimizedBoard.addPiece(new Position('d', 4).move(Movement.UP_LEFT), blackQueen);
        optimizedBoard.addPiece(new Position('d', 4).move(Movement.LEFT), blackQueen);
        optimizedBoard.addPiece(new Position('d', 4).move(Movement.LEFT_DOWN), blackQueen);
        optimizedBoard.addPiece(new Position('d', 4).move(Movement.DOWN), blackQueen);
        optimizedBoard.addPiece(new Position('d', 4).move(Movement.DOWN_RIGHT), blackQueen);
        optimizedBoard.addPiece(new Position('d', 4).move(Movement.RIGHT), blackQueen);
        optimizedBoard.addPiece(new Position('d', 4).move(Movement.UP_RIGHT), blackQueen);


        optimizedBoard.computePossibleMoves();


        assert optimizedBoard.getPossibleMoves().size() == 8;
    }
}
