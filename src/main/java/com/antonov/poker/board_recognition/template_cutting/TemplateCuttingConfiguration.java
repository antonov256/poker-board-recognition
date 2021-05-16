package com.antonov.poker.board_recognition.template_cutting;

import com.antonov.poker.board_recognition.config.loading.Loading;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;

import java.io.File;

public class TemplateCuttingConfiguration {
    private final File imagesDir;
    private final File templatesOutputDir;
    private final Loading<CropConfig> cropConfigLoading;
    private final Loading<CardMarkup> cardMarkupLoading;

    public TemplateCuttingConfiguration(File imagesDir, File templatesOutputDir, Loading<CropConfig> cropConfigLoading, Loading<CardMarkup> cardMarkupLoading) {
        this.imagesDir = imagesDir;
        this.templatesOutputDir = templatesOutputDir;
        this.cropConfigLoading = cropConfigLoading;
        this.cardMarkupLoading = cardMarkupLoading;
    }

    public TemplateCuttingTask getTemplateCuttingTask() {
        TemplateCuttingTask templateCuttingTask = new TemplateCuttingTask(
                imagesDir, templatesOutputDir, cropConfigLoading.load(),
                cardMarkupLoading.load()
        );

        return templateCuttingTask;
    }
}
