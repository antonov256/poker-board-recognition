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
    private final String[] args;

    public RecognitionApplication(String[] args) {
        this.args = args;
    }

    public static void main(String[] args) {
        RecognitionApplication recognitionApplication = new RecognitionApplication(args);
        recognitionApplication.launch();
    }

    public void launch() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");

        File imagesDir;
        if (args.length != 0) {
            imagesDir = new File(args[0]);
        } else {
            imagesDir = new File(resourceBundle.getString("images.dir"));
        }

        File templatesDir = new File(resourceBundle.getString("templates.dir"));
        BoardRecognition boardRecognition = configureBoardRecognition(templatesDir);

        RecognitionTask recognitionTask = new RecognitionTask(
                imagesDir,
                boardRecognition
        );

        recognitionTask.run();

        int correctCount = recognitionTask.getCorrectCount();
        int totalCount = recognitionTask.getTotalCount();

        String summary = "Correct: " + correctCount + " / " + totalCount + " (" + correctCount * 100d / totalCount + "%)";
        System.out.println(summary);
    }

    private BoardRecognition configureBoardRecognition(File templatesDir) {
        ResourceBundleParser<Crop> cropParser = new CropResourceBundleParser();
        ResourceBundleParser<Color> colorParser = new ColorResourceBundleParser();

        Loading<CropConfig> cropConfigLoading = new CropConfigLoading("crop_config", cropParser);
        Loading<ImageProcessor> preprocessorLoading = new ImageColorReplacingPreprocessorLoading("recognition", colorParser);
        Loading<CardMarkup> cardMarkupLoading = new CardMarkupLoading("card_markup", cropParser);
        Loading<TemplatesContainer<CardRank>> rankTemplatesLoading = new CardRankTemplatesLoading(new File(templatesDir, "ranks"));
        Loading<TemplatesContainer<CardSuit>> suitTemplatesLoading = new CardSuitTemplatesLoading(new File(templatesDir, "suits"));
        Loading<RecognitionSettings> recognitionSettingsLoading = new RecognitionSettingsLoading("recognition");

        RecognitionApplicationConfiguration recognitionApplicationConfiguration = new RecognitionApplicationConfiguration(
                cropConfigLoading,
                preprocessorLoading,
                cardMarkupLoading,
                rankTemplatesLoading,
                suitTemplatesLoading,
                recognitionSettingsLoading
        );

        BoardRecognition boardRecognition = recognitionApplicationConfiguration.getBoardRecognition();

        return boardRecognition;
    }
}
