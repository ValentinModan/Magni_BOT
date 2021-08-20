package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.MoveConvertor;
import board.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnMoveCalculatorTest
{
    OptimizedBoard optimizedBoard;

    PawnMoveCalculator pawnMoveCalculator;

    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        pawnMoveCalculator = new PawnMoveCalculator();
    }

    @Test
    void anPassant()
    {
        Position whitePawnPosition = new Position('d', 5);
        Pawn     whitePawn         = new Pawn();

        Position blackPawnPosition = new Position('c', 7);
        Pawn     blackPawn         = new Pawn(false);

        optimizedBoard.addPiece(whitePawnPosition, whitePawn);
        optimizedBoard.addPiece(blackPawnPosition, blackPawn);

        optimizedBoard.setWhiteToMove(false);
        optimizedBoard.move(MoveConvertor.stringToMove("c7c5"));
        optimizedBoard.setWhiteToMove(true);
        int result = pawnMoveCalculator.computeMoves(optimizedBoard, whitePawnPosition).size();

        assertEquals(result, 2);

        optimizedBoard.move(MoveConvertor.stringToMove("d5c6"));

        assertEquals(0, optimizedBoard.getBlackPiecesMap().size());
        assertEquals(1, optimizedBoard.getWhitePiecesMap().size());
    }
}