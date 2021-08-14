package board.possibleMoves.multiplepieces;

import board.OptimizedBoard;
import board.Position;
import board.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ComplexPositionsTest
{

    OptimizedBoard optimizedBoard;

    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
    }

    @Test
    public void threeQueensEach()
    {
        Queen firstWhiteQueen  = new Queen(true);
        Queen secondWhiteQueen = new Queen(true);
        Queen thirdWhiteQueen  = new Queen(true);

        Queen firstBlackQueen  = new Queen(false);
        Queen secondBlackQueen = new Queen(false);
        Queen thirdBlackQueen  = new Queen(false);

        Position firstWhiteQueenPosition  = new Position('b', 3);
        Position secondWhiteQueenPosition = new Position('c', 5);
        Position thirdWhiteQueenPosition  = new Position('g', 7);
        Position firstBlackQueenPosition  = new Position('b', 7);
        Position secondBlackQueenPosition = new Position('d', 5);
        Position thirdBlackQueenPosition  = new Position('g', 2);

        optimizedBoard.addPiece(firstWhiteQueenPosition, firstWhiteQueen);
        optimizedBoard.addPiece(secondWhiteQueenPosition, secondWhiteQueen);
        optimizedBoard.addPiece(thirdWhiteQueenPosition, thirdWhiteQueen);

        optimizedBoard.addPiece(firstBlackQueenPosition, firstBlackQueen);
        optimizedBoard.addPiece(secondBlackQueenPosition, secondBlackQueen);
        optimizedBoard.addPiece(thirdBlackQueenPosition, thirdBlackQueen);

        optimizedBoard.computePossibleMoves();

        int result = optimizedBoard.getPossibleMoves().size();

        assert result == 61;


        optimizedBoard.setWhiteToMove(false);

        optimizedBoard.computePossibleMoves();

        result = optimizedBoard.getPossibleMoves().size();


        assert result == 53;
    }
}
