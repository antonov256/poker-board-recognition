package com.antonov.poker.board_recognition.poker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TurnBoard implements Board {
    private final FlopBoard flopBoard;
    private final Card turnCard;

    private final List<Card> cards;

    public TurnBoard(FlopBoard flopBoard, Card turnCard) {
        this.flopBoard = flopBoard;
        this.turnCard = turnCard;

        cards = new ArrayList();
        cards.addAll(flopBoard.getCards());
        cards.add(turnCard);
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    public FlopBoard getFlopBoard() {
        return flopBoard;
    }

    @Override
    public String toString() {
        return getCardsAsString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnBoard turnBoard = (TurnBoard) o;
        return flopBoard.equals(turnBoard.flopBoard) && turnCard == turnBoard.turnCard && cards.equals(turnBoard.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flopBoard, turnCard, cards);
    }
}
