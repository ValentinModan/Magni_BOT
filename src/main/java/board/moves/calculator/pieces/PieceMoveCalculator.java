package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.Move;

import java.util.List;

public abstract class PieceMoveCalculator {


   public abstract List<Move> computeMoves(Board board, Position position);
}
