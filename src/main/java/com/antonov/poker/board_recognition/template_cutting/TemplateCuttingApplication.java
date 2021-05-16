package com.antonov.poker.board_recognition.template_cutting;

import com.antonov.poker.board_recognition.config.loading.CardMarkupLoading;
import com.antonov.poker.board_recognition.config.loading.CropConfigLoading;
import com.antonov.poker.board_recognition.config.loading.Loading;
import com.antonov.poker.board_recognition.config.parsing.CropResourceBundleParser;
import com.antonov.poker.board_recognition.config.parsing.ResourceBundleParser;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;

import java.io.File;
import java.util.ResourceBundle;

public class TemplateCuttingApplication {
    public static void main(String[] args) {
        TemplateCuttingApplication templateCuttingApplication = new TemplateCuttingApplication();
        templateCuttingApplication.launch();
    }

    public void launch() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("template_cutting");

        File imagesDir = new File(resourceBundle.getString("images.dir"));
        File templatesOutputDir = new File(resourceBundle.getString("templatesOutput.dir"));

        ResourceBundleParser<Crop> cropParser = new CropResourceBundleParser();

        Loading<CropConfig> cropConfigLoading = new CropConfigLoading("crop_config", cropParser);
        Loading<CardMarkup> cardMarkupLoading = new CardMarkupLoading("card_markup", cropParser);

        TemplateCuttingConfiguration templateCuttingConfiguration = new TemplateCuttingConfiguration(
                imagesDir,
                templatesOutputDir,
                cropConfigLoading,
                cardMarkupLoading
        );

        TemplateCuttingTask templateCuttingTask = templateCuttingConfiguration.getTemplateCuttingTask();
        templateCuttingTask.run();
    }
}
