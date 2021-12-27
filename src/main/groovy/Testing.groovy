import board.pieces.Piece

class Testing {

    static void main(String[] args) {
        println "hello"
    }

    public static void test(Piece piece)
    { piece?.isOpponentOf(piece)

    }
}
