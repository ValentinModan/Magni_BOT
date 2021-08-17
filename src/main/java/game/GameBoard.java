package game;

import api.RequestController;
import api.bot.MakeABotMove;
import api.games.GetMyOwnGoingGames;
import api.games.owngame.NowPlaying;
import api.json.challenge.AcceptChallenge;
import api.json.challenge.ListYourChallenges;
import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.setup.BoardSetup;
import game.kingcheck.attacked.KingSafety;
import reader.ConsoleReader;

import java.util.Random;

import static java.lang.Thread.sleep;

public class GameBoard {
    boolean isWhiteToPlay = true;

    OptimizedBoard actualBoard;

    public GameBoard() {
        actualBoard = new OptimizedBoard();
        BoardSetup.setupBoard(actualBoard);
    }


    public void startPlayerGame() throws InterruptedException
    {
        ListYourChallenges listYourChallenges = new ListYourChallenges();

        listYourChallenges = (ListYourChallenges) RequestController.sendRequest(listYourChallenges);

        AcceptChallenge acceptChallenge = new AcceptChallenge(listYourChallenges.getIn().get(0).getId());

        RequestController.sendRequest(acceptChallenge);

        GetMyOwnGoingGames getMyOwnGoingGames = new GetMyOwnGoingGames();


        while (true) {
            getMyOwnGoingGames =(GetMyOwnGoingGames) RequestController.sendRequest(getMyOwnGoingGames);
            while (!getMyOwnGoingGames.getNowPlaying().get(0).getIsMyTurn().equals("true")) {
                getMyOwnGoingGames = (GetMyOwnGoingGames) RequestController.sendRequest(getMyOwnGoingGames);

                sleep(2000);
            }

            NowPlaying nowPlaying = getMyOwnGoingGames.getNowPlaying().get(0);


            if(nowPlaying.getLastMove().equals(""))
            {
                //do the first move
                actualBoard.computePossibleMoves();
                Move  actualMove = actualBoard.getPossibleMoves().get(0);
                actualBoard.move(actualMove);

                //set black to move
                actualBoard.setWhiteToMove(!actualBoard.isWhiteToMove());

                //send the actual move
                MakeABotMove makeABotMove1 = new MakeABotMove(nowPlaying.getGameId(),actualMove.move());
                RequestController.sendRequest(makeABotMove1);

            }
            else
            {
                //make the opponent move on the board
                actualBoard.move(MoveConvertor.toMove(nowPlaying.getLastMove()));
                actualBoard.setWhiteToMove(!actualBoard.isWhiteToMove());

                //compute the possible moves
                actualBoard.computePossibleMoves();


                //do the move on the board

                int possibleMoves =actualBoard.getPossibleMoves().size();

                Move actualMove = tryMove(actualBoard,possibleMoves,nowPlaying);

                actualBoard.move(actualMove);


                actualBoard.setWhiteToMove(!actualBoard.isWhiteToMove());


            }


            sleep(2000);
        }

    }

   public static Move tryMove(OptimizedBoard actualBoard, int possibleMoves, NowPlaying nowPlaying)
   {
       Move actualMove;
       try {
           int randomnumber = new Random().nextInt();
           if (randomnumber < 0) {
               randomnumber = randomnumber * -1;
           }

           randomnumber = randomnumber % possibleMoves;
           actualMove = actualBoard.getPossibleMoves().get(randomnumber);
           //send the move
           MakeABotMove makeABotMove1 = new MakeABotMove(nowPlaying.getGameId(), actualMove.move());
           RequestController.sendRequest(makeABotMove1);
       }
       catch (Exception e)
       {
           try {
               sleep(200);
           } catch (InterruptedException interruptedException) {
               interruptedException.printStackTrace();
           }
           return tryMove(actualBoard,possibleMoves,nowPlaying);
       }

       return actualMove;
   }
    public void startGame()
    {
        System.out.println(actualBoard);
        actualBoard.computePossibleMoves();

        while(true) {
            String moveString = ConsoleReader.readMove();

            Move move = MoveConvertor.toMove(moveString);
            actualBoard.computePossibleMoves();
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
            actualBoard.setWhiteToMove(!actualBoard.isWhiteToMove());
            System.out.println(actualBoard);

        }
    }
}
