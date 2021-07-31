package board.moves.calculator;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.calculator.pieces.PieceMoveCalculator;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class PossibleMovesCalculator {


    List<Move> moveList = new ArrayList<>();

    public List<Move> getPossibleMoves(Board board) {
        List<Position> positionList = board.getCurrentPlayerPositionList();

        positionList.forEach(position -> moveList.addAll(computeAllPossibleMovesFromPosition(board, position)));

        //todo check possible stream
       // return positionList.stream().map(position -> computeAllPossibleMovesFromPosition(board,position)).flatMap(Collection::stream).collect(Collectors.toList());

        return moveList;
    }

    public List<Move> computeAllPossibleMovesFromPosition(Board board, Position position) {
        Piece currentPiece = board.getPosition(position);
        PieceMoveCalculator pieceMoveCalculator = PieceMoveStrategy.getCalculator(currentPiece);

        return pieceMoveCalculator.computeMoves(board,position);
    }

}
