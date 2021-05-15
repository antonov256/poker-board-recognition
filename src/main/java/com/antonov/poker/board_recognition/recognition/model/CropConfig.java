package com.antonov.poker.board_recognition.recognition.model;

public class CropConfig {
    private final Crop card1;
    private final Crop card2;
    private final Crop card3;
    private final Crop card4;
    private final Crop card5;

    public CropConfig(Crop card1, Crop card2, Crop card3, Crop card4, Crop card5) {
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.card4 = card4;
        this.card5 = card5;
    }

    public Crop getCard1() {
        return card1;
    }

    public Crop getCard2() {
        return card2;
    }

    public Crop getCard3() {
        return card3;
    }

    public Crop getCard4() {
        return card4;
    }

    public Crop getCard5() {
        return card5;
    }
}
