package game.mates;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.MoveConvertor;
import MovesGenerator;
import board.pieces.King;
import board.pieces.Queen;
import board.pieces.Rook;
import board.setup.BoardSetup;
import toDelete.CleanMoveCalculator;
import game.GameBoard;
import game.SingleThreadCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MateIn1
{
    Board board;
    SingleThreadCalculator singleThreadCalculator;

    @BeforeEach
    public void setUp()
    {
        board = new Board();
        BoardSetup.setupBoard(board);
        GameBoard.actualBoard = board;
        singleThreadCalculator = new SingleThreadCalculator();
    }

    @Test
    public void scholarsMate_depth_search_1() throws Exception
    {
        String firstMoves = "f2f3 e7e6 g2g4 d8h4 a2a3";
        MovesGenerator.makeMoves(board, firstMoves);

        Move bestCalculateMove = singleThreadCalculator.bestResponse(board);

        Move expectedMove = MoveConvertor.stringToMove("h4e1");
        Assertions.assertEquals(expectedMove, bestCalculateMove);
    }


    @Test
    public void scholarsMate_depth_search_2() throws Exception
    {
        String firstMoves = "f2f3 e7e6 g2g4";
        MovesGenerator.makeMoves(board, firstMoves);

        Move bestCalculateMove = singleThreadCalculator.bestResponse(board);

        Move expectedMove = MoveConvertor.stringToMove("d8h4");
        Assertions.assertEquals(expectedMove, bestCalculateMove);
    }

    @Test
    public void twoRooksMate() throws CloneNotSupportedException, InterruptedException
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        Rook firstBlackRook = new Rook(false);
        Rook secondBlackRook = new Rook(false);
        King whiteKing = new King(true);
        King blackKing = new King(false);

        Position fistBlackRookPosition = new Position('a', 3);
        Position secondBlackRookPosition = new Position('b', 2);
        Position whiteKingPosition = new Position('h', 1);
        Position blackKingPosition = new Position('a', 8);
        board.setWhiteToMove(true);
        board.addPiece(fistBlackRookPosition, firstBlackRook);
        board.addPiece(secondBlackRookPosition, secondBlackRook);
        board.addPiece(whiteKingPosition, whiteKing);
        board.addPiece(blackKingPosition, blackKing);

        board.move(MoveConvertor.stringToMove("h1g1"));
        board.nextTurn();

        Move bestMove = singleThreadCalculator.bestResponse(board);

        Assertions.assertEquals(MoveConvertor.stringToMove("a3a1"), bestMove);
    }


    @Test
    public void mateIn1TwoQueens() throws CloneNotSupportedException, InterruptedException
    {
        Board board = new Board();
        GameBoard.actualBoard = board;
        Position firstQueenPosition = new Position('b', 7);
        Position secondQueenPosition = new Position('a', 7);
        Position rook = new Position('h', 5);
        Position whiteKingPosition = new Position('c', 2);
        Position blackKingPosition = new Position('f', 6);

        board.addPiece(firstQueenPosition, new Queen(true));
        board.addPiece(secondQueenPosition, new Queen(true));
        board.addPiece(rook, new Rook(true));
        board.addPiece(whiteKingPosition, new King(true));
        board.addPiece(blackKingPosition, new King(false));

        Move bestMove = singleThreadCalculator.bestResponse(board);

        Assertions.assertTrue(bestMove.moveScore() > 900);
    }

    @Test
    public void notStaleMate() throws CloneNotSupportedException, InterruptedException
    {
        MovesGenerator.makeMoves(board, "e2e4 e7e5 f2f4 f7f5 e4f5 g8f6 f4e5 b8c6 e5f6 b7b6 f6g7 c8b7 g7f8 e8f8 a2a3 d8h4 g2g3 h8g8 g3h4 g8g1 h1g1 c6d4 h4h5 b7f3 d1f3 d7d6 f3a8 f8f7 a8a7 f7f6 a7b6 f6e5 b6d4 e5d4 c2c3 d4e5 c3c4 e5d4 a3a4 d4e5 a4a5 e5f4 f5f6 f4e5 a5a6 e5f4 f6f7 f4e5 f7f8 e5e4 f8d6 c7c5 d6c5 h7h6 a6a7 e4f4");

        Move bestMove = singleThreadCalculator.bestResponse(board);

        System.out.println(board);
        assert bestMove != MoveConvertor.stringToMove("a7a8");
    }

    @Test
    public void matein1() throws CloneNotSupportedException, InterruptedException
    {
        String moves = "d2d4 g8f6 c2c4 e7e6 b1c3 f8b4 g1f3 d7d5 c4c5 e6e5 a2a3 b4c3 b2c3 e5d4 c3d4 a7a6 e2e3 c7c6 f1d3 b7b6 e1h1 b6c5 d4c5 h7h6 d1b3 a6a5 c1b2 h6h5 a1b1 h5h4 b2c3 a5a4 c3f6 a4b3 b1b3 g7f6 d3f5 c8f5 f1b1 f5b1 b3b1 a8a3 f3d4 a3e3 b1b8";

        MovesGenerator.makeMoves(board, moves);

        Move bestMove = singleThreadCalculator.bestResponse(board);

        System.out.println(board);
        assert bestMove != MoveConvertor.stringToMove("e3e1");
    }

    @Test
    public void mate_in_1_bishop_move()
    {
        String moves = "d2d4 e7e6 c2c4 d7d5 c4c5 b8c6 e2e3 g8f6 e3e4 f6e4 g2g3 d8g5 c1g5 f7f5 f2f3 h7h6 f3e4 h6g5 e4d5 h8h2 h1h2 f5f4 d5c6 f8e7 c6b7 c8b7 g3f4 g5f4 c5c6 b7c6 b2b3 c6e4 b3b4 e7f6 b4b5 a8b8 b5b6 b8b6 d4d5 e6d5 d1d5 f6c3 b1c3 b6b1 a1b1 e4d3 d5d3 e8e7 a2a3 e7f6 a3a4 f6e5 a4a5 e5f6 a5a6 f6e5 h2h3 e5f6 c3b5 f6e7 d3d4 e7e6 d4a7 e6d5 b5c7 d5d6 a7a8 d6c7 a6a7 c7d6 a8b7 d6e6 b7g7 e6d5 f1e2 d5e6 a7a8 e6f5 e2d3 f5e6 h3h4 e6d6 g1f3 d6e6 d3c4 e6d6 f3e5 d6c5 h4f4 c5d6 e5d7 d6c7 c4b5 c7d6 d7b8 d6c5 b5a6 c5d6 b8c6 d6c5 f4f5 c5d6 f5f6 d6d5 b1b2 d5c5 f6f7 c5d5 a6b5 d5e4 c6b8 e4e3 a8a7 e3e4 b5a6 e4d5 f7f8 d5e4 b2b3 e4d5 b8c6 d5e4 a7a8 e4d5 c6b8 d5e6 f8e8 e6f5 g7g8 f5f4 a6b5 f4f5 b3b4 f5f6 g8f8 f6g5 e8d8 g5g6 b5a6 g6h5 a8a7 h5g5 d8c8 g5h5 b4b5 h5g6 a6b7 g6h7 f8e8 h7g7 a7a8 g7h7 b8a6 h7g7 e8d8 g7f7 c8b8 f7g7 d8c8 g7h7 c8c7 h7g6 a8a7 g6f6 a7a8 f6g6 b5b6 g6f5 a8a7 f5g5 a7a8 g5f5 b7c6 f5g5 a8a7 g5h5 c7c8 h5h6 a6b4 h6g6 b4a6 g6f6 b8a8 f6g6 c6b7 g6f7 a6b4 f7g7";
        MovesGenerator.makeMoves(board, moves);

        Move bestMove = CleanMoveCalculator.calculate2(board, 5);

        assert bestMove.moveScore() >= 4000;
    }

    @Test
    public void mate_in_1()
    {
        String x = "d2d4 c7c6 c2c4 d7d5 c4d5 c6d5 b2b4 c8f5 b1c3 e7e6 a2a3 d8b6 h2h4 a7a5 b4a5 a8a5 e2e3 g8f6 d1f3 b6b3 c3b1 f5b1 e3e4 b3c3 f3c3 b8c6 e4d5 f6d5 c3h3 c6d4 a1b1 f8a3 c1g5 a3b4 g5d2 d5f4 h3h2 f4d3 f1d3 b4d2 e1d2 e8h8 b1b7 f8d8 f2f3 g7g6 h2f4 d4f5 h4h5 a5a3 h5g6 h7g6 f4g4 d8d3 d2c1 a3a2 b7b8 g8g7 g4h3 g7f6 h3h2 f5e3 g1h3 d3d2 h1e1 d2c2 c1b1 e3d5 g2g4";
    }
}
