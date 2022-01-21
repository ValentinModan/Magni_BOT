package fen.mate;

import board.Board;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.setup.BoardSetup;
import fen.FenStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MateIn3
{

    @ParameterizedTest
    @MethodSource("chessFenAndMoves")
    public void mateIn1(String fen,String expectedMove)
    {
        Board board = new Board();
        BoardSetup.fenNotationBoardSetup(board, fen);

        Move actualMove = FenStrategy.bestResponse(board);
        assertEquals(MoveConvertor.stringToMove(expectedMove), actualMove, "Error calculating fen [" + fen + "] expected " + expectedMove + " actual ");

    }

    private static Stream<Arguments> chessFenAndMoves()
    {
        return Stream.of(
                Arguments.of("B4rk1/5ppp/p3p3/1p6/3PRn1q/5P1P/PPPQ1P2/R5K1 b - - 2 20", "h4g5")
        );
    }
}
