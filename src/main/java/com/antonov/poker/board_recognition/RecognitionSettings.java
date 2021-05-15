package com.antonov.poker.board_recognition;

public class RecognitionSettings {
    private final Double maxCardRankAcceptableDifference;
    private final Double maxCardSuitAcceptableDifference;

    public RecognitionSettings(Double maxCardRankAcceptableDifference, Double maxCardSuitAcceptableDifference) {
        this.maxCardRankAcceptableDifference = maxCardRankAcceptableDifference;
        this.maxCardSuitAcceptableDifference = maxCardSuitAcceptableDifference;
    }

    public Double getMaxCardRankAcceptableDifference() {
        return maxCardRankAcceptableDifference;
    }

    public Double getMaxCardSuitAcceptableDifference() {
        return maxCardSuitAcceptableDifference;
    }
}
