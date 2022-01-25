package game;

import api.RequestController;
import api.bot.MakeABotMove;
import api.challenges.ChallengeAPlayer;
import api.games.GetMyOwnGoingGames;
import api.games.owngame.NowPlaying;
import api.json.challenge.AcceptChallenge;
import api.json.challenge.ListYourChallenges;
import board.Board;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.setup.BoardSetup;
import game.multithreadedmap.MultiThreadedCalculator;
import lombok.extern.slf4j.Slf4j;
import openings.OpeningController;
import openings.OpeningReader;

import java.util.List;

import static java.lang.Thread.sleep;

@Slf4j
public class GameBoard
{
    public static int DEFAULT_DEPTH = 3;
    public static int depth = DEFAULT_DEPTH;

    public static GetMyOwnGoingGames getMyOwnGoingGames;

    public static Board actualBoard;
    OpeningController openingController = new OpeningController(OpeningReader.readOpenings());

    public GameBoard()
    {
        actualBoard = new Board();
        BoardSetup.setupBoard(actualBoard);
    }

    public void waitForChallengeAndAcceptIt() throws InterruptedException, CloneNotSupportedException
    {
        ListYourChallenges listYourChallenges = (ListYourChallenges) RequestController.sendRequest(new ListYourChallenges());
        while (listYourChallenges.getIn().isEmpty()) {
            listYourChallenges = (ListYourChallenges) RequestController.sendRequest(listYourChallenges);
        }
        AcceptChallenge acceptChallenge = new AcceptChallenge(listYourChallenges.getIn().get(0).getId());

        RequestController.sendRequest(acceptChallenge);
        startPlayerGame();
    }

    public void challengePlayer(String player) throws InterruptedException, CloneNotSupportedException
    {
        challengePlayer(player, 180, 10, true);
    }

    public void challengePlayer(String playerName, int timeInSeconds, int increment, boolean rated) throws InterruptedException, CloneNotSupportedException
    {
        ChallengeAPlayer challengeAPlayer = new ChallengeAPlayer(playerName);
        RequestController.sendRequestWithProperties(challengeAPlayer, timeInSeconds, increment, rated);
        startPlayerGame();
    }


    public void startPlayerGame() throws InterruptedException, CloneNotSupportedException
    {
        getMyOwnGoingGames = new GetMyOwnGoingGames();

        while (true) {

            while (!GameBoardHelper.isMyTurn()) {
                sleep(2000);
            }
            NowPlaying nowPlaying = getMyOwnGoingGames.getNowPlaying().get(0);

            if (GameBoardHelper.isFirstMoveOfTheGame(nowPlaying)) {
                //do the first move
                firstMove(nowPlaying.getGameId());
            }
            else {
                Board.displayAllMoves();

                makeEnemyMove(nowPlaying.getLastMove());
                ownMoveCalculator(nowPlaying.getGameId());
            }
        }
    }

    public void ownMoveCalculator(String gameId) throws InterruptedException, CloneNotSupportedException
    {
        //check opening moves
        String openingMove = openingController.generateMove();
        openingController.filterWithMove(openingMove);

        if (openingMove != null) {
            makeMyOwnMove(gameId, openingMove);
            return;
        }
        makeMyOwnMove(gameId, null);
    }

    AllPossibleMovesMultiThreaded allPossibleMovesMultiThreaded = new AllPossibleMovesMultiThreaded();
    MultiThreadedCalculator multiThreadedCalculator = new MultiThreadedCalculator();
    SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();

    private void makeMyOwnMove(String gameId, String move) throws CloneNotSupportedException, InterruptedException
    {
        GameBoardHelper.computeNewDepth();
        log.debug("Generated opening move " + move);
        Move actualMove = MoveConvertor.stringToMove(move);
        if (actualMove == null) {
            actualMove = singleThreadCalculator.bestResponse(actualBoard);
        }

        MakeABotMove makeABotMove1 = new MakeABotMove(gameId, actualMove.move());
        log.info("Best move chosen is " + actualMove + " with score of " + actualMove.getScore());
        log.info(actualBoard.toString());
        RequestController.sendRequest(makeABotMove1);

        System.gc();

        actualBoard.actualMove(actualMove);
        actualBoard.nextTurn();
    }

    private void makeEnemyMove(String lastMove)
    {
        //make the opponent move on the board
        actualBoard.actualMove(MoveConvertor.stringToMove(lastMove));
        actualBoard.nextTurn();
        openingController.filterWithMove(lastMove);
        log.info("Enemy made a move:");
        log.info(actualBoard.toString());

    }

    private void firstMove(String gameId)
    {
        Move actualMove = MoveConvertor.stringToMove(openingController.nextMove());

        actualBoard.actualMove(actualMove);
        //set black to move
        actualBoard.nextTurn();

        //send the actual move
        MakeABotMove makeABotMove = new MakeABotMove(gameId, actualMove.move());
        RequestController.sendRequest(makeABotMove);
    }
}
