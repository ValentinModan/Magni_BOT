import board.pieces.Piece

class Testing {

    static void main(String[] args) {
        println "hello"
    }

    static void test(Piece piece)
    { piece?.isOpponentOf(piece)
        println "meh"

    }
}
