package com.antonov.poker.board_recognition.config.loading;

import com.antonov.poker.board_recognition.RecognitionSettings;

import java.util.ResourceBundle;

public class RecognitionSettingsLoading implements Loading<RecognitionSettings> {
    private final String bundleName;

    public RecognitionSettingsLoading(String bundleName) {
        this.bundleName = bundleName;
    }

    @Override
    public RecognitionSettings load() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);
        Double maxCardRankAcceptableDifference = Double.valueOf(resourceBundle.getString("recognition.cardRank.maxAcceptableDifference"));
        Double maxCardSuitAcceptableDifference = Double.valueOf(resourceBundle.getString("recognition.cardSuit.maxAcceptableDifference"));

        RecognitionSettings recognitionSettings = new RecognitionSettings(
                maxCardRankAcceptableDifference,
                maxCardSuitAcceptableDifference);
        return recognitionSettings;
    }
}
