package fen.mate;

import board.Board;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.setup.BoardSetup;
import fen.FenStrategy;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MateIn1
{

    @ParameterizedTest
    @MethodSource("chessFenAndMoves")
    public void mateIn1(String fen, String expectedMove)
    {
        Board board = new Board();
        BoardSetup.fenNotationBoardSetup(board, fen);

        Move actualMove = FenStrategy.bestResponse(board);
        assertEquals(MoveConvertor.stringToMove(expectedMove), actualMove, "Error calculating fen [" + fen + "] expected " + expectedMove + " actual " + actualMove);

    }

    private static Stream<Arguments> chessFenAndMoves()
    {
        return Stream.of(
                Arguments.of("3r4/kp3p2/p1P1b3/8/P1P3pN/2b1P1B1/4KPP1/1R5R b - - 0 27", "e6c4"),
                Arguments.of("8/4R1pp/5pk1/8/6P1/1P2R2P/r6r/5K2 b - - 0 42", "h2h1"),
                Arguments.of("2Rr2k1/3Pr3/2B5/1P3p1b/P2Q1K1P/2B5/5P1P/3q4 b - - 12 42", "d1g4"),
                Arguments.of("r4b1r/2q1k3/p3ppQn/np6/1p1Bb3/1B2P3/P1P2PPP/R2R3K w - - 0 21", "d4f6"),
                Arguments.of("kr6/P5p1/4p2n/p7/3P1B2/4P3/1R1K1p2/7q w - - 0 36", "a7b8"),
                Arguments.of("1rbk3r/p5p1/2p1p3/3p4/5Q2/1PPBb1p1/P6P/RN3R1K b - - 0 21", "h8h2"),
                Arguments.of("2kr3r/R1p2p1p/2Qqp3/8/P2P1p2/2P3P1/5PP1/6K1 w - - 4 21", "a7a8")
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected)
    {
        assertEquals(expected, Strings.isBlank(input));
    }

    private static Stream<Arguments> provideStringsForIsBlank()
    {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }
}
