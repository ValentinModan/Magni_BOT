package game;

import board.Board;
import board.MovementMap;
import board.moves.movetypes.Move;
import board.moves.MoveConvertor;
import helper.MovementMapCounter;
import helper.MovesGenerator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static helper.MovementMapCounter.countChildrenMovesWithDepth;
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
    void impossiblePuzzle3()
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "8/2n5/7p/2p2K1k/Bb6/1n4P1/8/4n3 w - - 3 13");
        SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();
        board.computePossibleMoves();
        Move move = singleThreadCalculator.bestResponse(board);
        assertEquals(move, MoveConvertor.stringToMove("a4b3"));
    }

    //https://lichess.org/study/5vLGSMCF
    @Test
    void impossiblePuzzle2()
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "8/2n5/7p/5K1k/1bp5/1B4P1/8/4n3 w - - 0 14");
        SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();
        board.computePossibleMoves();
        Move move = singleThreadCalculator.bestResponse(board);
        assertEquals(move, MoveConvertor.stringToMove("b3d1"));
    }

    @Test
    void impossiblePuzzle1()
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "8/2n5/7p/5K1k/1bp5/5nP1/8/3B4 w - - 2 15");
        SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();
        board.computePossibleMoves();
        Move move = singleThreadCalculator.bestResponse(board);
        assertEquals(move, MoveConvertor.stringToMove("d1f3"));
    }

    @Test
    void impossiblePuzzleBegin() throws InterruptedException, CloneNotSupportedException
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "8/3P3k/n2K3p/2p3n1/1b4N1/2p1p1P1/8/3B4 w - - 0 1");
        SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();
        board.computePossibleMoves();
        singleThreadCalculator.setup(board);


        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();
        System.out.println("first 4 levels done");
        System.out.println(MovementMapCounter.countChildrenMoves(MovementMap.currentMoveFromTheGame));
        singleThreadCalculator.computeDoubleTree();
        System.out.println("6 levels done");

        int value = MovementMapCounter.countChildrenMoves(MovementMap.currentMoveFromTheGame);

        System.out.println(value);
    }

    @Test
    void impossiblePuzzleq()
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "3n4/8/n6p/2p2K1k/1b2B3/2p3P1/4p3/8 b - - 1 7");
        SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();
        board.computePossibleMoves();
        Move move = singleThreadCalculator.bestResponse(board);
        System.out.println(board);
        System.out.println(move);
    }


    @Test
    void shannonNumberTestTwoMoves() throws InterruptedException, CloneNotSupportedException
    {
        singleThreadCalculator.setup(board);

        singleThreadCalculator.computeDoubleTree();
        // singleThreadCalculator.computeDoubleTree();

        assertEquals(400, MovementMapCounter.countChildrenMoves(MovementMap.currentMoveFromTheGame));
    }

    @Test
    void shannonNumberTestThreeMoves() throws InterruptedException, CloneNotSupportedException
    {
        singleThreadCalculator.setup(board);

        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeTree();

        assertEquals(8902, MovementMapCounter.countChildrenMoves(MovementMap.currentMoveFromTheGame));
        MovementMapCounter.displayresults();
    }

    @Test
    void shannonNumberTestFourMoves() throws InterruptedException, CloneNotSupportedException
    {
        singleThreadCalculator.setup(board);

        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();

        assertEquals(197281, MovementMapCounter.countChildrenMoves(MovementMap.currentMoveFromTheGame));
        MovementMapCounter.displayresults();
    }

    @Test
    void shannonNumberTestFiveMoves() throws InterruptedException, CloneNotSupportedException, FileNotFoundException
    {
        PrintStream out = new PrintStream(new FileOutputStream("logs.log"));
        System.setOut(out);

        singleThreadCalculator.setup(board);

        singleThreadCalculator.computeDoubleTree();
        System.out.println("first part done");
        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeTree();

        assertEquals(197281, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 4));
        assertEquals(4865609, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 5));
    }

    @Test
    void position2_PerftResults() throws InterruptedException, CloneNotSupportedException
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - ");
        board.computePossibleMoves();

        singleThreadCalculator.setup(board);
        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();

        assertEquals(48, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 1));
        assertEquals(2039, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 2));
        assertEquals(97862, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 3));
        assertEquals(4085603, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 4));
    }

    @Test
    void position3_PerftResults3() throws InterruptedException, CloneNotSupportedException
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - ");
        board.computePossibleMoves();

        singleThreadCalculator.setup(board);
        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeTree();

        assertEquals(14, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 1));
        assertEquals(191, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 2));
        assertEquals(2812, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 3));
        assertEquals(43238, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 4));
        assertEquals(674624, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 5));
    }


    @Test
    void position4_PerftResults() throws InterruptedException, CloneNotSupportedException
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        board.computePossibleMoves();

        singleThreadCalculator.setup(board);


        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();

        assertEquals(6, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 1));
        assertEquals(264, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 2));
      //9159
        assertEquals(9467, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 3));
        assertEquals(422333, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 4));
    }

    @Test
    void position5_PerftResults() throws InterruptedException, CloneNotSupportedException
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        board.computePossibleMoves();

        singleThreadCalculator.setup(board);
        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeDoubleTree();

        assertEquals(44, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 1));
        assertEquals(1486, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 2));
        //61481
        assertEquals(62379, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 3));
        assertEquals(2103487, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 4));
    }

    @Test
    void position6_PerftResults() throws InterruptedException, CloneNotSupportedException
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        BoardSetup.fenNotationBoardSetup(board, "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
        board.computePossibleMoves();

        singleThreadCalculator.setup(board);
        singleThreadCalculator.computeDoubleTree();
        singleThreadCalculator.computeTree();

        assertEquals(46, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 1));
        assertEquals(2079, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 2));
        assertEquals(89890, countChildrenMovesWithDepth(MovementMap.currentMoveFromTheGame, 3));
    }
}