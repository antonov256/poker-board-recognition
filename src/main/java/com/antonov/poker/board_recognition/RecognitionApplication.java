package com.antonov.poker.board_recognition;

import com.antonov.poker.board_recognition.config.RecognitionApplicationConfiguration;
import com.antonov.poker.board_recognition.config.loading.*;
import com.antonov.poker.board_recognition.config.parsing.ColorResourceBundleParser;
import com.antonov.poker.board_recognition.config.parsing.CropResourceBundleParser;
import com.antonov.poker.board_recognition.config.parsing.ResourceBundleParser;
import com.antonov.poker.board_recognition.image_processing.ImageProcessor;
import com.antonov.poker.board_recognition.poker.model.CardRank;
import com.antonov.poker.board_recognition.poker.model.CardSuit;
import com.antonov.poker.board_recognition.recognition.BoardRecognition;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;
import com.antonov.poker.board_recognition.recognition.model.TemplatesContainer;

import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;

public class RecognitionApplication {
    private final String imagesDirPath;

    public RecognitionApplication(String imagesDirPath) {
        this.imagesDirPath = imagesDirPath;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You need to specify images path as argument");
            return;
        }

        String imagesDirPath = args[0];
        RecognitionApplication recognitionApplication = new RecognitionApplication(imagesDirPath);
        recognitionApplication.launch();
    }

    public void launch() {
        RecognitionTask recognitionTask = new RecognitionTask(
                new File(imagesDirPath),
                configureBoardRecognition()
        );

        recognitionTask.run();
        int correctCount = recognitionTask.getCorrectCount();
        int totalCount = recognitionTask.getTotalCount();

        String summary = "Correct: " + correctCount + " / " + totalCount + " (" + correctCount * 100d / totalCount + "%)";
        System.out.println(summary);
    }

    private BoardRecognition configureBoardRecognition() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        String templatesDirPath = resourceBundle.getString("templates.dir");

        ResourceBundleParser<Crop> cropParser = new CropResourceBundleParser();
        ResourceBundleParser<Color> colorParser = new ColorResourceBundleParser();

        Loading<CropConfig> cropConfigLoading = new CropConfigLoading("crop_config", cropParser);
        Loading<ImageProcessor> preprocessorLoading = new ImageColorReplacingPreprocessorLoading("recognition", colorParser);
        Loading<CardMarkup> cardMarkupLoading = new CardMarkupLoading("card_markup", cropParser);
        Loading<TemplatesContainer<CardRank>> rankTemplatesLoading = new CardRankTemplatesLoading(new File(templatesDirPath, "ranks"));
        Loading<TemplatesContainer<CardSuit>> suitTemplatesLoading = new CardSuitTemplatesLoading(new File(templatesDirPath, "suits"));
        Loading<RecognitionSettings> recognitionSettingsLoading = new RecognitionSettingsLoading("recognition");

        RecognitionApplicationConfiguration recognitionApplicationConfiguration = new RecognitionApplicationConfiguration(
                cropConfigLoading,
                preprocessorLoading,
                cardMarkupLoading,
                rankTemplatesLoading,
                suitTemplatesLoading,
                recognitionSettingsLoading
        );

        return recognitionApplicationConfiguration.getBoardRecognition();
    }
}
