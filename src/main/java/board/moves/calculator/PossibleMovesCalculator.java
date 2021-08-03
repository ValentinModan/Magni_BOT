package board.moves.calculator;

import board.Board;
import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.calculator.pieces.PieceMoveCalculator;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PossibleMovesCalculator {

    public static List<Move> getPossibleMoves(OptimizedBoard board) {
        List<Move> moveList = new ArrayList<>();
        Set<Position> positionList = board.getMovingPiecesMap().keySet();

        positionList.forEach(position -> moveList.addAll(computeAllPossibleMovesFromPosition(board, position)));

        //todo check possible stream
        // return positionList.stream().map(position -> computeAllPossibleMovesFromPosition(board,position)).flatMap(Collection::stream).collect(Collectors.toList());

        return moveList;
    }

    public static List<Move> computeAllPossibleMovesFromPosition(OptimizedBoard board, Position position) {
        Piece currentPiece = board.getPiece(position);
        PieceMoveCalculator pieceMoveCalculator = PieceMoveStrategy.getCalculator(currentPiece);

        return pieceMoveCalculator.computeMoves(board, position);
    }

}
