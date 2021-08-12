package board.possibleMoves;

import board.OptimizedBoard;
import board.Position;
import board.pieces.Knight;
import board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightPossibleMovesTest {

    private OptimizedBoard optimizedBoard;


    @BeforeEach
    private void setUP()
    {
        optimizedBoard = new OptimizedBoard();
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

        Piece whiteKnight = new Knight(true);
        Piece blackKnight = new Knight(false);

        Position whiteKnightPosition = new Position('a',8);
        Position blackKnightPosition = new Position('h',1);

        optimizedBoard.addPiece(whiteKnightPosition,whiteKnight);
        optimizedBoard.addPiece(blackKnightPosition, blackKnight);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() ==2;
    }

    @Test
    public void twoWhiteKnightsPossibleMoves() {

    }


}
