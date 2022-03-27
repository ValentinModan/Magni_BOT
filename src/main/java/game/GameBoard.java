package game;

import api.RequestController;
import api.bot.MakeABotMove;
import api.challenges.ChallengeAPlayer;
import api.games.GetMyOwnGoingGames;
import api.games.NowPlaying;
import api.json.challenge.AcceptChallenge;
import api.json.challenge.ListYourChallenges;
import board.Board;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.setup.BoardSetup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import openings.OpeningController;
import openings.OpeningReader;

import static java.lang.Thread.sleep;

@Slf4j
public class GameBoard
{
    //todo: make setup file
    public static int depth = 3;

    public static GetMyOwnGoingGames getMyOwnGoingGames;

    public static Board actualBoard;
    OpeningController openingController = new OpeningController(OpeningReader.readOpenings());
    SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();

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
        challengePlayer(player, 600, 15, true);
    }

    public void challengePlayer(String playerName, int timeInSeconds, int increment, boolean rated) throws InterruptedException, CloneNotSupportedException
    {
        ChallengeAPlayer challengeAPlayer = new ChallengeAPlayer(playerName);
        RequestController.sendRequestWithProperties(challengeAPlayer, timeInSeconds, increment, rated);
        startPlayerGame();
    }

    @SneakyThrows
    public void startPlayerGame()
    {
        getMyOwnGoingGames = new GetMyOwnGoingGames();

        while (true) {

            while (!GameBoardHelper.isMyTurn()) {
                sleep(500);
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

    public void ownMoveCalculator(String gameId) throws Exception
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

    private void makeMyOwnMove(String gameId, String move) throws Exception
    {
        boolean computationStarted = false;
        log.info("Generated opening move " + move);
        Move actualMove = MoveConvertor.stringToMove(move);
        if (actualMove == null) {
            computationStarted = true;
            actualMove = singleThreadCalculator.bestResponse(actualBoard);
        }

        MakeABotMove makeABotMove = new MakeABotMove(gameId, actualMove.move());
        RequestController.sendRequest(makeABotMove);

        actualBoard.actualMove(actualMove);
        actualBoard.nextTurn();
        singleThreadCalculator.updateFenMap(actualBoard);
        if (computationStarted) {
            System.gc();
            singleThreadCalculator.computeTree();
            singleThreadCalculator.computeMoves();
        }
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
        Board.myColorWhite = true;
        //set black to move
        actualBoard.nextTurn();

        //send the actual move
        MakeABotMove makeABotMove = new MakeABotMove(gameId, actualMove.move());
        RequestController.sendRequest(makeABotMove);
    }
}
