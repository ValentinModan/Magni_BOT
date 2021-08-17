import api.Controller;
import api.RequestAPI;
import api.RequestController;
import api.account.AccountUpgrade;
import api.bot.MakeABotMove;
import api.challenges.Challenge;
import api.games.GetMyOwnGoingGames;
import api.games.owngame.NowPlaying;
import api.json.challenge.AcceptChallenge;
import api.json.challenge.ListYourChallenges;
import board.Board;
import board.setup.BoardSetup;
import game.GameBoard;

public class Main
{

    static String[] whiteMoves = {"d2d4", "a2a3", "b2b4"};
    static String[] blackMoves = {"a7a5", "b7b5", "c7c5"};

    public static void main(String[] args)
    {
        GameBoard gameBoard = new GameBoard();

        try {
            gameBoard.startPlayerGame();







        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void workingGame()
    {
        try {


            //  AccountUpgrade accountUpgrade = new AccountUpgrade();

            //  RequestController.sendRequest(accountUpgrade);


            System.out.println("Testing the responses");

            GameBoard gameBoard = new GameBoard();


            Controller controller = new Controller();

            // controller.sendRestTemplateRequest();

            //gameBoard.startGame();

            ListYourChallenges listYourChallenges = new ListYourChallenges();

            listYourChallenges = (ListYourChallenges) RequestController.sendRequest(listYourChallenges);

            AcceptChallenge acceptChallenge = new AcceptChallenge(listYourChallenges.getIn().get(0).getId());

            RequestController.sendRequest(acceptChallenge);

            GetMyOwnGoingGames getMyOwnGoingGames = new GetMyOwnGoingGames();

            getMyOwnGoingGames = (GetMyOwnGoingGames) RequestController.sendRequest(getMyOwnGoingGames);

            int          i = 0;
            MakeABotMove makeABotMove;
            while (true) {
                while (!getMyOwnGoingGames.getNowPlaying().get(0).getIsMyTurn().equals("true")) {
                    getMyOwnGoingGames = (GetMyOwnGoingGames) RequestController.sendRequest(getMyOwnGoingGames);

                    Thread.sleep(2000);
                }

                NowPlaying nowPlaying = getMyOwnGoingGames.getNowPlaying().get(0);


                if (nowPlaying.getColor().equals("white")) {
                    makeABotMove = new MakeABotMove(nowPlaying.getGameId(), whiteMoves[i++]);
                } else {
                    makeABotMove = new MakeABotMove(nowPlaying.getGameId(), blackMoves[i++]);
                }
                // RequestController.sendRestTemplateRequest(makeABotMove, makeABotMove.getGameId(), makeABotMove.getMove());
                RequestController.sendRequest(makeABotMove);
                Thread.sleep(20000);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}