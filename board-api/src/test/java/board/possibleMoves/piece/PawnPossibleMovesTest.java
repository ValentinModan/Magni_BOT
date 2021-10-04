package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.Position;
import board.moves.calculator.pieces.PawnMoveCalculator;
import board.piecees.Knight;
import board.piecees.Pawn;
import board.piecees.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnPossibleMovesTest
{

    private OptimizedBoard optimizedBoard;
    private Piece          whitePawn;
    private Piece          blackPawn;

    private PawnMoveCalculator pawnMoveCalculator = new PawnMoveCalculator();


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


        assert pawnMoveCalculator.computeMoves(optimizedBoard, whitePawnPosition).size() == 2;
    }

    @Test
    public void blockedWhitePawn()
    {
        Position whitePawnPosition = new Position('b', 2);
        Position blackPawnPosition = new Position('b', 3);
        optimizedBoard.addPiece(whitePawnPosition, whitePawn);
        optimizedBoard.addPiece(blackPawnPosition, blackPawn);


        assert pawnMoveCalculator.computeMoves(optimizedBoard, whitePawnPosition).size() == 0;
        optimizedBoard.nextTurn();
        assert pawnMoveCalculator.computeMoves(optimizedBoard, blackPawnPosition).size() == 0;
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


        assert pawnMoveCalculator.computeMoves(optimizedBoard,whitePawnPosition).size() == 4;
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


        assert pawnMoveCalculator.computeMoves(optimizedBoard,whitePawnPosition).size() == 3;
    }

    @Test
    public void pawnPromotion()
    {
        Position whitePawnPosition = new Position('a', 7);

        optimizedBoard.addPiece(whitePawnPosition, whitePawn);



        assert pawnMoveCalculator.computeMoves(optimizedBoard,whitePawnPosition).size() == 4;
    }


    @Test
    public void blackPawnPromotion()
    {
        Position blackPawnPosition = new Position('a', 2);

        optimizedBoard.addPiece(blackPawnPosition, blackPawn);
        optimizedBoard.setWhiteToMove(false);

        assert pawnMoveCalculator.computeMoves(optimizedBoard,blackPawnPosition).size() == 4;
    }

    @Test
    public void blackPawnPromotions()
    {
        Position blackPawnPosition    = new Position('b', 2);
        Position knightPosition       = new Position('a', 1);
        Position secondKnightPosition = new Position('c', 1);
        optimizedBoard.addPiece(blackPawnPosition, blackPawn);
        optimizedBoard.addPiece(knightPosition, new Knight(true));
        optimizedBoard.addPiece(secondKnightPosition, new Knight(true));

        optimizedBoard.setWhiteToMove(false);


        assert pawnMoveCalculator.computeMoves(optimizedBoard,blackPawnPosition).size() == 12;
    }


}
