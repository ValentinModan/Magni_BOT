package board.moves;

import board.Position;
import board.pieces.Piece;

import java.util.Objects;

public class Move {
    private Position initialPosition;
    private Position finalPosition;
    private Position takenAnPassant;
    private Piece movingPiece;
    private Piece takenPiece;
    private int score;
    private boolean isPawnPromotion = false;
    private boolean isCastleMove = false;
    private boolean isAnPassant = false;

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

    public Move(Position initialPosition, Position finalPosition) {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
    }

    public boolean isCastleMove()
    {
        return isCastleMove;
    }

    public void setCastleMove(boolean castleMove)
    {
        isCastleMove = castleMove;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Position initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Position getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(Position finalPosition) {
        this.finalPosition = finalPosition;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

    public void setMovingPiece(Piece movingPiece) {
        this.movingPiece = movingPiece;
    }

    public Piece getTakenPiece() {
        return takenPiece;
    }

    public void setTakenPiece(Piece takenPiece) {
        this.takenPiece = takenPiece;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return initialPosition.equals(move.initialPosition) && finalPosition.equals(move.finalPosition);
    }

    public String move()
    {
        return "" + initialPosition + finalPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialPosition, finalPosition);
    }

    @Override
    public String toString() {
        return "Move{" + initialPosition + finalPosition + '}';
    }
}
