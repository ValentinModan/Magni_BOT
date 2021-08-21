package game;

import api.RequestController;
import api.bot.MakeABotMove;
import api.games.GetMyOwnGoingGames;
import api.games.owngame.NowPlaying;
import api.json.challenge.AcceptChallenge;
import api.json.challenge.ListYourChallenges;
import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.pieces.Piece;
import board.setup.BoardSetup;

import java.util.Map;
import java.util.Random;

import static java.lang.Thread.sleep;

public class GameBoard
{
    public static  int DEPTH = 5;
    private static final int MOVES = 200;
    OptimizedBoard actualBoard;

    public GameBoard()
    {
        actualBoard = new OptimizedBoard();
        BoardSetup.setupBoard(actualBoard);
    }

    public void startPlayerGame()
    {
        ListYourChallenges listYourChallenges = new ListYourChallenges();

        listYourChallenges = (ListYourChallenges) RequestController.sendRequest(listYourChallenges);

        while (listYourChallenges.getIn().size() == 0) {
            listYourChallenges = (ListYourChallenges) RequestController.sendRequest(listYourChallenges);

        }
        AcceptChallenge acceptChallenge = new AcceptChallenge(listYourChallenges.getIn().get(0).getId());

        RequestController.sendRequest(acceptChallenge);

        GetMyOwnGoingGames getMyOwnGoingGames = new GetMyOwnGoingGames();

        while (true) {
            getMyOwnGoingGames = (GetMyOwnGoingGames) RequestController.sendRequest(getMyOwnGoingGames);
            while (!getMyOwnGoingGames.getNowPlaying().get(0).getIsMyTurn().equals("true")) {
                getMyOwnGoingGames = (GetMyOwnGoingGames) RequestController.sendRequest(getMyOwnGoingGames);
            }

            NowPlaying nowPlaying = getMyOwnGoingGames.getNowPlaying().get(0);

            if (GameBoardHelper.isFirstMoveOfTheGame(nowPlaying)) {
                //do the first move
                firstMove(nowPlaying.getGameId());
            } else {
                OptimizedBoard.displayAllMoves();

                makeEnemyMove(nowPlaying.getLastMove());

                makeMyOwnMove(nowPlaying.getGameId());
            }
        }
    }

    private void makeMyOwnMove(String gameId)
    {
        Move actualMove = MovesCalculator.calculate2(actualBoard, MOVES, DEPTH);

        MakeABotMove makeABotMove1 = new MakeABotMove(gameId, actualMove.move());
        System.out.println("Best move chosen is " + actualMove + " with score of " + actualMove.getScore());
        System.out.println(actualBoard);
        RequestController.sendRequest(makeABotMove1);

        actualBoard.actualMove(actualMove);
        DEPTH = actualBoard.newDepth();
        actualBoard.nextTurn();
    }

    private void makeEnemyMove(String lastMove)
    {
        //make the opponent move on the board
        actualBoard.actualMove(MoveConvertor.stringToMove(lastMove));
        actualBoard.nextTurn();
    }

    private void firstMove(String gameId)
    {
        //do the first move
        actualBoard.computePossibleMoves();
        Move actualMove = actualBoard.getPossibleMoves().get(0);
        actualBoard.actualMove(actualMove);

        //set black to move
        actualBoard.nextTurn();

        //send the actual move
        MakeABotMove makeABotMove1 = new MakeABotMove(gameId, actualMove.move());
        RequestController.sendRequest(makeABotMove1);
    }

    //remains as backup
    public static Move tryMove(OptimizedBoard actualBoard, int possibleMoves, NowPlaying nowPlaying)
    {
        Move actualMove;
        try {
            int randomNumber = new Random().nextInt();
            if (randomNumber < 0) {
                randomNumber = randomNumber * -1;
            }

            randomNumber = randomNumber % possibleMoves;
            actualMove = actualBoard.getPossibleMoves().get(randomNumber);
            //send the move
            MakeABotMove makeABotMove1 = new MakeABotMove(nowPlaying.getGameId(), actualMove.move());
            RequestController.sendRequest(makeABotMove1);
        } catch (Exception e) {
            try {
                sleep(200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            return tryMove(actualBoard, possibleMoves, nowPlaying);
        }

        return actualMove;
    }
}
