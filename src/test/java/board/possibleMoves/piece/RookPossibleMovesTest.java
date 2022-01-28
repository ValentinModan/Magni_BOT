package board.possibleMoves.piece;

import board.Board;
import helper.MovesGenerator;
import board.Position;
import board.moves.Movement;
import board.moves.calculator.pieces.RookMoveCalculator;
import board.pieces.Piece;
import board.pieces.Rook;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RookPossibleMovesTest
{
    Board board;
    Piece whiteRook;
    Piece              blackRook;
    Piece              secondWhiteRook;
    RookMoveCalculator rookMoveCalculator = new RookMoveCalculator();

    @BeforeEach
    public void setUp()
    {
        board = new Board();
        whiteRook = new Rook(true);
        blackRook = new Rook(false);
        secondWhiteRook = new Rook(true);
    }

    @Test
    public void singleRook()
    {
        Position cornerPosition = new Position('a', 1);

        board.addPiece(cornerPosition, whiteRook);

        assert rookMoveCalculator.computeMoves(board, cornerPosition).size() == 14;
    }

    @Test
    public void trappedRook()
    {
        Position cornerPosition = new Position('a', 1);

        RookMoveCalculator rookMoveCalculator = new RookMoveCalculator();

        board.addPiece(cornerPosition, whiteRook);

        board.addPiece(cornerPosition.move(Movement.UP), blackRook);
        board.addPiece(cornerPosition.move(Movement.RIGHT), blackRook);

        int result = rookMoveCalculator.computeMoves(board, cornerPosition).size();

        Assertions.assertEquals(2, result);
    }


    @Test
    public void sameLineRooks()
    {
        Position firstWhiteRookPosition  = new Position('b', 2);
        Position secondWhiteRookPosition = new Position('f', 2);

        board.addPiece(firstWhiteRookPosition, whiteRook);
        board.addPiece(secondWhiteRookPosition, secondWhiteRook);

        int result = rookMoveCalculator.computeMoves(board, firstWhiteRookPosition).size();

        Assertions.assertEquals(11, result);

        result = rookMoveCalculator.computeMoves(board, secondWhiteRookPosition).size();

        Assertions.assertEquals(12, result);
    }

    @Test
    public void complexRookPosition()
    {
        String firstMoves = "f2f3 e7e6 g2g4 d8h4 h2h3 h4h3";
        BoardSetup.setupBoard(board);

        MovesGenerator.makeMoves(board, firstMoves);

        RookMoveCalculator rookMoveCalculator = new RookMoveCalculator();

        int rookMoves = rookMoveCalculator.computeMoves(board, new Position('h', 1)).size();

        Assertions.assertEquals(2, rookMoves);

    }
}
