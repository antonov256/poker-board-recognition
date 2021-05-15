package com.antonov.poker.board_recognition.recognition.model;

public class CardMarkup {
    private final Crop rankCrop;
    private final Crop suitCrop;
    private final Crop cropForCheck;

    public CardMarkup(Crop rankCrop, Crop suitCrop, Crop cropForCheck) {
        this.rankCrop = rankCrop;
        this.suitCrop = suitCrop;
        this.cropForCheck = cropForCheck;
    }

    public Crop getRankCrop() {
        return rankCrop;
    }

    public Crop getSuitCrop() {
        return suitCrop;
    }

    public Crop getCropForCheck() {
        return cropForCheck;
    }
}
