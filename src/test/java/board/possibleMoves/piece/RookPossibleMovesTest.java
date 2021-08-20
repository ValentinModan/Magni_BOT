package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.moves.MovesGenerator;
import board.moves.calculator.pieces.RookMoveCalculator;
import board.pieces.Piece;
import board.pieces.Rook;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RookPossibleMovesTest
{
    OptimizedBoard optimizedBoard;
    Piece          whiteRook;
    Piece          blackRook;
    Piece secondWhiteRook;

    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        whiteRook = new Rook(true);
        blackRook = new Rook(false);
        secondWhiteRook = new Rook(true);
    }

    @Test
    public void singleRook()
    {
        Position cornerPosition = new Position('a',1);

        optimizedBoard.addPiece(cornerPosition,whiteRook);

        optimizedBoard.computePossibleMoves();

       assert  optimizedBoard.getPossibleMoves().size() == 14;
    }

    @Test
    public void trappedRook()
    {
        Position cornerPosition = new Position('a',1);

        optimizedBoard.addPiece(cornerPosition,whiteRook);

        optimizedBoard.addPiece(cornerPosition.move(Movement.UP),blackRook);
        optimizedBoard.addPiece(cornerPosition.move(Movement.RIGHT),blackRook);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 2;
    }

    @Test
    public void sameLineRooks()
    {
        Position firstWhiteRookPosition = new Position ('b',2);
        Position secondWhiteRookPosition = new Position('f',2);

        optimizedBoard.addPiece(firstWhiteRookPosition, whiteRook);
        optimizedBoard.addPiece(secondWhiteRookPosition,secondWhiteRook);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 23;
    }

    @Test
    public void complexRookPosition()
    {
        String firstMoves = "f2f3 e7e6 g2g4 d8h4 h2h3 h4h3";
        BoardSetup.setupBoard(optimizedBoard);

        MovesGenerator.makeMoves(optimizedBoard,firstMoves);

        RookMoveCalculator rookMoveCalculator = new RookMoveCalculator();

        int rookMoves = rookMoveCalculator.computeMoves(optimizedBoard,new Position('h',1)).size();

        Assertions.assertEquals(2,rookMoves);

    }
}
