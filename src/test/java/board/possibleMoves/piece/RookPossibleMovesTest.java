package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;
import board.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RookPossibleMovesTest
{
    OptimizedBoard optimizedBoard;
    Piece          whiteRook;
    Piece          blackRook;
    Piece secondWhiteRook;

    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        whiteRook = new Rook(true);
        blackRook = new Rook(false);
        secondWhiteRook = new Rook(true);
    }

    @Test
    public void singleRook()
    {
        Position cornerPosition = new Position('a',1);

        optimizedBoard.addPiece(cornerPosition,whiteRook);

        optimizedBoard.computePossibleMoves();

       assert  optimizedBoard.getPossibleMoves().size() == 14;
    }

    @Test
    public void trappedRook()
    {
        Position cornerPosition = new Position('a',1);

        optimizedBoard.addPiece(cornerPosition,whiteRook);

        optimizedBoard.addPiece(cornerPosition.move(Movement.UP),blackRook);
        optimizedBoard.addPiece(cornerPosition.move(Movement.RIGHT),blackRook);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 2;
    }
}
