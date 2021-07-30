package game.kingcheck.attacked;

import board.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KingSafety {

    private static List<AttackedStrategy> attackedStrategyList = new ArrayList<>(Arrays.asList(
            new PawnAttackedStrategy(),
            new RookAttackedStrategy(),
            new KnightAttackedStrategy(),
            new BishopAttackedStrategy(),
            new QueenAttackedStrategy()));

    public static int getNumberOfAttackers(Board board,boolean isWhiteKing)
    {
        int numberOfAttackers = 0;

        for(AttackedStrategy attackedStrategy: attackedStrategyList)
        {
            if(attackedStrategy.isAttackingTheKing(board,isWhiteKing))
            {
                numberOfAttackers++;
            }
            // You can not be attacked by more than two pieces
            if(numberOfAttackers>=2)
            {
                return numberOfAttackers;
            }
        }
        return numberOfAttackers;
    }

}
