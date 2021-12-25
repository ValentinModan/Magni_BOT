package openings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class OpeningController
{
    Random random = new Random();
    public static List<Opening> openingList = new ArrayList<>();

    public OpeningController(List<String> openingListString)
    {
        for (String element : openingListString) {
            Opening opening = null;
            try {
                opening = new Opening(element);
            } catch (Exception e) {
                System.out.println("Invalid index for " + element);
            }
            if (opening != null) {
                openingList.add(opening);
            }
        }
    }

    public String generateMove()
    {
        if (openingList.isEmpty()) {
            return null;
        }
        int randomMovePosition = random.nextInt(openingList.size());

        return openingList.get(randomMovePosition).getNextMove();
    }

    public String nextMove()
    {
        String nextMove = generateMove();
        //TODO: check this code
        if (nextMove != null) {
            filterWithMove(nextMove);
        }
        return nextMove;
    }

    public void filterWithMove(String move)
    {
        openingList = openingList.stream().filter(opening ->
             opening.isThisOpening(move) &&
                    opening.hasContinuation()
        ).collect(Collectors.toList());
        Opening.addMove(move);
    }
}
