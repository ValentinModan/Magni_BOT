import board.Board
import board.Position
import board.moves.Move
import board.moves.calculator.PieceMoveStrategy
import board.moves.calculator.pieces.PieceMoveCalculator
import board.pieces.Piece

import java.util.stream.Collectors

class PossibleMovesCalculator1 {

    static List<Move> getPossibleMoves(Board board) {

        List<Position> positionList = new ArrayList<>(board.getMovingPiecesMap().keySet())

        return positionList.stream()
                .map(position -> computeAllPossibleMovesFromPosition(board, position))
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
    }

    static List<Move> computeAllPossibleMovesFromPosition(Board board, Position position) throws Exception {
        PieceMoveCalculator pieceMoveCalculator = PieceMoveStrategy.generatePieceCalculatorStrategy(board.getMovingPiece(position).getPieceType());

        return pieceMoveCalculator.computeMoves(board, position)
    }
}
