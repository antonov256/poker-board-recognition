package com.antonov.poker.board_recognition.config;

import com.antonov.poker.board_recognition.config.loading.CropConfigLoading;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;
import com.antonov.poker.board_recognition.util.TemplateCutting;

import java.io.File;

public class TemplateCutterConfiguration {
    private final String templatesPath;

    public TemplateCutterConfiguration(String templatesPath) {
        this.templatesPath = templatesPath;
    }

    public TemplateCutting getTemplateCutter() {
        CropConfig cropConfig = new CropConfigLoading().load();
        File templatesDir = new File(templatesPath);
        CardMarkup cardMarkup = getCardMarkup();

        TemplateCutting templateCutting = new TemplateCutting(cropConfig, templatesDir, cardMarkup);
        return templateCutting;
    }

    private CardMarkup getCardMarkup() {
        Crop rankCrop = new Crop(0, 0, 34, 27);
        Crop suitCrop = new Crop(0, 27, 25, 19);
        Crop cropForCheck = new Crop(39, 0, 15, 40);

        CardMarkup cardMarkup = new CardMarkup(rankCrop, suitCrop, cropForCheck);
        return cardMarkup;
    }
}
