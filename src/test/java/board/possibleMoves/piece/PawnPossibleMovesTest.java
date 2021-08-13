package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.Position;
import board.pieces.Knight;
import board.pieces.Pawn;
import board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnPossibleMovesTest
{

    private OptimizedBoard optimizedBoard;
    private Piece          whitePawn;
    private Piece          blackPawn;


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
        Position whitePawnPosition = new Position('a', 2);
        optimizedBoard.addPiece(whitePawnPosition, whitePawn);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 2;
    }

    @Test
    public void blockedWhitePawn()
    {
        Position whitePawnPosition = new Position('b', 2);
        Position blackPawnPosition = new Position('b', 3);
        optimizedBoard.addPiece(whitePawnPosition, whitePawn);
        optimizedBoard.addPiece(blackPawnPosition, blackPawn);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 0;
    }

    @Test
    public void fourPossibleMovesPawn()
    {
        Position whitePawnPosition        = new Position('b', 2);
        Position upLeftBlackPawnPosition  = new Position('a', 3);
        Position upRightBlackPawnPosition = new Position('c', 3);

        optimizedBoard.addPiece(whitePawnPosition, whitePawn);
        optimizedBoard.addPiece(upLeftBlackPawnPosition, new Pawn(false));
        optimizedBoard.addPiece(upRightBlackPawnPosition, new Pawn(false));

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 4;
    }

    @Test
    public void threePossibleMovesPawn()
    {
        Position whitePawnPosition        = new Position('b', 3);
        Position upLeftBlackPawnPosition  = new Position('a', 4);
        Position upRightBlackPawnPosition = new Position('c', 4);

        optimizedBoard.addPiece(whitePawnPosition, whitePawn);
        optimizedBoard.addPiece(upLeftBlackPawnPosition, new Pawn(false));
        optimizedBoard.addPiece(upRightBlackPawnPosition, new Pawn(false));

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 3;
    }

    @Test
    public void pawnPromotion()
    {
        Position whitePawnPosition = new Position('a', 7);

        optimizedBoard.addPiece(whitePawnPosition, whitePawn);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 4;
    }


    @Test
    public void blackPawnPromotion()
    {
        Position blackPawnPosition = new Position('a', 2);

        optimizedBoard.addPiece(blackPawnPosition, blackPawn);
        optimizedBoard.setWhiteToMove(false);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 4;
    }

    @Test
    public void blackPawnPromotions()
    {
        Position blackPawnPosition = new Position('b',2);
        Position knightPosition = new Position('a',1);
        Position secondKnightPosition = new Position('c',1);
        optimizedBoard.addPiece(blackPawnPosition,blackPawn);
        optimizedBoard.addPiece(knightPosition,new Knight(true));
        optimizedBoard.addPiece(secondKnightPosition, new Knight(true));

        optimizedBoard.setWhiteToMove(false);
        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 12;
    }



}
