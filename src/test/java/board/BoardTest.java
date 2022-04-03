package board;

import board.moves.movetypes.Move;
import board.moves.MoveConvertor;
import board.moves.calculator.pieces.PawnMoveCalculator;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.pieces.PieceType;
import helper.resource.DepthCalculator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Test;
import pieces.PieceFactory;

import static board.ColorEnum.WHITE;
import static board.PositionEnum.A2;
import static board.pieces.PieceType.PAWN;
import static org.junit.jupiter.api.Assertions.*;
import static pieces.PieceFactory.createPiece;

class BoardTest
{
    @Test
    void moveThenUndoMove()
    {
        Board board = new Board();
        Board originalBoard = new Board();

        BoardSetup.setupBoard(originalBoard);
        BoardSetup.setupBoard(board);

        assertEquals(board, originalBoard);
        Move move = MoveConvertor.stringToMove("d2d4");

        board.move(move);

        board.undoMove(move);

        assertEquals(board, originalBoard);

    }

    @Test
    void twoBoardWithSamePosition()
    {
        Board board = new Board();
        Board secondBoard = new Board();

        board.addPiece(A2, createPiece(WHITE, PAWN));
        secondBoard.addPiece(A2, createPiece(WHITE, PAWN));

        assertEquals(board, secondBoard);
    }

    @Test
    void firstMove()
    {
        Board board = new Board();
        board.addPiece(A2, createPiece(WHITE, PAWN));

        assertEquals(2, PawnMoveCalculator.getInstance().computeMoves(board, A2.getPosition()).size());
    }

    @Test
    void singleMoveTest()
    {
        Board board = new Board();

        BoardSetup.setupBoard(board);
        assertEquals(20, DepthCalculator.possibleMoves(board, 1));
    }

    @Test
    void twoMovesTest()
    {
        Board board = new Board();
        BoardSetup.setupBoard(board);

        assertEquals(400, DepthCalculator.possibleMoves(board, 2));
    }

    @Test
    void threeMovesTest()
    {
        Board board = new Board();

        BoardSetup.setupBoard(board);

        int result = DepthCalculator.possibleMoves(board, 3);
        assert result == 8902;
    }

    @Test
    void fourMovesTest()
    {
        Board board = new Board();

        BoardSetup.setupBoard(board);
        int result = DepthCalculator.possibleMoves(board, 4);

        assertEquals(197281, result);
    }
}