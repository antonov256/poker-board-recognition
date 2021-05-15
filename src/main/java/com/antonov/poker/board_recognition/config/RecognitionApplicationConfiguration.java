package com.antonov.poker.board_recognition.config;

import com.antonov.poker.board_recognition.RecognitionSettings;
import com.antonov.poker.board_recognition.config.loading.Loading;
import com.antonov.poker.board_recognition.cv.*;
import com.antonov.poker.board_recognition.image_processing.ImageProcessor;
import com.antonov.poker.board_recognition.poker.model.CardRank;
import com.antonov.poker.board_recognition.poker.model.CardSuit;
import com.antonov.poker.board_recognition.recognition.BoardRecognition;
import com.antonov.poker.board_recognition.recognition.CardRankRecognition;
import com.antonov.poker.board_recognition.recognition.CardRecognition;
import com.antonov.poker.board_recognition.recognition.CardSuitRecognition;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;
import com.antonov.poker.board_recognition.recognition.model.TemplatesContainer;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedBoardRecognition;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardRankRecognition;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardRecognition;
import com.antonov.poker.board_recognition.recognition.template_based.TemplateBasedCardSuitRecognition;

public class RecognitionApplicationConfiguration {
    private final Loading<CropConfig> cropConfigLoading;
    private final Loading<ImageProcessor> preprocessorLoading;
    private final Loading<CardMarkup> cardMarkupLoading;
    private final Loading<TemplatesContainer<CardRank>> rankTemplatesLoading;
    private final Loading<TemplatesContainer<CardSuit>> suitTemplatesLoading;
    private final Loading<RecognitionSettings> recognitionSettingsLoading;

    public RecognitionApplicationConfiguration(Loading<CropConfig> cropConfigLoading, Loading<ImageProcessor> preprocessorLoading, Loading<CardMarkup> cardMarkupLoading, Loading<TemplatesContainer<CardRank>> rankTemplatesLoading, Loading<TemplatesContainer<CardSuit>> suitTemplatesLoading, Loading<RecognitionSettings> recognitionSettingsLoading) {
        this.cropConfigLoading = cropConfigLoading;
        this.preprocessorLoading = preprocessorLoading;
        this.cardMarkupLoading = cardMarkupLoading;
        this.rankTemplatesLoading = rankTemplatesLoading;
        this.suitTemplatesLoading = suitTemplatesLoading;
        this.recognitionSettingsLoading = recognitionSettingsLoading;
    }

    public BoardRecognition getBoardRecognition() {
        BoardRecognition boardRecognition = new TemplateBasedBoardRecognition(
                cropConfigLoading.load(),
                preprocessorLoading.load(),
                getCardRecognition()
        );

        return boardRecognition;
    }

    private CardRecognition getCardRecognition() {
        CardRecognition cardRecognition = new TemplateBasedCardRecognition(
                cardMarkupLoading.load(),
                getCardRankRecognition(),
                getCardSuitRecognition()
        );

        return cardRecognition;
    }

    private CardRankRecognition getCardRankRecognition() {
        CardRankRecognition cardRankRecognition = new TemplateBasedCardRankRecognition(
                rankTemplatesLoading.load(),
                getImageDifferenceCalculation(),
                recognitionSettingsLoading.load()
        );

        return cardRankRecognition;
    }

    private CardSuitRecognition getCardSuitRecognition() {
        CardSuitRecognition cardSuitRecognition = new TemplateBasedCardSuitRecognition(
                suitTemplatesLoading.load(),
                getImageDifferenceCalculation(),
                recognitionSettingsLoading.load()
        );

        return cardSuitRecognition;
    }

    private ImageDifference getImageDifferenceCalculation() {
        NaiveImageDifference naiveImageDifference = new NaiveImageDifference(getColorsDifferenceMetric());
        ImageDifference slidingImageDifference = new SlidingImageDifference(naiveImageDifference);

        return slidingImageDifference;
    }

    private ColorsDifference getColorsDifferenceMetric() {
        return new ArithmeticBrightnessDifference();
    }
}
