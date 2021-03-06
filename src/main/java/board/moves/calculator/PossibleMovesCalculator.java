package board.moves.calculator;

import board.Board;
import board.Position;
import board.moves.movetypes.Move;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PossibleMovesCalculator
{
    public static List<Move> getPossibleMoves(Board board)
    {
        return board.getMovingPiecesMap().keySet().stream()
                .map(position -> computeAllPossibleMovesFromPosition(board, position))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public static List<Move> computeAllPossibleMovesFromPosition(Board board, Position position)
    {
        return PieceMoveStrategy
                .getPieceCalculatorStrategy(board.getMovingPiece(position).getPieceType())
                .computeMoves(board, position);
    }

}
