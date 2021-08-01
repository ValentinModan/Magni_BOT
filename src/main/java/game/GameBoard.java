package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.setup.BoardSetup;
import game.kingcheck.attacked.KingSafety;
import reader.ConsoleReader;

public class GameBoard {
    boolean isWhiteToPlay = true;

    OptimizedBoard actualBoard;

    public GameBoard() {
        actualBoard = new OptimizedBoard();
        BoardSetup.setupBoard(actualBoard);
    }

    public void startGame()
    {
        System.out.println(actualBoard);
        actualBoard.computePossibleMoves();

        String moveString = ConsoleReader.readMove();

        Move move = MoveConvertor.toMove(moveString);
        while(true) {
            if (isWhiteToPlay) {
                if(!actualBoard.isValidMove(move))
                {
                    System.out.println(move + " is invalid");
                }
                actualBoard.move(move);

                if(KingSafety.getNumberOfAttackers(actualBoard)>0)
                {
                    System.out.println("Check!");
                }

            } else {
                actualBoard.move(move);
                if(KingSafety.getNumberOfAttackers(actualBoard)>0)
                {
                    System.out.println("Check!");
                }
            }
            System.out.println(actualBoard);

        }
    }
}
