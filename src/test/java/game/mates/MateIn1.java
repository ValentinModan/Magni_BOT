package game.mates;

import board.Board;
import board.Position;
import board.moves.movetypes.Move;
import board.moves.MoveConvertor;
import board.pieces.King;
import board.pieces.Queen;
import board.pieces.Rook;
import board.setup.BoardSetup;
import game.GameBoard;
import game.SingleThreadCalculator;
import helper.MovesGenerator;
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
    public void scholarsMate_depth_search_1()
    {
        String firstMoves = "f2f3 e7e6 g2g4 d8h4 a2a3";
        MovesGenerator.makeMoves(board, firstMoves);

        Move bestCalculateMove = singleThreadCalculator.bestResponse(board);

        Move expectedMove = MoveConvertor.stringToMove("h4e1");
        Assertions.assertEquals(expectedMove, bestCalculateMove);
    }


    @Test
    public void scholarsMate_depth_search_2()
    {
        String firstMoves = "f2f3 e7e6 g2g4";
        MovesGenerator.makeMoves(board, firstMoves);

        Move bestCalculateMove = singleThreadCalculator.bestResponse(board);

        Move expectedMove = MoveConvertor.stringToMove("d8h4");
        Assertions.assertEquals(expectedMove, bestCalculateMove);
    }

    @Test
    public void twoRooksMate()
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
    public void mateIn1TwoQueens()
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
    public void notStaleMate()
    {
        MovesGenerator.makeMoves(board, "e2e4 e7e5 f2f4 f7f5 e4f5 g8f6 f4e5 b8c6 e5f6 b7b6 f6g7 c8b7 g7f8 e8f8 a2a3 d8h4 g2g3 h8g8 g3h4 g8g1 h1g1 c6d4 h4h5 b7f3 d1f3 d7d6 f3a8 f8f7 a8a7 f7f6 a7b6 f6e5 b6d4 e5d4 c2c3 d4e5 c3c4 e5d4 a3a4 d4e5 a4a5 e5f4 f5f6 f4e5 a5a6 e5f4 f6f7 f4e5 f7f8 e5e4 f8d6 c7c5 d6c5 h7h6 a6a7 e4f4");

        Move bestMove = singleThreadCalculator.bestResponse(board);

        System.out.println(board);
        assert bestMove != MoveConvertor.stringToMove("a7a8");
    }

    @Test
    public void matein1()
    {
        String moves = "d2d4 g8f6 c2c4 e7e6 b1c3 f8b4 g1f3 d7d5 c4c5 e6e5 a2a3 b4c3 b2c3 e5d4 c3d4 a7a6 e2e3 c7c6 f1d3 b7b6 e1h1 b6c5 d4c5 h7h6 d1b3 a6a5 c1b2 h6h5 a1b1 h5h4 b2c3 a5a4 c3f6 a4b3 b1b3 g7f6 d3f5 c8f5 f1b1 f5b1 b3b1 a8a3 f3d4 a3e3 b1b8";

        MovesGenerator.makeMoves(board, moves);

        Move bestMove = singleThreadCalculator.bestResponse(board);

        System.out.println(board);
        assert bestMove != MoveConvertor.stringToMove("e3e1");
    }


}
