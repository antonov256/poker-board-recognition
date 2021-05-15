package com.antonov.poker.board_recognition.poker.model;

public enum CardSuit {
    hearts("h", 1),        // hearts       ♥ h     red
    diamonds("d", 2),      // diamonds     ♦ d     blue
    spades("s", 3),        // spades       ♠ s     black
    clubs("c", 4);         // clubs        ♣ c     green

    private final String suitName;
    private final int number;

    CardSuit(String suitStr, int number) {
        this.suitName = suitStr;
        this.number = number;
    }

    public String getSuitName() {
        return suitName;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return suitName;
    }

    public static class Utils {
        public CardSuit fromString(String string) {
            switch (string) {
                case "s": return spades;
                case "h": return hearts;
                case "d": return diamonds;
                case "c": return clubs;
                default: throw new IllegalArgumentException("Wrong CardSuit string: " + string);
            }
        }
    }
}