package board.possibleMoves.piece;

import board.OptimizedBoard;
import board.Position;
import board.pieces.Bishop;
import board.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BishopPossibleMovesTest {

    OptimizedBoard optimizedBoard;

    Piece whiteBishop;

    Piece blackBishop;

    Piece secondWhiteBishop;


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
        Position whitePosition = new Position('a',1);

        optimizedBoard.addPiece(whitePosition,whiteBishop);

        optimizedBoard.computePossibleMoves();


        assert  optimizedBoard.getPossibleMoves().size() == 7;
    }

    @Test
    public void twoAdjacentWhiteBishops()
    {
        Position firstWhitePosition = new Position('d',4);
        Position secondWhitePosition = new Position('e',5);

        optimizedBoard.addPiece(firstWhitePosition, whiteBishop);
        optimizedBoard.addPiece(secondWhitePosition, secondWhiteBishop);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 18;
    }

    @Test
    public void twoAdjacentOppositeBishops()
    {
        Position whitePosition = new Position('d',4);
        Position blackPosition = new Position('e',5);

        optimizedBoard.addPiece(whitePosition, whiteBishop);
        optimizedBoard.addPiece(blackPosition, blackBishop);

        optimizedBoard.computePossibleMoves();

        assert optimizedBoard.getPossibleMoves().size() == 10;
    }
}
