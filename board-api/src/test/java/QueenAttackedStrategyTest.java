import board.OptimizedBoard;
import board.Position;
import board.piecees.King;
import board.piecees.Queen;
import game.kingcheck.attacked.QueenAttackedStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenAttackedStrategyTest
{
    OptimizedBoard optimizedBoard;

    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
    }

    @Test
    void singleQueenDiagonalAttack()
    {
        Queen blackQueen = new Queen(false);
        Position blackQueenPosition = new Position('h',8);

        King whiteKing = new King(true);
        Position whiteKingPosition = new Position('a',1);

        optimizedBoard.addPiece(blackQueenPosition,blackQueen);
        optimizedBoard.addPiece(whiteKingPosition,whiteKing);

        QueenAttackedStrategy queenAttackedStrategy = new QueenAttackedStrategy();

        assertTrue(queenAttackedStrategy.isAttackingTheKing(optimizedBoard));
    }


    @Test
    void singleQueenLineAttack()
    {
        Queen blackQueen = new Queen(false);
        Position blackQueenPosition = new Position('a',8);

        King whiteKing = new King(true);
        Position whiteKingPosition = new Position('a',1);

        optimizedBoard.addPiece(blackQueenPosition,blackQueen);
        optimizedBoard.addPiece(whiteKingPosition,whiteKing);

        QueenAttackedStrategy queenAttackedStrategy = new QueenAttackedStrategy();

        assertTrue(queenAttackedStrategy.isAttackingTheKing(optimizedBoard));
    }
}