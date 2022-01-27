package game.mates;

import board.Board;
import board.Position;
import board.moves.Move;
import board.pieces.King;
import board.pieces.Rook;
import toDelete.CleanMoveCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MateIn3
{
    Board board;

    @BeforeEach
    public void setUp()
    {
        board = new Board();
        Position whiteKingPosition = new Position('a',1);
        Position blackKingPosition = new Position('g',7);
        Position firstWhiteRookPosition = new Position('b',5);
        Position secondWhiteRookPosition = new Position('a',4);

        board.addPiece(whiteKingPosition, new King(true));
        board.addPiece(blackKingPosition, new King(false));
        board.addPiece(firstWhiteRookPosition, new Rook(true));
        board.addPiece(secondWhiteRookPosition, new Rook(true));
    }


    @Test
    public void twoRooks()
    {
        Move bestMove = CleanMoveCalculator.calculate2(board, 6);

        System.out.println(bestMove);
      Assertions.assertEquals(6,bestMove.getFinalPosition().getRow());
    }






}
