package com.antonov.poker.board_recognition.recognition.template_based;

import com.antonov.poker.board_recognition.RecognitionSettings;
import com.antonov.poker.board_recognition.exception.CantRecognizeException;
import com.antonov.poker.board_recognition.poker.model.CardSuit;
import com.antonov.poker.board_recognition.recognition.CardSuitRecognition;
import com.antonov.poker.board_recognition.cv.ImageDifference;
import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.recognition.model.Template;
import com.antonov.poker.board_recognition.recognition.model.TemplatesContainer;

import java.util.Optional;

public class TemplateBasedCardSuitRecognition implements CardSuitRecognition {
    private final TemplatesContainer<CardSuit> cardSuitTemplates;
    private final ImageDifference imageDifference;
    private final RecognitionSettings recognitionSettings;

    public TemplateBasedCardSuitRecognition(TemplatesContainer<CardSuit> cardSuitTemplates, ImageDifference imageDifference, RecognitionSettings recognitionSettings) {
        this.cardSuitTemplates = cardSuitTemplates;
        this.imageDifference = imageDifference;
        this.recognitionSettings = recognitionSettings;
    }

    @Override
    public CardSuit recognize(Image suitImage) throws CantRecognizeException {
        Double minDifference = Double.MAX_VALUE;
        Optional<CardSuit> cardSuitOptional = Optional.empty();

        for(Template<CardSuit> suitTemplate : cardSuitTemplates.getTemplates()) {
            Double difference = imageDifference.difference(suitTemplate.getImage(), suitImage);

            if(difference < minDifference) {
                minDifference = difference;
                cardSuitOptional = Optional.of(suitTemplate.getObject());
            }
        }

        if(cardSuitOptional.isEmpty())
            throw new CantRecognizeException("Cant recognize CardSuit");

        if(minDifference > recognitionSettings.getMaxCardSuitAcceptableDifference())
            throw new CantRecognizeException("minDifference is too big: " + minDifference);

        return cardSuitOptional.get();
    }
}
