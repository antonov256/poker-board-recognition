package com.antonov.poker.board_recognition.config;

import com.antonov.poker.board_recognition.RecognitionSettings;
import com.antonov.poker.board_recognition.config.loading.CardRankTemplatesLoading;
import com.antonov.poker.board_recognition.config.loading.CardSuitTemplatesLoading;
import com.antonov.poker.board_recognition.config.loading.CropConfigLoading;
import com.antonov.poker.board_recognition.cv.*;
import com.antonov.poker.board_recognition.recognition.*;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedBoardRecognition;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardRankRecognition;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardRecognition;
import com.antonov.poker.board_recognition.image_processing.ImageColorReplacingPreprocessor;
import com.antonov.poker.board_recognition.image_processing.ImageProcessor;
import com.antonov.poker.board_recognition.poker.model.CardSuit;
import com.antonov.poker.board_recognition.poker.model.CardRank;
import com.antonov.poker.board_recognition.recognition.model.TemplatesContainer;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardSuitRecognition;

import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;

public class RecognitionApplicationConfiguration {
    private final File templatesDir;

    public RecognitionApplicationConfiguration(String templatesDir) {
        this.templatesDir = new File(templatesDir);
    }

    public BoardRecognition getBoardRecognition() {
        BoardRecognition boardRecognition = new TemplateBasedBoardRecognition(
                getCropConfig(),
                getCardPreprocessor(),
                getCardRecognition()
        );

        return boardRecognition;
    }

    private CropConfig getCropConfig() {
        CropConfigLoading cropConfigLoading = new CropConfigLoading();
        CropConfig cropConfig = cropConfigLoading.load();

        return cropConfig;
    }

    public ImageProcessor getCardPreprocessor() {
        Color oldGrayColor = new Color(120, 120, 120);
        Color newWhiteColor = Color.white;

        return new ImageColorReplacingPreprocessor(oldGrayColor, newWhiteColor);
    }

    private CardRecognition getCardRecognition() {
        CardRecognition cardRecognition = new TemplateBasedCardRecognition(
                getCardMarkup(),
                getCardRankRecognition(),
                getCardSuitRecognition()
        );

        return cardRecognition;
    }

    private CardMarkup getCardMarkup() {
        Crop rankCrop = new Crop(0, 0, 34, 27);
        Crop suitCrop = new Crop(0, 27, 25, 19);
        Crop cropForCheck = new Crop(39, 0, 15, 40);

        CardMarkup cardMarkup = new CardMarkup(rankCrop, suitCrop, cropForCheck);
        return cardMarkup;
    }

    private CardRankRecognition getCardRankRecognition() {
        CardRankTemplatesLoading cardRankTemplatesLoading = new CardRankTemplatesLoading();
        TemplatesContainer<CardRank> cardRankTemplates = cardRankTemplatesLoading.load(new File(templatesDir, "ranks"));

        CardRankRecognition cardRankRecognition = new TemplateBasedCardRankRecognition(
                cardRankTemplates,
                getImageDifferenceCalculation(),
                getRecognitionSettings()
        );

        return cardRankRecognition;
    }

    private CardSuitRecognition getCardSuitRecognition() {
        CardSuitTemplatesLoading cardSuitTemplatesLoading = new CardSuitTemplatesLoading();
        TemplatesContainer<CardSuit> cardSuitTemplates = cardSuitTemplatesLoading.load(new File(templatesDir, "suits"));

        CardSuitRecognition cardSuitRecognition = new TemplateBasedCardSuitRecognition(
                cardSuitTemplates,
                getImageDifferenceCalculation(),
                getRecognitionSettings()
        );

        return cardSuitRecognition;
    }

    private RecognitionSettings getRecognitionSettings() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("settings");
        Double maxAcceptableDifference = Double.valueOf(resourceBundle.getString("recognition.maxAcceptableDifference"));

        RecognitionSettings recognitionSettings = new RecognitionSettings(maxAcceptableDifference);
        return recognitionSettings;
    }

    private ColorsDifference getColorsDifferenceMetric() {
        return new ArithmeticBrightnessDifference();
    }

    private ImageDifference getImageDifferenceCalculation() {
        NaiveImageDifference naiveImageDifference = new NaiveImageDifference(getColorsDifferenceMetric());
        ImageDifference slidingImageDifference = new SlidingImageDifference(naiveImageDifference);

        return slidingImageDifference;
    }
}
