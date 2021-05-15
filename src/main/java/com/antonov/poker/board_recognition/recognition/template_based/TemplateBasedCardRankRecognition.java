package com.antonov.poker.board_recognition.recognition.template_based;

import com.antonov.poker.board_recognition.RecognitionSettings;
import com.antonov.poker.board_recognition.exception.CantRecognizeException;
import com.antonov.poker.board_recognition.poker.model.CardRank;
import com.antonov.poker.board_recognition.recognition.CardRankRecognition;
import com.antonov.poker.board_recognition.cv.ImageDifference;
import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.recognition.model.Template;
import com.antonov.poker.board_recognition.recognition.model.TemplatesContainer;

import java.util.Optional;

public class TemplateBasedCardRankRecognition implements CardRankRecognition {
    private final TemplatesContainer<CardRank> cardRankTemplates;
    private final ImageDifference imageDifference;
    private final RecognitionSettings recognitionSettings;

    public TemplateBasedCardRankRecognition(TemplatesContainer<CardRank> cardRankTemplates, ImageDifference imageDifference, RecognitionSettings recognitionSettings) {
        this.cardRankTemplates = cardRankTemplates;
        this.imageDifference = imageDifference;
        this.recognitionSettings = recognitionSettings;
    }

    @Override
    public CardRank recognize(Image rankImage) throws CantRecognizeException {

        Double minDifference = Double.MAX_VALUE;
        Optional<CardRank> cardRankOptional = Optional.empty();

        for(Template<CardRank> rankTemplate : cardRankTemplates.getTemplates()) {
            Double difference = imageDifference.difference(rankTemplate.getImage(), rankImage);

            if(difference < minDifference) {
                minDifference = difference;
                cardRankOptional = Optional.of(rankTemplate.getObject());
            }
        }

        if(cardRankOptional.isEmpty())
            throw new CantRecognizeException("Cant recognize CardRank");

        if(minDifference > recognitionSettings.getMaxAcceptableDifference())
            throw new CantRecognizeException("minDifference is too big: " + minDifference);

//        System.out.println("cardRank minDifference: " + minDifference);
        return cardRankOptional.get();
    }
}
