package board.moves.calculator;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.calculator.pieces.PieceMoveCalculator;
import board.pieces.Piece;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
                .generatePieceCalculatorStrategy(board.getMovingPiece(position).getPieceType())
                .computeMoves(board, position);
    }

}
