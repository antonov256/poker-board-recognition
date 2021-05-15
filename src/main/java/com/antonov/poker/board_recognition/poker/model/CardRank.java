package com.antonov.poker.board_recognition.poker.model;

public enum CardRank implements Comparable<CardRank> {
    two("2", 2),
    three("3", 3),
    four("4", 4),
    five("5", 5),
    six("6", 6),
    seven("7", 7),
    eight("8", 8),
    nine("9", 9),
    ten("T", 10),
    jack("J", 11),
    queen("Q", 12),
    king("K", 13),
    ace("A", 14);

    private final String rankName;
    private final int value;

    CardRank(String rankName, int value) {
        this.rankName = rankName;
        this.value = value;
    }

    public String getRankName() {
        return rankName;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        if(this == ten)
            return "10";

        return rankName;
    }

    public static class Creating {
        public CardRank fromValue(int value) {
            switch (value) {
                case 14 : return ace;
                case 13 : return king;
                case 12 : return queen;
                case 11 : return jack;
                case 10 : return ten;
                case 9 : return nine;
                case 8 : return eight;
                case 7 : return seven;
                case 6 : return six;
                case 5 : return five;
                case 4 : return four;
                case 3 : return three;
                case 2 : return two;
                default: throw new IllegalArgumentException("Wrong CardRank value: " + value);
            }
        }

        public CardRank fromString(String string) {
            switch (string) {
                case "A" : return ace;
                case "K" : return king;
                case "Q" : return queen;
                case "J" : return jack;
                case "T" : return ten;
                case "10": return ten;
                case "9" : return nine;
                case "8" : return eight;
                case "7" : return seven;
                case "6" : return six;
                case "5" : return five;
                case "4" : return four;
                case "3" : return three;
                case "2" : return two;
                default: throw new IllegalArgumentException("Wrong CardRank string: " + string);
            }
        }
    }
}