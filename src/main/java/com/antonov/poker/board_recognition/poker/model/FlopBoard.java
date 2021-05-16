package com.antonov.poker.board_recognition.poker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlopBoard implements Board {
    private final Card flopCard1;
    private final Card flopCard2;
    private final Card flopCard3;

    private final List<Card> cards;

    public FlopBoard(Card flopCard1, Card flopCard2, Card flopCard3) {
        this.flopCard1 = flopCard1;
        this.flopCard2 = flopCard2;
        this.flopCard3 = flopCard3;

        cards = new ArrayList();
        cards.add(flopCard1);
        cards.add(flopCard2);
        cards.add(flopCard3);
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return getCardsAsString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlopBoard flopBoard = (FlopBoard) o;
        return flopCard1 == flopBoard.flopCard1 && flopCard2 == flopBoard.flopCard2 && flopCard3 == flopBoard.flopCard3 && cards.equals(flopBoard.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flopCard1, flopCard2, flopCard3, cards);
    }
}
