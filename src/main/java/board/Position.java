package board;

import board.moves.Movement;

import java.util.Objects;

public class Position
{

    private char column;
    private int  row;

    public Position(int column, int row)
    {
        this((char) ('a' - 1 + column), row);
    }

    public Position(char column, int row)
    {
        this.column = column;
        this.row = row;
    }

    public char getColumn()
    {
        return column;
    }

    public int getRow()
    {
        return row;
    }

    public boolean isValid()
    {
        return ('a' <= column && column <= 'h') &&
                (1 <= row && row <= 8);
    }

    public Position move(Movement movements)
    {
        return new Position(column - 'a' + 1 + movements.getColumn(), row + movements.getRow());
    }

    public Movement castleDirection(Position position)
    {
        if(column< position.column)
        {
            return Movement.RIGHT;
        }
        return Movement.LEFT;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position1 = (Position) o;
        return column == position1.column && row == position1.row;
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
