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
import openings.OpeningController;
import openings.OpeningReader;

import java.util.Random;

import static java.lang.Thread.sleep;

public class GameBoard
{
    public static final  int DEFAULT_DEPTH      = 6;
    public static        int depth              = DEFAULT_DEPTH;
    public static final  int MAX_DEPTH          = 12;
    public static final  int MAX_DEPTH_MID_GAME = 6;
    private static final int MOVES              = 300;
    OptimizedBoard    actualBoard;
    OpeningController openingController = new OpeningController(OpeningReader.readOpenings());

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
            }
            else {
                OptimizedBoard.displayAllMoves();

                makeEnemyMove(nowPlaying.getLastMove());

                makeMyOwnMove(nowPlaying.getGameId());
            }
        }
    }

    private void makeMyOwnMove(String gameId)
    {
        String move = openingController.generateMove();
        openingController.filterWithMove(move);
        newDepth();
        System.out.println("Generated opening move " + move);
        Move actualMove = MoveConvertor.stringToMove(move);
        if (actualMove == null) {
            actualMove = CleanMoveCalculator.calculate2(actualBoard, depth);
        }

        MakeABotMove makeABotMove1 = new MakeABotMove(gameId, actualMove.move());
        System.out.println("Best move chosen is " + actualMove + " with score of " + actualMove.getScore());
        System.out.println(actualBoard);
        RequestController.sendRequest(makeABotMove1);

        actualBoard.actualMove(actualMove);
        actualBoard.nextTurn();
    }

    private void makeEnemyMove(String lastMove)
    {
        //make the opponent move on the board
        actualBoard.actualMove(MoveConvertor.stringToMove(lastMove));
        actualBoard.nextTurn();
        openingController.filterWithMove(lastMove);
        System.out.println("Enemy made move ");
        System.out.println(actualBoard);
    }

    private void firstMove(String gameId)
    {
        Move actualMove = MoveConvertor.stringToMove(openingController.nextMove());

        actualBoard.actualMove(actualMove);
        //set black to move
        actualBoard.nextTurn();

        //send the actual move
        MakeABotMove makeABotMove1 = new MakeABotMove(gameId, actualMove.move());
        RequestController.sendRequest(makeABotMove1);
    }

    public void newDepth()
    {
        int opponentPiecesLeft = actualBoard.opponentPiecesLeft();
        if (opponentPiecesLeft >= 10) {
            depth = GameBoard.DEFAULT_DEPTH;
        }
        else {
            depth = GameBoard.DEFAULT_DEPTH + (16 - opponentPiecesLeft) / 2;
            if (depth > MAX_DEPTH) {
                depth = MAX_DEPTH;
            }
            if (actualBoard.myPiecesLeft() + opponentPiecesLeft >= 8) {
                depth = MAX_DEPTH_MID_GAME;
            }
        }
        depth += OptimizedBoard.actualMoves.size() / 30;
        depth -= depth % 2;
        System.out.println("Computing for new depth: " + depth);
    }
}
