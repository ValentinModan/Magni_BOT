package board.moves.movetypes;

import board.Position;

public class EnPassantMove extends Move
{
    public EnPassantMove(Position initialPosition, Position finalPosition)
    {
        super(initialPosition, finalPosition);
    }
}
