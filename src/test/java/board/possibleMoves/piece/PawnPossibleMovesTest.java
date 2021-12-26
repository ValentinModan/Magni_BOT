package board.possibleMoves.piece;

import board.Board;
import board.Position;
import board.moves.calculator.pieces.PawnMoveCalculator;
import board.pieces.Knight;
import board.pieces.Pawn;
import board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnPossibleMovesTest
{

    private Board board;
    private Piece whitePawn;
    private Piece          blackPawn;

    private PawnMoveCalculator pawnMoveCalculator = new PawnMoveCalculator();


    @BeforeEach
    private void setUp()
    {
        board = new Board();
        whitePawn = new Pawn();
        blackPawn = new Pawn(false);
    }

    @Test
    public void singleWhitePawn()
    {
        Position whitePawnPosition = new Position('a', 2);
        board.addPiece(whitePawnPosition, whitePawn);


        assert pawnMoveCalculator.computeMoves(board, whitePawnPosition).size() == 2;
    }

    @Test
    public void blockedWhitePawn()
    {
        Position whitePawnPosition = new Position('b', 2);
        Position blackPawnPosition = new Position('b', 3);
        board.addPiece(whitePawnPosition, whitePawn);
        board.addPiece(blackPawnPosition, blackPawn);


        assert pawnMoveCalculator.computeMoves(board, whitePawnPosition).size() == 0;
        board.nextTurn();
        assert pawnMoveCalculator.computeMoves(board, blackPawnPosition).size() == 0;
    }

    @Test
    public void fourPossibleMovesPawn()
    {
        Position whitePawnPosition        = new Position('b', 2);
        Position upLeftBlackPawnPosition  = new Position('a', 3);
        Position upRightBlackPawnPosition = new Position('c', 3);

        board.addPiece(whitePawnPosition, whitePawn);
        board.addPiece(upLeftBlackPawnPosition, new Pawn(false));
        board.addPiece(upRightBlackPawnPosition, new Pawn(false));


        assert pawnMoveCalculator.computeMoves(board, whitePawnPosition).size() == 4;
    }

    @Test
    public void threePossibleMovesPawn()
    {
        Position whitePawnPosition        = new Position('b', 3);
        Position upLeftBlackPawnPosition  = new Position('a', 4);
        Position upRightBlackPawnPosition = new Position('c', 4);

        board.addPiece(whitePawnPosition, whitePawn);
        board.addPiece(upLeftBlackPawnPosition, new Pawn(false));
        board.addPiece(upRightBlackPawnPosition, new Pawn(false));


        assert pawnMoveCalculator.computeMoves(board, whitePawnPosition).size() == 3;
    }

    @Test
    public void pawnPromotion()
    {
        Position whitePawnPosition = new Position('a', 7);

        board.addPiece(whitePawnPosition, whitePawn);



        assert pawnMoveCalculator.computeMoves(board, whitePawnPosition).size() == 4;
    }


    @Test
    public void blackPawnPromotion()
    {
        Position blackPawnPosition = new Position('a', 2);

        board.addPiece(blackPawnPosition, blackPawn);
        board.setWhiteToMove(false);

        assert pawnMoveCalculator.computeMoves(board, blackPawnPosition).size() == 4;
    }

    @Test
    public void blackPawnPromotions()
    {
        Position blackPawnPosition    = new Position('b', 2);
        Position knightPosition       = new Position('a', 1);
        Position secondKnightPosition = new Position('c', 1);
        board.addPiece(blackPawnPosition, blackPawn);
        board.addPiece(knightPosition, new Knight(true));
        board.addPiece(secondKnightPosition, new Knight(true));

        board.setWhiteToMove(false);


        assert pawnMoveCalculator.computeMoves(board, blackPawnPosition).size() == 12;
    }


}
