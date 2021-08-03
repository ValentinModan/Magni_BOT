package board;

import board.moves.Move;
import board.moves.MoveConvertor;
import board.moves.Movement;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptimizedBoardTest {


    @Test
    void moveThenUndoMove() {
        OptimizedBoard board = new OptimizedBoard();
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
    void singlePieceTest() {
        OptimizedBoard board = new OptimizedBoard();
        OptimizedBoard originalBoard = new OptimizedBoard();

        Piece piece = new Pawn(true);
        Piece secondPawn = new Pawn(true);
        Position position = new Position('a', 2);
        Position position1 = new Position('a', 2);

        board.addPiece(position, piece);

        originalBoard.addPiece(position1, secondPawn);

        assertEquals(board, originalBoard);
    }

    @Test
    void blackPieceMove() {
        OptimizedBoard board = new OptimizedBoard();
        BoardSetup.setupBoard(board);
        board.setWhiteToMove(false);

        Move move = MoveConvertor.toMove("b8c6");


       board.computePossibleMoves();
        assert board.isValidMove(move);

        System.out.println(board.getPossibleMoves().size());
    }

    @Test
    void firstMove() {
        OptimizedBoard board = new OptimizedBoard();

        board.addPiece(new Position(2, 2), new Pawn(true));

        board.computePossibleMoves();

        assertEquals( 2,board.getPossibleMoves().size());
    }
}