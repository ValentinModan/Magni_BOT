package board.possibleMoves.piece;

import board.Board;
import board.ColorEnum;
import board.PositionEnum;
import board.pieces.PieceType;
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
import pieces.PieceFactory;

import static board.ColorEnum.WHITE;
import static board.PositionEnum.A1;
import static board.pieces.PieceType.ROOK;

public class RookPossibleMovesTest
{
    Board board;
    Piece whiteRook;
    Piece blackRook;
    Piece secondWhiteRook;
    RookMoveCalculator rookMoveCalculator = RookMoveCalculator.getInstance();

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
        board.addPiece(A1, PieceFactory.createPiece(WHITE, ROOK));
        Assertions.assertEquals(14, rookMoveCalculator.computeMoves(board, A1.getPosition()).size());
    }

    @Test
    public void trappedRook()
    {
        Position cornerPosition = new Position('a', 1);

        RookMoveCalculator rookMoveCalculator = RookMoveCalculator.getInstance();

        board.addPiece(A1, whiteRook);
        board.addPiece(A1.move(Movement.UP), blackRook);
        board.addPiece(A1.move(Movement.RIGHT), blackRook);

        int result = rookMoveCalculator.computeMoves(board, cornerPosition).size();

        Assertions.assertEquals(2, result);
    }


    @Test
    public void sameLineRooks()
    {
        Position firstWhiteRookPosition = new Position('b', 2);
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

        RookMoveCalculator rookMoveCalculator = RookMoveCalculator.getInstance();

        int rookMoves = rookMoveCalculator.computeMoves(board, new Position('h', 1)).size();

        Assertions.assertEquals(2, rookMoves);

    }
}
