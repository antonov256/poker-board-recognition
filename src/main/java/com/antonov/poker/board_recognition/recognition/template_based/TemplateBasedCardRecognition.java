package com.antonov.poker.board_recognition.recognition.template_based;

import com.antonov.poker.board_recognition.exception.CantRecognizeException;
import com.antonov.poker.board_recognition.image_check.CardIsPresentedCheck;
import com.antonov.poker.board_recognition.image_check.ImageIsEmptyCheck;
import com.antonov.poker.board_recognition.poker.model.Card;
import com.antonov.poker.board_recognition.poker.model.CardRank;
import com.antonov.poker.board_recognition.poker.model.CardSuit;
import com.antonov.poker.board_recognition.recognition.CardRankRecognition;
import com.antonov.poker.board_recognition.recognition.CardRecognition;
import com.antonov.poker.board_recognition.recognition.CardSuitRecognition;
import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Image;

import java.awt.*;

public class TemplateBasedCardRecognition implements CardRecognition {
    private final CardMarkup cardMarkup;
    private final CardRankRecognition cardRankRecognition;
    private final CardSuitRecognition cardSuitRecognition;

    private final CardIsPresentedCheck cardIsPresentedCheck;

    public TemplateBasedCardRecognition(CardMarkup cardMarkup, CardRankRecognition cardRankRecognition, CardSuitRecognition cardSuitRecognition) {
        this.cardMarkup = cardMarkup;
        this.cardRankRecognition = cardRankRecognition;
        this.cardSuitRecognition = cardSuitRecognition;

        cardIsPresentedCheck = new CardIsPresentedCheck(new ImageIsEmptyCheck(Color.white), cardMarkup.getCropForCheck());
    }

    @Override
    public Card recognize(Image cardImage) throws CantRecognizeException {
        if(!cardIsPresentedCheck.check(cardImage)) {
            throw new CantRecognizeException("Card not presented");
        }

        Image rankImage = cardImage.getSubImage(cardMarkup.getRankCrop());
        CardRank rank = cardRankRecognition.recognize(rankImage);

        Image suitImage = cardImage.getSubImage(cardMarkup.getSuitCrop());
        CardSuit suit = cardSuitRecognition.recognize(suitImage);

        Card card = new Card.Creating().fromRankAndSuit(rank, suit);

        return card;
    }
}
