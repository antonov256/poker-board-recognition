package com.antonov.poker.board_recognition;

public class RecognitionSettings {
    private final Double maxAcceptableDifference;

    public RecognitionSettings(Double maxAcceptableDifference) {
        this.maxAcceptableDifference = maxAcceptableDifference;
    }

    public Double getMaxAcceptableDifference() {
        return maxAcceptableDifference;
    }
}
