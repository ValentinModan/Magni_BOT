package board.moves;

import board.Position;
import board.pieces.Piece;
import game.gameSetupOptions.GameOptions;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Move implements Comparable<Move>
{
    private Position initialPosition;
    private Position finalPosition;
    private Position takenAnPassant;
    private Piece movingPiece;
    private Piece takenPiece;
    private Piece promotionPiece;
    private int score = 0;
    private boolean isPawnPromotion = false;
    private boolean isCastleMove = false;
    private boolean isAnPassant = false;
    private boolean isCheckMate;
    private Move bestResponse;

    public Move(boolean isCheckMate)
    {
        this.isCheckMate = isCheckMate;
        score = 10000;
    }

    public Move(int score)
    {
        this.score = score;
    }

    public Move(Position initialPosition, Position finalPosition, Piece movingPiece, boolean isPawnPromotion)
    {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
        this.movingPiece = movingPiece;
        this.isPawnPromotion = isPawnPromotion;
    }

    public Move(Position initialPosition, Position finalPosition)
    {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
        if (initialPosition.equals(finalPosition)) {
            int x = 0;
        }
    }

    public boolean isCheckMate()
    {
        return isCheckMate;
    }

    public void setCheckMate(boolean checkMate)
    {
        isCheckMate = checkMate;
    }

    public boolean isCastleMove()
    {
        return isCastleMove;
    }

    public void setCastleMove(boolean castleMove)
    {
        isCastleMove = castleMove;
    }

    public Position getInitialPosition()
    {
        return initialPosition;
    }

    public void setInitialPosition(Position initialPosition)
    {
        this.initialPosition = initialPosition;
    }

    public Position getFinalPosition()
    {
        return finalPosition;
    }

    public void setFinalPosition(Position finalPosition)
    {
        this.finalPosition = finalPosition;
    }

    public Piece getMovingPiece()
    {
        return movingPiece;
    }

    public void setMovingPiece(Piece movingPiece)
    {
        this.movingPiece = movingPiece;
    }

    public Piece getTakenPiece()
    {
        return takenPiece;
    }

    public void setTakenPiece(Piece takenPiece)
    {
        this.takenPiece = takenPiece;
    }

    public int getScore()
    {
        return GameOptions.getSingleMoveScore(this);
    }

    private int moveScore = 0;


    //score  including the best response and so on
    public int moveScore()
    {
        if (bestResponse != null) {
            moveScore = GameOptions.getSingleMoveScore(this) - bestResponse.moveScore();
            return moveScore;
        }

        moveScore = GameOptions.getSingleMoveScore(this);

        return moveScore;
    }

    public void setMoveScore(Integer moveScore)
    {
        this.moveScore = moveScore;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getActualScore()
    {
        return score;
    }

    public boolean isAnPassant()
    {
        return isAnPassant;
    }

    public void setAnPassant(boolean anPassant)
    {
        isAnPassant = anPassant;
    }

    public Position getTakenAnPassant()
    {
        return takenAnPassant;
    }

    public void setTakenAnPassant(Position takenAnPassant)
    {
        this.takenAnPassant = takenAnPassant;
    }

    public Move getBestResponse()
    {
        return bestResponse;
    }

    public synchronized void setBestResponse(Move bestResponse)
    {
        this.bestResponse = bestResponse;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Move move = (Move) o;
        return initialPosition.equals(move.initialPosition) && finalPosition.equals(move.finalPosition);
    }

    public boolean isPawnPromotion()
    {
        return isPawnPromotion;
    }

    public void setPawnPromotion(boolean pawnPromotion)
    {
        isPawnPromotion = pawnPromotion;
    }

    public String move()
    {
        return "" + initialPosition + finalPosition;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(initialPosition, finalPosition);
    }

    @Override
    public String toString()
    {
        return "" + initialPosition + finalPosition;
    }

    @Override
    public int compareTo(@NotNull Move o)
    {
        if (score == o.getScore()) {
            return 0;
        }
        return score < o.getScore() ? 1 : -1;
    }
}
