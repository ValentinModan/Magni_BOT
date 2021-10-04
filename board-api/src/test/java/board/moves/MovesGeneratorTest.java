package board.moves;

import board.OptimizedBoard;
import board.Position;
import board.piecees.PieceType;
import board.setup.BoardSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovesGeneratorTest
{

    OptimizedBoard optimizedBoard;


    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        BoardSetup.setupBoard(optimizedBoard);
    }

    @Test
    void makeMoves()
    {
        MovesGenerator.makeMoves(optimizedBoard, "b2b4 b7b5");

        assertSame(optimizedBoard.getMovingPiece(new Position('b', 4)).getPieceType(), PieceType.PAWN);
        assertSame(optimizedBoard.getTakenPiecesMap().get(new Position('b', 5)).getPieceType(), PieceType.PAWN);


    }
}