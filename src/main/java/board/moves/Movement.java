package board.moves;

public enum Movement {
    UP(0, 1),
    UP_TWO(0,2),
    DOWN(0, -1),
    DOWN_TWO(0,-2),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    LEFT_DOWN(-1, -1),
    DOWN_RIGHT(1, -1),
    KNIGHT_UP_LEFT(-1, 2),
    KNIGHT_LEFT_UP(-2, 1),
    KNIGHT_LEFT_DOWN(-2, -1), //i'm feeling left down :(,
    KNIGHT_DOWN_LEFT(-1, -2),
    KNIGHT_DOWN_RIGHT(1, -2),
    KNIGHT_RIGHT_DOWN(2, -1),
    KNIGHT_RIGHT_UP(2, 1),
    KNIGHT_UP_RIGHT(1, 2);

    private final int row;
    private final int column;

    Movement(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Movement opposite()
    {
        switch (this){
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: throw new IllegalStateException("Something went wrong");
        }

    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
