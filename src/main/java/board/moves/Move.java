package board.moves;

import board.Position;

import java.util.Objects;

public class Move {
    private Position initialPosition;
    private Position finalPosition;

    public Move(Position initialPosition, Position finalPosition) {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Position initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Position getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(Position finalPosition) {
        this.finalPosition = finalPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return initialPosition.equals(move.initialPosition) && finalPosition.equals(move.finalPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialPosition, finalPosition);
    }

    @Override
    public String toString() {
        return "Move{" + initialPosition + finalPosition + '}';
    }
}