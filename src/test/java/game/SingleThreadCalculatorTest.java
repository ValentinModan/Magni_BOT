package game;

import board.Board;
import board.MovementMap;
import board.moves.Move;
import board.moves.MoveConvertor;
import helper.MovementMapCounter;
import helper.MovesGenerator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleThreadCalculatorTest
{
    Board board;
    SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();


    @BeforeEach
    public void setUp()
    {
        board = new Board();
        BoardSetup.setupBoard(board);
        GameBoard.actualBoard = board;
    }

    @Test
    void bestResponse()
    {
        String firstMoves = "f2f3 e7e6 g2g4";
        MovesGenerator.makeMoves(board, firstMoves);
        Move bestMove = singleThreadCalculator.bestResponse(board);

        assertEquals(MoveConvertor.stringToMove("d8h4"), bestMove);

    }

    @Test
    void pawnPromotionTest()
    {
        String firstMoves = "d2d4 g8f6 g1f3 f6e4 b1c3 e4c3 b2c3 c7c6 c1f4 d7d6 a2a4 b7b6 d1d3 b6b5 a4b5 g7g5 f4g5 h7h6 b5c6 h6g5 d3b5 a7a6 c6c7 a6b5 c7d8 e8d8 a1a8 g5g4 a8b8 d8c7 b8b5 g4f3 e2f3 c8a6 f1c4 a6b5 c4b5 f7f6 e1h1 e7e5 f1a1 c7b6 b5c4 e5d4 a1b1 b6c5 c4e6 f8h6 c3d4 c5d4 b1b4 d4c3 b4c4 c3b2 c4c8 h8c8 e6c8 b2c2 c8f5 c2c1 h2h3 d6d5 f5e6 d5d4 g1h2 d4d3 f3f4 d3d2 e6g4";
        MovesGenerator.makeMoves(board, firstMoves);
        Move bestMove = singleThreadCalculator.bestResponse(board);
        System.out.println(board);

        System.out.println(bestMove);
    }
    
    @Test
    void blunderMove()
    {
        String moves = "e2e4 e7e6 d2d4 d7d5 e4e5 c7c5 g1f3 c5d4 f3d4 b8d7 f2f4 b7b6 f1b5 a7a6 b5c6";
        MovesGenerator.makeMoves(board, moves);
        Move bestMove = singleThreadCalculator.bestResponse(board);
        System.out.println(board);

        System.out.println(bestMove);
    }

    @Test
    void shannonNumberTestTwoMoves() throws InterruptedException, CloneNotSupportedException
    {
        singleThreadCalculator.setup(board);

        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();

        assertEquals(400, MovementMapCounter.countChildrenMoves(MovementMap.currentMoveFromTheGame));
    }

    @Test
    void shannonNumberTestFourMoves() throws InterruptedException, CloneNotSupportedException
    {
        singleThreadCalculator.setup(board);

        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();

        assertEquals(197281, MovementMapCounter.countChildrenMoves(MovementMap.currentMoveFromTheGame));
    }

    @Test
    void shannonNumberTestFiveMoves() throws InterruptedException, CloneNotSupportedException
    {
        singleThreadCalculator.setup(board);

        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeTree();

        assertEquals(4865609, MovementMapCounter.countChildrenMoves(MovementMap.currentMoveFromTheGame));
    }
}