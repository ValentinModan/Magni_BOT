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

public class MateIn2
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
                Arguments.of("r1b4R/2Q2p2/p2pk3/1p4R1/2nq1P2/P4K2/2P5/8 w - - 2 36", "h8e8"),
                Arguments.of("r6k/6pp/P7/N1R2p2/P2q4/N3rPQP/6P1/2R3K1 b - - 0 32", "e3e1"),
                Arguments.of("1rb3k1/7p/p3p1p1/2pp2BP/1N2n3/2bBQ3/P4rP1/2KR3R b - - 0 24", "c3b2")
        );
    }
}
