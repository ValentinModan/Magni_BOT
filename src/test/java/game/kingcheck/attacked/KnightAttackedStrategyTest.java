package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.pieces.King;
import board.pieces.Knight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightAttackedStrategyTest
{
    OptimizedBoard optimizedBoard;

    @BeforeEach
    void setUp()
    {
        optimizedBoard = new OptimizedBoard();
    }

    @Test
    void isAttackingTheKing()
    {
        Knight   blackKnight         = new Knight(false);
        Position blackKnightPosition = new Position('b', 3);

        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        optimizedBoard.addPiece(blackKnightPosition, blackKnight);
        optimizedBoard.addPiece(whiteKingPosition, whiteKing);

        KnightAttackedStrategy knightAttackedStrategy = new KnightAttackedStrategy();

        assertTrue(knightAttackedStrategy.isAttackingTheKing(optimizedBoard));
    }

    @Test
    void knightIsNotAttackingTheKing()
    {
        Knight   blackKnight         = new Knight(false);
        Position blackKnightPosition = new Position('b', 4);

        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        optimizedBoard.addPiece(blackKnightPosition, blackKnight);
        optimizedBoard.addPiece(whiteKingPosition, whiteKing);

        KnightAttackedStrategy knightAttackedStrategy = new KnightAttackedStrategy();

        assertFalse(knightAttackedStrategy.isAttackingTheKing(optimizedBoard));
    }

    @Test
    void knightIsNotAttackingTheKing2()
    {
        Knight   blackKnight         = new Knight(false);
        Position blackKnightPosition = new Position('g', 4);

        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        optimizedBoard.addPiece(blackKnightPosition, blackKnight);
        optimizedBoard.addPiece(whiteKingPosition, whiteKing);

        KnightAttackedStrategy knightAttackedStrategy = new KnightAttackedStrategy();

        assertFalse(knightAttackedStrategy.isAttackingTheKing(optimizedBoard));
    }
}