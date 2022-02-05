package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.pieces.King;
import board.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenAttackStrategyTest
{
    Board board;

    @BeforeEach
    public void setUp()
    {
        board = new Board();
    }

    @Test
    void singleQueenDiagonalAttack()
    {
        Queen blackQueen = new Queen(false);
        Position blackQueenPosition = new Position('h',8);

        King whiteKing = new King(true);
        Position whiteKingPosition = new Position('a',1);

        board.addPiece(blackQueenPosition, blackQueen);
        board.addPiece(whiteKingPosition, whiteKing);

        QueenAttackStrategy queenAttackedStrategy = new QueenAttackStrategy();

        assertTrue(queenAttackedStrategy.isAttackingTheKing(board));
    }


    @Test
    void singleQueenLineAttack()
    {
        Queen blackQueen = new Queen(false);
        Position blackQueenPosition = new Position('a',8);

        King whiteKing = new King(true);
        Position whiteKingPosition = new Position('a',1);

        board.addPiece(blackQueenPosition, blackQueen);
        board.addPiece(whiteKingPosition, whiteKing);

        QueenAttackStrategy queenAttackedStrategy = new QueenAttackStrategy();

        assertTrue(queenAttackedStrategy.isAttackingTheKing(board));
    }
}