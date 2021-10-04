package game.mates;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.piecees.King;
import board.piecees.Rook;
import game.CleanMoveCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MateIn3
{
    OptimizedBoard optimizedBoard;

    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        Position whiteKingPosition = new Position('a',1);
        Position blackKingPosition = new Position('g',7);
        Position firstWhiteRookPosition = new Position('b',5);
        Position secondWhiteRookPosition = new Position('a',4);

        optimizedBoard.addPiece(whiteKingPosition,new King(true));
        optimizedBoard.addPiece(blackKingPosition,new King(false));
        optimizedBoard.addPiece(firstWhiteRookPosition, new Rook(true));
        optimizedBoard.addPiece(secondWhiteRookPosition, new Rook(true));
    }


    @Test
    public void twoRooks()
    {
        Move bestMove = CleanMoveCalculator.calculate2(optimizedBoard, 6);

        System.out.println(bestMove);
      Assertions.assertEquals(6,bestMove.getFinalPosition().getRow());
    }






}
