package board.possibleMoves;

import board.OptimizedBoard;
import board.Position;
import board.pieces.Pawn;
import board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnPossibleMovesTest {

    private OptimizedBoard optimizedBoard;
    private Piece whitePawn;
    private Piece blackPawn;


    @BeforeEach
    private void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        whitePawn = new Pawn();
        blackPawn = new Pawn(false);
    }

    @Test
    public void singleWhitePawn()
    {
        Position whitePawnPosition = new Position('a',2);
        optimizedBoard.addPiece(whitePawnPosition, whitePawn);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() ==2;
    }

    @Test
    public void blockedWhitePawn()
    {
        Position whitePawnPosition = new Position('b',2);
        Position blackPawnPosition = new Position('b',3);
        optimizedBoard.addPiece(whitePawnPosition, whitePawn);

    }
}
