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
        int length = openingList.size();

        if (length == 0) {
            return null;
        }

        int position = random.nextInt(length);

        return openingList.get(position).getNextMove();
    }

    public String nextMove()
    {
        String nextMove = generateMove();
        if (nextMove != null) {
            filterWithMove(nextMove);
        }
        return nextMove;
    }

    public void filterWithMove(String move)
    {
        openingList = openingList.stream().filter(opening -> {
            return opening.isThisOpening(move) &&
                    opening.hasContinuation();
        }).collect(Collectors.toList());
        Opening.addMove(move);
    }

    public static List<Opening> getOpeningList()
    {
        return openingList;
    }

    public void addMove(String move)
    {
        Opening.addMove(move);
    }

    public static void displayOpenings()
    {
        for (Opening opening : openingList) {
            System.out.println(opening);
        }
    }
}
