package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.pieces.King;
import board.pieces.Knight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightAttackStrategyTest
{
    Board board;

    @BeforeEach
    void setUp()
    {
        board = new Board();
    }

    @Test
    void isAttackingTheKing()
    {
        Knight   blackKnight         = new Knight(false);
        Position blackKnightPosition = new Position('b', 3);

        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        board.addPiece(blackKnightPosition, blackKnight);
        board.addPiece(whiteKingPosition, whiteKing);

        KnightAttackStrategy knightAttackedStrategy = new KnightAttackStrategy();

        assertTrue(knightAttackedStrategy.isAttackingTheKing(board));
    }

    @Test
    void knightIsNotAttackingTheKing()
    {
        Knight   blackKnight         = new Knight(false);
        Position blackKnightPosition = new Position('b', 4);

        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        board.addPiece(blackKnightPosition, blackKnight);
        board.addPiece(whiteKingPosition, whiteKing);

        KnightAttackStrategy knightAttackedStrategy = new KnightAttackStrategy();

        assertFalse(knightAttackedStrategy.isAttackingTheKing(board));
    }

    @Test
    void knightIsNotAttackingTheKing2()
    {
        Knight   blackKnight         = new Knight(false);
        Position blackKnightPosition = new Position('g', 4);

        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        board.addPiece(blackKnightPosition, blackKnight);
        board.addPiece(whiteKingPosition, whiteKing);

        KnightAttackStrategy knightAttackedStrategy = new KnightAttackStrategy();

        assertFalse(knightAttackedStrategy.isAttackingTheKing(board));
    }
}