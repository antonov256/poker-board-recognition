package com.antonov.poker.board_recognition.config;

import com.antonov.poker.board_recognition.config.loading.CardMarkupLoading;
import com.antonov.poker.board_recognition.config.loading.CropConfigLoading;
import com.antonov.poker.board_recognition.config.parsing.CropResourceBundleParser;
import com.antonov.poker.board_recognition.config.parsing.ResourceBundleParser;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;
import com.antonov.poker.board_recognition.util.TemplateCutting;

import java.io.File;

public class TemplateCutterConfiguration {
    private final String templatesPath;
    private final ResourceBundleParser<Crop> cropParser;

    public TemplateCutterConfiguration(String templatesPath) {
        this.templatesPath = templatesPath;
        this.cropParser = new CropResourceBundleParser();
    }

    public TemplateCutting getTemplateCutter() {
        CropConfigLoading cropConfigLoading = new CropConfigLoading("crop_config", cropParser);
        CropConfig cropConfig = cropConfigLoading.load();
        File templatesDir = new File(templatesPath);
        CardMarkup cardMarkup = getCardMarkup();

        TemplateCutting templateCutting = new TemplateCutting(cropConfig, templatesDir, cardMarkup);
        return templateCutting;
    }

    private CardMarkup getCardMarkup() {
        CardMarkupLoading cardMarkupLoading = new CardMarkupLoading("card_markup", cropParser);
        CardMarkup cardMarkup = cardMarkupLoading.load();

        return cardMarkup;
    }
}
