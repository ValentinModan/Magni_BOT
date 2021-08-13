package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.Position;
import board.pieces.Knight;
import board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightPossibleMovesTest {

    private OptimizedBoard optimizedBoard;
    private Piece whiteKnight;
    private Piece blackKnight;
    private Piece secondWhiteKnight;


    @BeforeEach
    private void setUP()
    {
        optimizedBoard = new OptimizedBoard();
        whiteKnight = new Knight(true);
        blackKnight = new Knight(false);
        secondWhiteKnight = new Knight(true);
    }

    @Test
    public void singleKnightCornerPossibleMoves() {
        optimizedBoard.setWhiteToMove(true);
        Piece knight = new Knight(true);

        optimizedBoard.addPiece(new Position('a', 1), knight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 2;
    }

    @Test
    public void twoOppositeColorKnightsCornerPossibleMoves() {



        Position whiteKnightPosition = new Position('a',8);
        Position blackKnightPosition = new Position('h',1);

        optimizedBoard.addPiece(whiteKnightPosition,whiteKnight);
        optimizedBoard.addPiece(blackKnightPosition, blackKnight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() ==2;
    }

    @Test
    public void twoWhiteKnightsPossibleMoves() {

        Position firstWhitePosition = new Position('d',4);
        Position secondWhitePosition = new Position('f',5);

        optimizedBoard.addPiece(firstWhitePosition,whiteKnight);
        optimizedBoard.addPiece(secondWhitePosition, secondWhiteKnight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 14;
    }

    @Test
    public void twoOppositeAttackingKnights()
    {
        Position whitePosition = new Position('d',4);
        Position blackPosition = new Position('f',5);

        optimizedBoard.addPiece(whitePosition,whiteKnight);
        optimizedBoard.addPiece(blackPosition, blackKnight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 8;
    }

    @Test
    public void whiteToMoveWithNoWhiteKnights()
    {
       Piece thirdKnight = new Knight(true);

        Position firstWhitePosition = new Position('d',4);
        Position secondWhitePosition = new Position('f',5);
        Position thirdWhitePosition = new Position('f',3);


        optimizedBoard.addPiece(firstWhitePosition,whiteKnight);
        optimizedBoard.addPiece(secondWhitePosition, secondWhiteKnight);
        optimizedBoard.addPiece(thirdWhitePosition, thirdKnight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 20;
    }


}
