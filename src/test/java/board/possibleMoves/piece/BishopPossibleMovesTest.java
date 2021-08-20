package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.Position;
import board.moves.calculator.pieces.BishopMoveCalculator;
import board.pieces.Bishop;
import board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BishopPossibleMovesTest
{

    OptimizedBoard optimizedBoard;

    Piece whiteBishop;

    Piece blackBishop;

    Piece secondWhiteBishop;

    BishopMoveCalculator bishopMoveCalculator = new BishopMoveCalculator();


    @BeforeEach
    private void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        whiteBishop = new Bishop(true);
        blackBishop = new Bishop(false);
        secondWhiteBishop = new Bishop(true);
    }

    @Test
    public void singleWhiteBishop()
    {
        Position whiteBishopPosition = new Position('a', 1);

        optimizedBoard.addPiece(whiteBishopPosition, whiteBishop);


        int bishopMoves = bishopMoveCalculator.computeMoves(optimizedBoard,whiteBishopPosition).size();


        assertEquals(7,bishopMoves);
    }

    @Test
    public void twoAdjacentWhiteBishops()
    {
        Position firstWhitePosition  = new Position('d', 4);
        Position secondWhitePosition = new Position('e', 5);

        optimizedBoard.addPiece(firstWhitePosition, whiteBishop);
        optimizedBoard.addPiece(secondWhitePosition, secondWhiteBishop);

        int firstBishopMoves  = bishopMoveCalculator.computeMoves(optimizedBoard, firstWhitePosition).size();
        int secondBishopMoves = bishopMoveCalculator.computeMoves(optimizedBoard, secondWhitePosition).size();

        assertEquals(firstBishopMoves, 9);
        assertEquals(secondBishopMoves, 9);
    }

    @Test
    public void twoAdjacentOppositeBishops()
    {
        Position whitePosition = new Position('d', 4);
        Position blackPosition = new Position('e', 5);

        optimizedBoard.addPiece(whitePosition, whiteBishop);
        optimizedBoard.addPiece(blackPosition, blackBishop);

        int whiteMoves = bishopMoveCalculator.computeMoves(optimizedBoard, whitePosition).size();
        int blackMoves = bishopMoveCalculator.computeMoves(optimizedBoard, blackPosition).size();

        assertEquals(10, whiteMoves);
        assertEquals(10, blackMoves);
    }
}
