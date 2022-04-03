package board.possibleMoves.piece;

import board.Board;
import board.Position;
import board.PositionEnum;
import board.moves.calculator.pieces.BishopMoveCalculator;
import board.pieces.Bishop;
import board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static board.PositionEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BishopPossibleMovesTest
{
    Board board;

    Piece whiteBishop;

    Piece blackBishop;

    Piece secondWhiteBishop;

    BishopMoveCalculator bishopMoveCalculator = BishopMoveCalculator.getInstance();

    @BeforeEach
    private void setUp()
    {
        board = new Board();
        whiteBishop = new Bishop(true);
        blackBishop = new Bishop(false);
        secondWhiteBishop = new Bishop(true);
    }

    @Test
    public void singleWhiteBishop() throws Exception
    {
        board.addPiece(A1, whiteBishop);
        int bishopMoves = bishopMoveCalculator.computeMoves(board, A1.getPosition()).size();

        assertEquals(7, bishopMoves);
    }

    @Test
    public void twoAdjacentWhiteBishops() throws Exception
    {
        Position firstWhitePosition = new Position('d', 4);
        Position secondWhitePosition = new Position('e', 5);

        board.addPiece(firstWhitePosition, whiteBishop);
        board.addPiece(secondWhitePosition, secondWhiteBishop);

        int firstBishopMoves = bishopMoveCalculator.computeMoves(board, firstWhitePosition).size();
        int secondBishopMoves = bishopMoveCalculator.computeMoves(board, secondWhitePosition).size();

        assertEquals(firstBishopMoves, 9);
        assertEquals(secondBishopMoves, 9);
    }

    @Test
    public void twoAdjacentOppositeBishops() throws Exception
    {
        board.addPiece(D4, whiteBishop);
        board.addPiece(E5, blackBishop);

        assertEquals(10, bishopMoveCalculator.computeMoves(board, D4.getPosition()).size());
        board.setWhiteToMove(false);
        assertEquals(10, bishopMoveCalculator.computeMoves(board, E5.getPosition()).size());
    }
}
