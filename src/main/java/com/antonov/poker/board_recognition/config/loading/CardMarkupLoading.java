package com.antonov.poker.board_recognition.config.loading;

import com.antonov.poker.board_recognition.config.parsing.ResourceBundleParser;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Crop;

import java.util.ResourceBundle;

public class CardMarkupLoading implements Loading<CardMarkup> {
    private final String bundleName;
    private final ResourceBundleParser<Crop> cropParser;

    public CardMarkupLoading(String bundleName, ResourceBundleParser<Crop> cropParser) {
        this.bundleName = bundleName;
        this.cropParser = cropParser;
    }

    @Override
    public CardMarkup load() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);

        Crop rankCrop = cropParser.parse(resourceBundle, "rankCrop");
        Crop suitCrop = cropParser.parse(resourceBundle, "suitCrop");
        Crop cropForCheck = cropParser.parse(resourceBundle, "cropForCheck");

        CardMarkup cardMarkup = new CardMarkup(
                rankCrop,
                suitCrop,
                cropForCheck
        );

        return cardMarkup;
    }
}
