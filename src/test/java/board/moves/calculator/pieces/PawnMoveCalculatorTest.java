package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.MoveConvertor;
import board.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnMoveCalculatorTest
{
    Board board;

    PawnMoveCalculator pawnMoveCalculator;

    @BeforeEach
    public void setUp()
    {
        board = new Board();
        pawnMoveCalculator = new PawnMoveCalculator();
    }

    @Test
    void enPassant()
    {
        Position whitePawnPosition = new Position('d', 5);
        Pawn     whitePawn         = new Pawn();

        Position blackPawnPosition = new Position('c', 7);
        Pawn     blackPawn         = new Pawn(false);

        board.addPiece(whitePawnPosition, whitePawn);
        board.addPiece(blackPawnPosition, blackPawn);

        board.setWhiteToMove(false);
        board.move(MoveConvertor.stringToMove("c7c5"));
        board.setWhiteToMove(true);
        int result = pawnMoveCalculator.computeMoves(board, whitePawnPosition).size();

        assertEquals(2, result);

        board.move(MoveConvertor.stringToMove("d5c6"));

        assertEquals(0, board.getTakenPiecesMap().size());
        assertEquals(1, board.getMovingPiecesMap().size());
    }
}