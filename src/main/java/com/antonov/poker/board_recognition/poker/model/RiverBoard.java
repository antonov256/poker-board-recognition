package com.antonov.poker.board_recognition.poker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RiverBoard implements Board {
    private final TurnBoard turnBoard;
    private final Card riverCard;

    private final List<Card> cards;

    public RiverBoard(TurnBoard turnBoard, Card riverCard) {
        this.turnBoard = turnBoard;
        this.riverCard = riverCard;

        cards = new ArrayList();
        cards.addAll(turnBoard.getCards());
        cards.add(riverCard);
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    public FlopBoard getFlopBoard() {
        return getTurnBoard().getFlopBoard();
    }

    public TurnBoard getTurnBoard() {
        return turnBoard;
    }

    @Override
    public String toString() {
        return getCardsAsString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiverBoard that = (RiverBoard) o;
        return turnBoard.equals(that.turnBoard) && riverCard == that.riverCard && cards.equals(that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turnBoard, riverCard, cards);
    }
}
