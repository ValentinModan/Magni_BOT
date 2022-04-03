package board.possibleMoves.piece;

import board.Board;
import board.Position;
import board.PositionEnum;
import board.moves.movetypes.Move;
import board.moves.calculator.pieces.KnightMoveCalculator;
import board.pieces.Knight;
import board.pieces.Piece;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static board.PositionEnum.A1;

public class KnightPossibleMovesTest
{

    private Board board;
    private Piece blackKnight;
    private Piece secondWhiteKnight;
    private final KnightMoveCalculator knightMoveCalculator = KnightMoveCalculator.getInstance();


    @BeforeEach
    private void setUP()
    {
        board = new Board();
        blackKnight = new Knight(false);
        secondWhiteKnight = new Knight(true);
    }

    @Test
    public void whiteSecondMove()
    {
        BoardSetup.setupBoard(board);
        Move move = new Move(new Position('b', 1), new Position('c', 3));
        board.move(move);

        board.computePossibleMoves();

        assert board.getPossibleMoves().size() == 22;
    }

    @Test
    public void blackSecondMove()
    {
        BoardSetup.setupBoard(board);
        board.setWhiteToMove(false);
        Move move = new Move(new Position('b', 8), new Position('c', 6));
        board.move(move);

        board.computePossibleMoves();

        assert board.getPossibleMoves().size() == 22;
    }

}
