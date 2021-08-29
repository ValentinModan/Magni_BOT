package game.mates;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.pieces.King;
import board.pieces.Queen;
import board.pieces.Rook;
import board.setup.BoardSetup;
import game.CleanMoveCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MateIn2
{
    OptimizedBoard optimizedBoard;

    @BeforeEach
    public void setUp()
    {
       optimizedBoard = new OptimizedBoard();
        Position whiteKingPosition = new Position('a',1);
        Position blackKingPosition = new Position('g',7);
        Position firstWhiteRookPosition = new Position('b',5);
        Position secondWhiteRookPosition = new Position('a',6);

        optimizedBoard.addPiece(whiteKingPosition,new King(true));
        optimizedBoard.addPiece(blackKingPosition,new King(false));
        optimizedBoard.addPiece(firstWhiteRookPosition, new Rook(true));
        optimizedBoard.addPiece(secondWhiteRookPosition, new Rook(true));
    }


    @Test
    public void twoRooks()
    {
       Move bestMove = CleanMoveCalculator.calculate2(optimizedBoard, 4);

        Assertions.assertEquals(bestMove, MoveConvertor.stringToMove("b5b7"));

    }

    @Test
    public void twoRooksAndAQueen()
    {
        optimizedBoard.addPiece(new Position('f',1),new Queen(true));
        Move bestMove = CleanMoveCalculator.calculate2(optimizedBoard, 6);

        Assertions.assertEquals(bestMove, MoveConvertor.stringToMove("b5b7"));

    }




}
