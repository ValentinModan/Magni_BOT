package game;

import api.RequestController;
import api.bot.MakeABotMove;
import api.games.GetMyOwnGoingGames;
import api.games.owngame.NowPlaying;
import api.json.challenge.AcceptChallenge;
import api.json.challenge.ListYourChallenges;
import board.Board;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.setup.BoardSetup;
import game.multithreadedmap.MultiThreadedCalculator;
import openings.OpeningController;
import openings.OpeningReader;

import java.util.List;

import static java.lang.Thread.sleep;

public class GameBoard
{
    public static final int DEFAULT_DEPTH = 6;
    public static       int depth         = DEFAULT_DEPTH;

    public static GetMyOwnGoingGames getMyOwnGoingGames;

    public static Board actualBoard;
    OpeningController openingController = new OpeningController(OpeningReader.readOpenings());
    private List<Move> opponentResponse = null;

    public GameBoard()
    {
        actualBoard = new Board();
        BoardSetup.setupBoard(actualBoard);
    }

    public void startPlayerGame() throws InterruptedException
    {
        ListYourChallenges listYourChallenges = (ListYourChallenges) RequestController.sendRequest(new ListYourChallenges());

        while (listYourChallenges.getIn().isEmpty()) {
            listYourChallenges = (ListYourChallenges) RequestController.sendRequest(listYourChallenges);
        }
        AcceptChallenge acceptChallenge = new AcceptChallenge(listYourChallenges.getIn().get(0).getId());

        RequestController.sendRequest(acceptChallenge);

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

    public void ownMoveCalculator(String gameId)
    {
        //check opening moves
        String openingMove = openingController.generateMove();
        openingController.filterWithMove(openingMove);

        if (openingMove != null) {
            makeMyOwnMove(gameId, openingMove);
            return;
        }

        //is firstNonOpeningMove
        if (opponentResponse == null) {
            makeMyOwnMove(gameId, null);
        }
        else {
            Move moveResult = opponentResponse.stream()
                    .filter(move -> move.toString().equals(actualBoard.lastMove().toString()))
                    .findFirst().orElse(null);

            String bestResponse = null;
            if (moveResult != null && moveResult.getBestResponse() != null) {
                bestResponse = moveResult.getBestResponse().toString();
            }
            makeMyOwnMove(gameId, bestResponse);
        }
        // opponentResponse = CleanMoveCalculator.calculateAllMoveBestResponse(actualBoard, depth);
    }

    AllPossibleMovesMultiThreaded allPossibleMovesMultiThreaded = new AllPossibleMovesMultiThreaded();
    MultiThreadedCalculator       multiThreadedCalculator       = new MultiThreadedCalculator();
    SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();

    private void makeMyOwnMove(String gameId, String move)
    {
        GameBoardHelper.computeNewDepth();
        System.out.println("Generated opening move " + move);
        Move actualMove = MoveConvertor.stringToMove(move);
        if (actualMove == null) {
        //    actualMove = CleanMoveCalculator.calculate2(actualBoard, depth);
            try {
                actualMove = singleThreadCalculator.bestResponse(actualBoard);
               // actualMove = multiThreadedCalculator.possibleMoves(actualBoard);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
        System.out.println("Enemy made a move:");
        System.out.println(actualBoard);
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
