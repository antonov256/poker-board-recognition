package com.antonov.poker.board_recognition.poker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Board {
    List<Card> getCards();

    default String getCardsAsString() {
        return getCards().stream().map(Card::toString).collect(Collectors.joining());
    }

    class Creating {
        public Board fromCards(List<Card> cards) {
            if (cards.size() == 3) {
                return new FlopBoard(cards.get(0), cards.get(1), cards.get(2));
            }

            if (cards.size() == 4) {
                FlopBoard flopBoard = new FlopBoard(cards.get(0), cards.get(1), cards.get(2));
                return new TurnBoard(flopBoard, cards.get(3));
            }

            if (cards.size() == 5) {
                FlopBoard flopBoard = new FlopBoard(cards.get(0), cards.get(1), cards.get(2));
                TurnBoard turnBoard = new TurnBoard(flopBoard, cards.get(3));
                return new RiverBoard(turnBoard, cards.get(4));
            }

            throw new IllegalArgumentException("Wrong cards count: " + cards.size());
        }

        public Board fromString(String cardsStr) {
            if (cardsStr == null) {
                throw new IllegalArgumentException("Wrong string on '" + cardsStr + "'");
            }

            List<Card> cards = new ArrayList<>();

            while (cardsStr.length() != 0) {
                String firstCardStr;
                if (cardsStr.startsWith("10")) {
                    firstCardStr = cardsStr.substring(0, 3);
                } else {
                    firstCardStr = cardsStr.substring(0, 2);
                }

                Card card = new Card.Creating().fromString(firstCardStr);
                cards.add(card);

                cardsStr = cardsStr.substring(firstCardStr.length());
            }

            if (cards.size() < 3 || cards.size() > 5) {
                throw new IllegalArgumentException("Wrong string on '" + cardsStr + "'");
            }

            Board board = fromCards(cards);
            return board;
        }
    }
}
