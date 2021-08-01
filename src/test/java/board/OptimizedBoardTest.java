package board;

import board.moves.Move;
import board.moves.MoveConvertor;
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

        assertEquals(board,originalBoard);
        Move move = MoveConvertor.toMove("d2d4");

        board.move(move);

        board.undoMove(move);

       assertEquals(board,originalBoard);

    }

    @Test
    void singlePieceTest()
    {
        OptimizedBoard board = new OptimizedBoard();
        OptimizedBoard originalBoard = new OptimizedBoard();

        Piece piece = new Pawn(true);
        Piece secondPawn = new Pawn(true);
        Position position = new Position('a',2);
        Position position1 = new Position('a',2);

        board.addPiece(position,piece);

        originalBoard.addPiece(position1,secondPawn);

        assertEquals(board,originalBoard);
    }
}