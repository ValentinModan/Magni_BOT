package board;

import board.moves.Move;
import board.moves.MoveConvertor;
import board.moves.Movement;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.possibleMoves.resource.DepthCalculator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class OptimizedBoardTest
{


    @Test
    void moveThenUndoMove()
    {
        OptimizedBoard board         = new OptimizedBoard();
        OptimizedBoard originalBoard = new OptimizedBoard();

        BoardSetup.setupBoard(originalBoard);
        BoardSetup.setupBoard(board);

        assertEquals(board, originalBoard);
        Move move = MoveConvertor.toMove("d2d4");

        board.move(move);

        board.undoMove(move);

        assertEquals(board, originalBoard);

    }

    @Test
    void singlePieceTest()
    {
        OptimizedBoard board         = new OptimizedBoard();
        OptimizedBoard originalBoard = new OptimizedBoard();

        Piece    piece      = new Pawn(true);
        Piece    secondPawn = new Pawn(true);
        Position position   = new Position('a', 2);
        Position position1  = new Position('a', 2);

        board.addPiece(position, piece);

        originalBoard.addPiece(position1, secondPawn);

        assertEquals(board, originalBoard);
    }

    @Test
    void blackPieceMove()
    {
        OptimizedBoard board = new OptimizedBoard();
        BoardSetup.setupBoard(board);
        board.setWhiteToMove(false);

        //Move move = MoveConvertor.toMove("b8c6");


        board.computePossibleMoves();
        System.out.println(board.getPossibleMoves());
        // assert board.isValidMove(move);

        System.out.println(board.getPossibleMoves().size());
    }

    @Test
    void firstMove()
    {
        OptimizedBoard board = new OptimizedBoard();

        board.addPiece(new Position(2, 2), new Pawn(true));

        board.computePossibleMoves();

        assertEquals(2, board.getPossibleMoves().size());
    }

    @Test
    void singleMoveTest()
    {
        OptimizedBoard board = new OptimizedBoard();

        BoardSetup.setupBoard(board);

        assert DepthCalculator.possibleMoves(board, 1) == 20;
    }

    @Test
    void twoMovesTest()
    {
        OptimizedBoard board = new OptimizedBoard();

        BoardSetup.setupBoard(board);

        int result = DepthCalculator.possibleMoves(board, 2);
        assert result == 400;
    }

    @Test
    void threeMovesTest()
    {
        OptimizedBoard board = new OptimizedBoard();

        BoardSetup.setupBoard(board);

        int result = DepthCalculator.possibleMoves(board, 3);
        assert result == 8902;
    }
    @Test
    void fourMovesTest()
    {
        OptimizedBoard optimizedBoard = new OptimizedBoard();

        BoardSetup.setupBoard(optimizedBoard);
        int result = DepthCalculator.possibleMoves(optimizedBoard, 4);
        assertEquals(197281, result);
    }


    void fiveMovesTest()
    {
        OptimizedBoard optimizedBoard = new OptimizedBoard();

        BoardSetup.setupBoard(optimizedBoard);
        int result = DepthCalculator.possibleMoves(optimizedBoard, 5);
        //  assert result == 197742;
        assertEquals(4865609, result);
    }



    void sixMovesTest()
    {
        OptimizedBoard optimizedBoard = new OptimizedBoard();


        BoardSetup.setupBoard(optimizedBoard);
        int result = DepthCalculator.possibleMoves(optimizedBoard, 6);
        //  assert result == 197742;
        assertEquals(119060324, result);
    }
}