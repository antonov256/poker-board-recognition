package com.antonov.poker.board_recognition.poker.model;

import java.util.Arrays;
import java.util.List;

public enum Card {
    card_2h(0, CardRank.two, CardSuit.hearts),
    card_3h(1, CardRank.three, CardSuit.hearts),
    card_4h(2, CardRank.four, CardSuit.hearts),
    card_5h(3, CardRank.five, CardSuit.hearts),
    card_6h(4, CardRank.six, CardSuit.hearts),
    card_7h(5, CardRank.seven, CardSuit.hearts),
    card_8h(6, CardRank.eight, CardSuit.hearts),
    card_9h(7, CardRank.nine, CardSuit.hearts),
    card_Th(8, CardRank.ten, CardSuit.hearts),
    card_Jh(9, CardRank.jack, CardSuit.hearts),
    card_Qh(10, CardRank.queen, CardSuit.hearts),
    card_Kh(11, CardRank.king, CardSuit.hearts),
    card_Ah(12, CardRank.ace, CardSuit.hearts),
    card_2d(13, CardRank.two, CardSuit.diamonds),
    card_3d(14, CardRank.three, CardSuit.diamonds),
    card_4d(15, CardRank.four, CardSuit.diamonds),
    card_5d(16, CardRank.five, CardSuit.diamonds),
    card_6d(17, CardRank.six, CardSuit.diamonds),
    card_7d(18, CardRank.seven, CardSuit.diamonds),
    card_8d(19, CardRank.eight, CardSuit.diamonds),
    card_9d(20, CardRank.nine, CardSuit.diamonds),
    card_Td(21, CardRank.ten, CardSuit.diamonds),
    card_Jd(22, CardRank.jack, CardSuit.diamonds),
    card_Qd(23, CardRank.queen, CardSuit.diamonds),
    card_Kd(24, CardRank.king, CardSuit.diamonds),
    card_Ad(25, CardRank.ace, CardSuit.diamonds),
    card_2s(26, CardRank.two, CardSuit.spades),
    card_3s(27, CardRank.three, CardSuit.spades),
    card_4s(28, CardRank.four, CardSuit.spades),
    card_5s(29, CardRank.five, CardSuit.spades),
    card_6s(30, CardRank.six, CardSuit.spades),
    card_7s(31, CardRank.seven, CardSuit.spades),
    card_8s(32, CardRank.eight, CardSuit.spades),
    card_9s(33, CardRank.nine, CardSuit.spades),
    card_Ts(34, CardRank.ten, CardSuit.spades),
    card_Js(35, CardRank.jack, CardSuit.spades),
    card_Qs(36, CardRank.queen, CardSuit.spades),
    card_Ks(37, CardRank.king, CardSuit.spades),
    card_As(38, CardRank.ace, CardSuit.spades),
    card_2c(39, CardRank.two, CardSuit.clubs),
    card_3c(40, CardRank.three, CardSuit.clubs),
    card_4c(41, CardRank.four, CardSuit.clubs),
    card_5c(42, CardRank.five, CardSuit.clubs),
    card_6c(43, CardRank.six, CardSuit.clubs),
    card_7c(44, CardRank.seven, CardSuit.clubs),
    card_8c(45, CardRank.eight, CardSuit.clubs),
    card_9c(46, CardRank.nine, CardSuit.clubs),
    card_Tc(47, CardRank.ten, CardSuit.clubs),
    card_Jc(48, CardRank.jack, CardSuit.clubs),
    card_Qc(49, CardRank.queen, CardSuit.clubs),
    card_Kc(50, CardRank.king, CardSuit.clubs),
    card_Ac(51, CardRank.ace, CardSuit.clubs);

    private final int id;
    private final CardRank cardRank;
    private final CardSuit cardSuit;

    Card(int id, CardRank cardRank, CardSuit cardSuit) {
        this.id = id;
        this.cardRank = cardRank;
        this.cardSuit = cardSuit;
    }

    public static class Utils {
        public Card fromRankAndSuit(CardRank rank, CardSuit suit) {
            for(Card card : values()) {
                if(card.getRank() == rank && card.getSuit() == suit)
                    return card;
            }

            throw new IllegalArgumentException("Wrong rank or suit: " + rank + ", " + suit);
        }

        public Card fromString(String s) {
            for(Card card : values()) {
                if(card.toString().equals(s))
                    return card;
            }

            throw new IllegalArgumentException("Wrong string: " + s);
        }

        public List<Card> getCardsByRank(CardRank rank){
            switch (rank) {
                case two:
                    return Arrays.asList(card_2h, card_2d, card_2s, card_2c);
                case three:
                    return Arrays.asList(card_3h, card_3d, card_3s, card_3c);
                case four:
                    return Arrays.asList(card_4h, card_4d, card_4s, card_4c);
                case five:
                    return Arrays.asList(card_5h, card_5d, card_5s, card_5c);
                case six:
                    return Arrays.asList(card_6h, card_6d, card_6s, card_6c);
                case seven:
                    return Arrays.asList(card_7h, card_7d, card_7s, card_7c);
                case eight:
                    return Arrays.asList(card_8h, card_8d, card_8s, card_8c);
                case nine:
                    return Arrays.asList(card_9h, card_9d, card_9s, card_9c);
                case ten:
                    return Arrays.asList(card_Th, card_Td, card_Ts, card_Tc);
                case jack:
                    return Arrays.asList(card_Jh, card_Jd, card_Js, card_Jc);
                case queen:
                    return Arrays.asList(card_Qh, card_Qd, card_Qs, card_Qc);
                case king:
                    return Arrays.asList(card_Kh, card_Kd, card_Ks, card_Kc);
                case ace:
                    return Arrays.asList(card_Ah, card_Ad, card_As, card_Ac);
                default:
                    throw new IllegalArgumentException("Wrong CardRank: " + rank);
            }
        }
    }

    public int getId() {
        return id;
    }

    public CardRank getRank() {
        return cardRank;
    }

    public CardSuit getSuit() {
        return cardSuit;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(cardRank.toString()).append(cardSuit.toString());
        return sb.toString();
    }
}
