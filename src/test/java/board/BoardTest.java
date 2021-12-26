package board;

import board.moves.Move;
import board.moves.MoveConvertor;
import board.moves.calculator.pieces.PawnMoveCalculator;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.possibleMoves.resource.DepthCalculator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest
{


    @Test
    void moveThenUndoMove()
    {
        Board board         = new Board();
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
    void singlePieceTest()
    {
        Board board         = new Board();
        Board originalBoard = new Board();

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
        Board board = new Board();
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
        Board    board        = new Board();
        Position pawnPosition = new Position(2, 2);

        board.addPiece(pawnPosition, new Pawn(true));

        PawnMoveCalculator pawnMoveCalculator = new PawnMoveCalculator();

        int moves = pawnMoveCalculator.computeMoves(board, pawnPosition).size();

        assertEquals(2, moves);
    }

    @Test
    void singleMoveTest()
    {
        Board board = new Board();

        BoardSetup.setupBoard(board);

        assert DepthCalculator.possibleMoves(board, 1) == 20;
    }

    @Test
    void twoMovesTest()
    {
        Board board = new Board();

        BoardSetup.setupBoard(board);

        int result = DepthCalculator.possibleMoves(board, 2);
        assert result == 400;
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


    void fiveMovesTest()
    {
        Board board = new Board();

        BoardSetup.setupBoard(board);
        int  n         = 5;
        long startTime = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            int result = DepthCalculator.possibleMoves(board, 5);
        }
        double elapsedTime = (double) (System.nanoTime() - startTime) / 1_000_000_000.0;
        System.out.println(elapsedTime / n);

        //  assert result == 197742;

        // assertEquals(4865609, result);
    }

    // @Test
    void fiveeMovesTest()
    {
        Board board = new Board();


        BoardSetup.setupBoard(board);
        int result = DepthCalculator.possibleMoves(board, 5);
        //  assert result == 197742;
        assertEquals(4865609, result);
    }


   // @Test
    void sixMovesTest()
    {
        Board board = new Board();


        BoardSetup.setupBoard(board);
        int result = DepthCalculator.possibleMoves(board, 6);
        //  assert result == 197742;
        assertEquals(119060324, result);
    }
}