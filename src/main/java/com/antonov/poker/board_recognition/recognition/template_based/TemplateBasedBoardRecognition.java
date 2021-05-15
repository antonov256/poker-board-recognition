package com.antonov.poker.board_recognition.recognition.template_based;

import com.antonov.poker.board_recognition.recognition.BoardRecognition;
import com.antonov.poker.board_recognition.recognition.CardRecognition;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;
import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.image_processing.ImageProcessor;
import com.antonov.poker.board_recognition.poker.model.Board;
import com.antonov.poker.board_recognition.poker.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TemplateBasedBoardRecognition implements BoardRecognition {
    private final CropConfig cropConfig;
    private final ImageProcessor cardPreprocessor;
    private final CardRecognition cardRecognition;

    public TemplateBasedBoardRecognition(CropConfig cropConfig, ImageProcessor cardPreprocessor, CardRecognition cardRecognition) {
        this.cropConfig = cropConfig;
        this.cardPreprocessor = cardPreprocessor;
        this.cardRecognition = cardRecognition;
    }

    @Override
    public Board recognize(Image image) {
        List<Card> cards = recognizeCardsOnImage(image);
        Board board = new Board.Creating().fromCards(cards);

        return board;
    }

    private List<Card> recognizeCardsOnImage(Image image) {
        List<Card> cards = new ArrayList<>();

        Optional<Card> optionalCard1 = recognizeCard(image.getSubImage(cropConfig.getCard1()));
        optionalCard1.ifPresent(cards::add);

        Optional<Card> optionalCard2 = recognizeCard(image.getSubImage(cropConfig.getCard2()));
        optionalCard2.ifPresent(cards::add);

        Optional<Card> optionalCard3 = recognizeCard(image.getSubImage(cropConfig.getCard3()));
        optionalCard3.ifPresent(cards::add);

        Optional<Card> optionalCard4 = recognizeCard(image.getSubImage(cropConfig.getCard4()));
        optionalCard4.ifPresent(cards::add);

        Optional<Card> optionalCard5 = recognizeCard(image.getSubImage(cropConfig.getCard5()));
        optionalCard5.ifPresent(cards::add);

        return cards;
    }

    private Optional<Card> recognizeCard(Image cardImage) {
        cardImage = cardPreprocessor.apply(cardImage);
        Optional<Card> cardOptional = Optional.empty();

        try {
            Card card = cardRecognition.recognize(cardImage);
            cardOptional = Optional.of(card);
        } catch (Exception e) {
            //todo need logging and saving unrecognized image for further analysis
            // e.printStackTrace();
            // for now for convenience we return Optional.empty() in case if we cant find card on image
        }

        return cardOptional;
    }
}
