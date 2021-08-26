package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;
import game.CleanMoveCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KingPossibleMoves
{
    OptimizedBoard optimizedBoard;


    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        BoardSetup.setupBoard(optimizedBoard);
    }


    @Test
    public void oneMoveLeft()
    {
        MovesGenerator.makeMoves(optimizedBoard,"e2e4 e7e5 f2f4 f7f5 e4f5 g8f6 f4e5 b8c6 e5f6 b7b6 f6g7 c8b7 g7f8 e8f8 a2a3 d8h4 g2g3 h8g8 g3h4 g8g1 h1g1 c6d4 h4h5 b7f3 d1f3 d7d6 f3a8 f8f7 a8a7 f7f6 a7b6 f6e5 b6d4 e5d4 c2c3 d4e5 c3c4 e5d4 a3a4 d4e5 a4a5 e5f4 f5f6 f4e5 a5a6 e5f4 f6f7 f4e5 f7f8 e5e4 f8d6 c7c5 d6c5 h7h6 a6a7 e4f4 f1h3 f4f3");
        Move bestMove = CleanMoveCalculator.calculate2(optimizedBoard, 2);

        Assertions.assertEquals(MoveConvertor.stringToMove("c5e3"),bestMove);

    }
}
