import board.OptimizedBoard;
import board.Position;
import board.piecees.King;
import board.piecees.Pawn;
import board.piecees.Rook;
import game.kingcheck.attacked.KingSafety;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingSafetyTest
{

    OptimizedBoard optimizedBoard;

    @BeforeEach
    void setUp()
    {
        optimizedBoard = new OptimizedBoard();
    }

    @Test
    void singleRookAttacker()
    {
        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        optimizedBoard.addPiece(whiteKingPosition, whiteKing);

        Rook     blackRook         = new Rook(false);
        Position blackRookPosition = new Position('d', 1);

        optimizedBoard.addPiece(blackRookPosition, blackRook);

        Assertions.assertEquals(1, KingSafety.getNumberOfAttackers(optimizedBoard));
    }


    @Test
    void pawnAttacker()
    {
        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        optimizedBoard.addPiece(whiteKingPosition, whiteKing);

        Pawn     blackPawn         = new Pawn(false);
        Position blackPawnPosition = new Position('b', 2);

        optimizedBoard.addPiece(blackPawnPosition, blackPawn);

        assertEquals(1, KingSafety.getNumberOfAttackers(optimizedBoard));
    }

    @Test
    void twoAttackers()
    {
        King     whiteKing         = new King(true);
        Position whiteKingPosition = new Position('a', 1);

        optimizedBoard.addPiece(whiteKingPosition, whiteKing);

        Pawn     blackPawn         = new Pawn(false);
        Position blackPawnPosition = new Position('b', 2);

        optimizedBoard.addPiece(blackPawnPosition, blackPawn);


        Rook     blackRook         = new Rook(false);
        Position blackRookPosition = new Position('d', 1);

        optimizedBoard.addPiece(blackRookPosition, blackRook);

        assertEquals(2, KingSafety.getNumberOfAttackers(optimizedBoard));
    }


}