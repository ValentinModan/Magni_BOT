package board.possibleMoves.piece;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.calculator.pieces.KnightMoveCalculator;
import board.pieces.Knight;
import board.pieces.Piece;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnightPossibleMovesTest
{

    private Board board;
    private Piece whiteKnight;
    private Piece          blackKnight;
    private Piece          secondWhiteKnight;
    private KnightMoveCalculator knightMoveCalculator = new KnightMoveCalculator();


    @BeforeEach
    private void setUP()
    {
        board = new Board();
        whiteKnight = new Knight(true);
        blackKnight = new Knight(false);
        secondWhiteKnight = new Knight(true);
    }


    public void singleKnightCornerPossibleMoves()
    {
        board.setWhiteToMove(true);
        Piece knight = new Knight(true);

        board.addPiece(new Position('a', 1), knight);

        board.computePossibleMoves();

        assert board.getPossibleMoves().size() == 2;
    }


    public void twoOppositeColorKnightsCornerPossibleMoves()
    {
        Position whiteKnightPosition = new Position('a', 8);
        Position blackKnightPosition = new Position('h', 1);

        board.addPiece(whiteKnightPosition, whiteKnight);
        board.addPiece(blackKnightPosition, blackKnight);

        board.computePossibleMoves();

        assert board.getPossibleMoves().size() == 2;
    }


    public void twoWhiteKnightsPossibleMoves()
    {

        Position firstWhitePosition  = new Position('d', 4);
        Position secondWhitePosition = new Position('f', 5);

        board.addPiece(firstWhitePosition, whiteKnight);
        board.addPiece(secondWhitePosition, secondWhiteKnight);

        board.computePossibleMoves();

        assert board.getPossibleMoves().size() == 14;
    }


    public void twoOppositeAttackingKnights()
    {
        Position whitePosition = new Position('d', 4);
        Position blackPosition = new Position('f', 5);

        board.addPiece(whitePosition, whiteKnight);
        board.addPiece(blackPosition, blackKnight);

        board.computePossibleMoves();

        assert board.getPossibleMoves().size() == 8;
    }


    public void whiteToMoveWithNoWhiteKnights() throws Exception
    {
        Piece thirdKnight = new Knight(true);

        Position firstWhitePosition  = new Position('d', 4);
        Position secondWhitePosition = new Position('f', 5);
        Position thirdWhitePosition  = new Position('f', 3);


        board.addPiece(firstWhitePosition, whiteKnight);
        board.addPiece(secondWhitePosition, secondWhiteKnight);
        board.addPiece(thirdWhitePosition, thirdKnight);

        Assertions.assertEquals(6,knightMoveCalculator.computeMoves(board, firstWhitePosition).size());
        Assertions.assertEquals(7,knightMoveCalculator.computeMoves(board, secondWhitePosition).size());
        Assertions.assertEquals(7,knightMoveCalculator.computeMoves(board, thirdWhitePosition).size());
    }


    @Test
    public void whiteSecondMove()
    {
        BoardSetup.setupBoard(board);
        Move move = new Move(new Position('b', 1), new Position('c', 3));
        board.move(move);

        board.computePossibleMoves();

        assert board.getPossibleMoves().size() == 22;
    }


    @Test
    public void blackSecondMove()
    {
        BoardSetup.setupBoard(board);
        board.setWhiteToMove(false);
        Move move = new Move(new Position('b', 8), new Position('c', 6));
        board.move(move);


        board.computePossibleMoves();

        assert board.getPossibleMoves().size() == 22;
    }

}
