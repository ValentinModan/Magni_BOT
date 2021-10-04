package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.calculator.pieces.KnightMoveCalculator;
import board.piecees.Knight;
import board.piecees.Piece;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightPossibleMovesTest
{

    private OptimizedBoard optimizedBoard;
    private Piece          whiteKnight;
    private Piece          blackKnight;
    private Piece          secondWhiteKnight;
    private KnightMoveCalculator knightMoveCalculator = new KnightMoveCalculator();


    @BeforeEach
    private void setUP()
    {
        optimizedBoard = new OptimizedBoard();
        whiteKnight = new Knight(true);
        blackKnight = new Knight(false);
        secondWhiteKnight = new Knight(true);
    }

    @Test
    public void singleKnightCornerPossibleMoves()
    {
        optimizedBoard.setWhiteToMove(true);
        Piece knight = new Knight(true);

        optimizedBoard.addPiece(new Position('a', 1), knight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 2;
    }

    @Test
    public void twoOppositeColorKnightsCornerPossibleMoves()
    {
        Position whiteKnightPosition = new Position('a', 8);
        Position blackKnightPosition = new Position('h', 1);

        optimizedBoard.addPiece(whiteKnightPosition, whiteKnight);
        optimizedBoard.addPiece(blackKnightPosition, blackKnight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 2;
    }

    @Test
    public void twoWhiteKnightsPossibleMoves()
    {

        Position firstWhitePosition  = new Position('d', 4);
        Position secondWhitePosition = new Position('f', 5);

        optimizedBoard.addPiece(firstWhitePosition, whiteKnight);
        optimizedBoard.addPiece(secondWhitePosition, secondWhiteKnight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 14;
    }

    @Test
    public void twoOppositeAttackingKnights()
    {
        Position whitePosition = new Position('d', 4);
        Position blackPosition = new Position('f', 5);

        optimizedBoard.addPiece(whitePosition, whiteKnight);
        optimizedBoard.addPiece(blackPosition, blackKnight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 8;
    }

    @Test
    public void whiteToMoveWithNoWhiteKnights() throws Exception
    {
        Piece thirdKnight = new Knight(true);

        Position firstWhitePosition  = new Position('d', 4);
        Position secondWhitePosition = new Position('f', 5);
        Position thirdWhitePosition  = new Position('f', 3);


        optimizedBoard.addPiece(firstWhitePosition, whiteKnight);
        optimizedBoard.addPiece(secondWhitePosition, secondWhiteKnight);
        optimizedBoard.addPiece(thirdWhitePosition, thirdKnight);

        Assertions.assertEquals(6,knightMoveCalculator.computeMoves(optimizedBoard,firstWhitePosition).size());
        Assertions.assertEquals(7,knightMoveCalculator.computeMoves(optimizedBoard,secondWhitePosition).size());
        Assertions.assertEquals(7,knightMoveCalculator.computeMoves(optimizedBoard,thirdWhitePosition).size());
    }


    @Test
    public void whiteSecondMove()
    {
        BoardSetup.setupBoard(optimizedBoard);
        Move move = new Move(new Position('b', 1), new Position('c', 3));
        optimizedBoard.move(move);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 22;
    }


    @Test
    public void blackSecondMove()
    {
        BoardSetup.setupBoard(optimizedBoard);
        optimizedBoard.setWhiteToMove(false);
        Move move = new Move(new Position('b', 8), new Position('c', 6));
        optimizedBoard.move(move);


        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 22;
    }

}
