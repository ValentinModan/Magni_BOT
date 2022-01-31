package board;

import board.moves.Movement;

import java.util.Objects;

public class Position
{
    private final char column;
    private final int row;

    public Position(int column, int row)
    {
        this((char) ('a' - 1 + column), row);
    }

    public Position(char column, int row)
    {
        this.column = column;
        this.row = row;
    }

    public boolean isValid()
    {
        return ('a' <= column && column <= 'h') &&
                (1 <= row && row <= 8);
    }

    public boolean sameRow(Position position)
    {
        return this.row == position.getRow();
    }

    public boolean sameColumn(Position position)
    {
        return this.column == position.getColumn();
    }

    public boolean sameDiagonal(Position position)
    {
        return Math.abs(this.row - position.row) == Math.abs(this.column - position.column);
    }

    public Position move(Movement movements)
    {
        return new Position(column - 'a' + 1 + movements.getColumn(), row + movements.getRow());
    }

    public Position leftDiagonal(boolean whitePiece)
    {
        if (whitePiece) {
            Position position = this.move(Movement.UP_LEFT);

            return position.isValid() ? position : null;
        }

        Position position = this.move(Movement.DOWN_RIGHT);

        return position.isValid() ? position : null;
    }

    public char getColumn()
    {
        return column;
    }

    public int getRow()
    {
        return row;
    }

    public Position rightDiagonal(boolean whitePiece)
    {
        if (whitePiece) {
            Position position = this.move(Movement.UP_RIGHT);

            return position.isValid() ? position : null;
        }

        Position position = this.move(Movement.LEFT_DOWN);

        return position.isValid() ? position : null;
    }

    public Movement getDiagonalMovement(Position position)
    {
        if (row < position.row) {
            if (column < position.column) {
                return Movement.UP_RIGHT;
            }
            return Movement.UP_LEFT;
        }
        if (column < position.column) {
            return Movement.DOWN_RIGHT;
        }

        return Movement.LEFT_DOWN;
    }

    public Movement lineDirection(Position position)
    {
        if (sameRow(position)) {
            return this.column < position.column ? Movement.RIGHT : Movement.LEFT;
        }
        if (sameColumn(position)) {
            return this.row < position.row ? Movement.UP : Movement.DOWN;
        }
        return null;
    }

    public Movement diagonalDirection(Position position)
    {
        if (sameDiagonal(position)) {
            if (this.column < position.column) {
                return this.row < position.row ? Movement.UP_RIGHT : Movement.DOWN_RIGHT;
            }
            return this.row < position.row ? Movement.UP_LEFT : Movement.LEFT_DOWN;
        }
        return null;
    }

    public Movement castleDirection(Position position)
    {
        if (column < position.column) {
            return Movement.RIGHT;
        }
        return Movement.LEFT;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(column, row);
    }

    @Override
    public String toString()
    {
        return "" + column + row;
    }
}
