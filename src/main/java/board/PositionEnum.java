package board;

public enum PositionEnum
{
    A1('a',1),
    B1('b',1),
    C1('c',1),
    D1('d',1),
    E1('e',1),
    F1('f',1),
    G1('g',1),
    H1('h',1),
    A2('a',2),
    B2('b',2),
    C2('c',2),
    D2('d',2),
    E2('e',2),
    F2('f',2),
    G2('g',2),
    H2('h',2),
    A3('a',3),
    B3('b',3),
    C3('c',3),
    D3('d',3),
    E3('e',3),
    F3('f',3),
    G3('g',3),
    H3('h',3),
    A4('a',4),
    B4('b',4),
    C4('c',4),
    D4('d',4),
    E4('e',4),
    F4('f',4),
    G4('g',4),
    H4('h',4),
    A5('a',5),
    B5('b',5),
    C5('c',5),
    D5('d',5),
    E5('e',5),
    F5('f',5),
    G5('g',5),
    H5('h',5),
    A6('a',6),
    B6('b',6),
    C6('c',6),
    D6('d',6),
    E6('e',6),
    F6('f',6),
    G6('g',6),
    H6('h',6),
    A7('a',7),
    B7('b',7),
    C7('c',7),
    D7('d',7),
    E7('e',7),
    F7('f',7),
    G7('g',7),
    H7('h',7),
    A8('a',8),
    B8('b',8),
    C8('c',8),
    D8('d',8),
    E8('e',8),
    F8('f',8),
    G8('g',8),
    H8('h',8);

    private final Position position;

    PositionEnum(char column, int row)
    {
        position = new Position(column,row);
    }

    public Position getPosition()
    {
        return position;
    }
}
