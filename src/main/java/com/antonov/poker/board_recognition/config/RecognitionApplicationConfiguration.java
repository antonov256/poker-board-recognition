package com.antonov.poker.board_recognition.config;

import com.antonov.poker.board_recognition.RecognitionSettings;
import com.antonov.poker.board_recognition.config.loading.*;
import com.antonov.poker.board_recognition.config.parsing.ColorResourceBundleParser;
import com.antonov.poker.board_recognition.config.parsing.CropResourceBundleParser;
import com.antonov.poker.board_recognition.config.parsing.ResourceBundleParser;
import com.antonov.poker.board_recognition.cv.*;
import com.antonov.poker.board_recognition.image_processing.ImageColorReplacingPreprocessor;
import com.antonov.poker.board_recognition.image_processing.ImageProcessor;
import com.antonov.poker.board_recognition.poker.model.CardRank;
import com.antonov.poker.board_recognition.poker.model.CardSuit;
import com.antonov.poker.board_recognition.recognition.BoardRecognition;
import com.antonov.poker.board_recognition.recognition.CardRankRecognition;
import com.antonov.poker.board_recognition.recognition.CardRecognition;
import com.antonov.poker.board_recognition.recognition.CardSuitRecognition;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;
import com.antonov.poker.board_recognition.recognition.model.TemplatesContainer;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedBoardRecognition;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardRankRecognition;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardRecognition;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardSuitRecognition;

import java.awt.*;
import java.io.File;

public class RecognitionApplicationConfiguration {
    private final File templatesDir;

    private final ResourceBundleParser<Crop> cropParser;
    private final ResourceBundleParser<Color> colorParser;

    public RecognitionApplicationConfiguration(String templatesDir) {
        this.templatesDir = new File(templatesDir);
        this.cropParser = new CropResourceBundleParser();
        this.colorParser = new ColorResourceBundleParser();
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
        CropConfigLoading cropConfigLoading = new CropConfigLoading("crop_config", cropParser);
        CropConfig cropConfig = cropConfigLoading.load();

        return cropConfig;
    }

    public ImageProcessor getCardPreprocessor() {
        ImageColorReplacingPreprocessorLoading imagePreprocessorLoading = new ImageColorReplacingPreprocessorLoading("recognition", colorParser);
        ImageColorReplacingPreprocessor imageColorReplacingPreprocessor = imagePreprocessorLoading.load();

        return imageColorReplacingPreprocessor;
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
        CardMarkupLoading cardMarkupLoading = new CardMarkupLoading("card_markup", cropParser);
        CardMarkup cardMarkup = cardMarkupLoading.load();

        return cardMarkup;
    }

    private CardRankRecognition getCardRankRecognition() {
        CardRankTemplatesLoading cardRankTemplatesLoading = new CardRankTemplatesLoading(new File(templatesDir, "ranks"));
        TemplatesContainer<CardRank> cardRankTemplates = cardRankTemplatesLoading.load();

        CardRankRecognition cardRankRecognition = new TemplateBasedCardRankRecognition(
                cardRankTemplates,
                getImageDifferenceCalculation(),
                getRecognitionSettings()
        );

        return cardRankRecognition;
    }

    private CardSuitRecognition getCardSuitRecognition() {
        CardSuitTemplatesLoading cardSuitTemplatesLoading = new CardSuitTemplatesLoading(new File(templatesDir, "suits"));
        TemplatesContainer<CardSuit> cardSuitTemplates = cardSuitTemplatesLoading.load();

        CardSuitRecognition cardSuitRecognition = new TemplateBasedCardSuitRecognition(
                cardSuitTemplates,
                getImageDifferenceCalculation(),
                getRecognitionSettings()
        );

        return cardSuitRecognition;
    }

    private RecognitionSettings getRecognitionSettings() {
        RecognitionSettingsLoading recognitionSettingsLoading = new RecognitionSettingsLoading("recognition");
        RecognitionSettings recognitionSettings = recognitionSettingsLoading.load();

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
