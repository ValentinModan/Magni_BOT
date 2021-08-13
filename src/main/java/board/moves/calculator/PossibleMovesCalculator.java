package board.moves.calculator;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.calculator.pieces.PieceMoveCalculator;
import board.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
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
        log.info("Computing possible moves for: " + currentPiece + position);

        if(currentPiece == null)
        {
            return Collections.emptyList();
            //todo remove this
        }
        PieceMoveCalculator pieceMoveCalculator = PieceMoveStrategy.getCalculator(currentPiece);

        //TODO: current piece can also be passed as a parameter
        return pieceMoveCalculator.computeMoves(board, position);
    }

}
