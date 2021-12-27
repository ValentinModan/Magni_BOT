package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.pieces.King;
import board.pieces.Pawn;
import board.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingSafetyTest
{

    Board board;

    @BeforeEach
    void setUp()
    {
        board = new Board();
    }

    @Test
    void singleRookAttacker()
    {
        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        board.addPiece(whiteKingPosition, whiteKing);

        Rook     blackRook         = new Rook(false);
        Position blackRookPosition = new Position('d', 1);

        board.addPiece(blackRookPosition, blackRook);

        assertTrue(KingSafety.isTheKingAttacked(board));
    }


    @Test
    void pawnAttacker()
    {
        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        board.addPiece(whiteKingPosition, whiteKing);

        Pawn     blackPawn         = new Pawn(false);
        Position blackPawnPosition = new Position('b', 2);

        board.addPiece(blackPawnPosition, blackPawn);

        assertTrue(KingSafety.isTheKingAttacked(board));
    }

    @Test
    void twoAttackers()
    {
        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        board.addPiece(whiteKingPosition, whiteKing);

        Pawn     blackPawn         = new Pawn(false);
        Position blackPawnPosition = new Position('b', 2);

        board.addPiece(blackPawnPosition, blackPawn);


        Rook     blackRook         = new Rook(false);
        Position blackRookPosition = new Position('d', 1);

        board.addPiece(blackRookPosition, blackRook);

        assertTrue(KingSafety.isTheKingAttacked(board));
    }


}