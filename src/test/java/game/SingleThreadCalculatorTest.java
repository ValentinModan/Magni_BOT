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