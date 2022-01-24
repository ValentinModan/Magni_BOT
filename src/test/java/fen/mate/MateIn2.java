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
        assertEquals(MoveConvertor.stringToMove(expectedMove), actualMove, "Error calculating fen [" + fen + "] expected " + expectedMove + " actual. Please check https://fen2png.com/api/?fen="+fen);

    }

    private static Stream<Arguments> chessFenAndMoves()
    {
        return Stream.of(
               // Arguments.of("r1b4R/2Q2p2/p2pk3/1p4R1/2nq1P2/P4K2/2P5/8 w - - 2 36", "h8e8"),
                Arguments.of("r6k/6pp/P7/N1R2p2/P2q4/N3rPQP/6P1/2R3K1 b - - 0 32", "e3e1"),
                Arguments.of("1rb3k1/7p/p3p1p1/2pp2BP/1N2n3/2bBQ3/P4rP1/2KR3R b - - 0 24", "c3b2")
        );
        //All moves are [e2e4, d7d5, e4d5, d8d5, b1c3, d5e6, d1e2, e6e2, f1e2, c8d7, c3d5, e8d8, d5b4, b8c6, c2c3, a7a5, b4d3, f7f6, g2g3, e7e5, f2f3, g7g5, e2d1, g8e7, d3c5, d8c8, a2a4, b7b6, c5d7, c8d7, b2b3, e7f5, d1c2, c6e7, c2f5, e7f5, g3g4, f5g7, h2h3, a8d8, d2d4, g7e6, d4e5, f6e5, h1h2, d7c8, c1a3, f8a3, a1a3, d8d7, b3b4, a5b4, c3b4, h8d8, a3a1, e6d4, h3h4, g5h4, g4g5, c8b7, b4b5, d8g8, f3f4, e5f4, a4a5, g8g5, a5a6, b7a7, e1f1, d4b5, h2h4, d7f7, f1f2, b5d6, g1f3, d6e4, f2e2, g5g2, e2d3, e4c5, d3c4, c5a6, c4b5, g2b2, b5a4, a6c5, a4a3, b2b4, a3b4, a7b7, b4a3, c7c6, a3b4, c5e6, b4a3, c6c5, a3b3, b6b5, b3b2, b7b6, b2c3, c5c4, c3b2, b5b4, b2c2, b6b5, c2c1, c4c3, c1d1, b4b3, h4h5, b5b4, d1e2, f7d7, a1d1, d7d1, e2d1, c3c2, d1c1, b4c3 should be h5b5 https://lichess.org/D8UJhtPI
    }
}
