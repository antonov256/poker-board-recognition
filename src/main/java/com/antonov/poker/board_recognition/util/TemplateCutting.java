package com.antonov.poker.board_recognition.util;

import com.antonov.poker.board_recognition.recognition.model.CardMarkup;
import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;
import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.image_check.CardIsPresentedCheck;
import com.antonov.poker.board_recognition.image_check.ImageIsEmptyCheck;
import com.antonov.poker.board_recognition.image_processing.EmptySpacesCropping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TemplateCutting {
    private final CropConfig cropConfig;
    private final File dir;
    private final CardMarkup cardMarkup;
    private final long timestamp;

    private final CardIsPresentedCheck cardIsPresentedCheck;
    private final EmptySpacesCropping emptySpacesCropping;

    public TemplateCutting(CropConfig cropConfig, File dir, CardMarkup cardMarkup) {
        this.cropConfig = cropConfig;
        this.cardMarkup = cardMarkup;

        this.timestamp = System.currentTimeMillis();
        this.dir = new File(dir, String.valueOf(timestamp));

        if(!this.dir.exists()) {
            this.dir.mkdir();
        }

        cardIsPresentedCheck = new CardIsPresentedCheck(new ImageIsEmptyCheck(Color.white), cardMarkup.getCropForCheck());
        emptySpacesCropping = new EmptySpacesCropping(Color.white);
    }

    public void cutTemplatesFromImage(Image image) {
        processCardImage(image, cropConfig.getCard1(), 1);
        processCardImage(image, cropConfig.getCard2(), 2);
        processCardImage(image, cropConfig.getCard3(), 3);
        processCardImage(image, cropConfig.getCard4(), 4);
        processCardImage(image, cropConfig.getCard5(), 5);
    }

    private void processCardImage(Image image, Crop cardCrop, int cardNum){
        Image cardImage = cutCard(image, cardCrop);

        if(!cardIsPresentedCheck.check(cardImage)) {
            saveImage(cardImage, "empty");
            return;
        }

        Image cardImageClear = emptySpacesCropping.apply(cardImage);
        saveImage(cardImageClear, "card_" + cardNum);

        processCardRank(cardImageClear, cardNum);
        processCardSuit(cardImageClear, cardNum);
    }

    private Image cutCard(Image image, Crop cardCrop) {
        Image cardImage = image.getSubImage(cardCrop);
        return cardImage;
    }

    private void processCardRank(Image image, int cardNum) {
        Image rankImage = image.getSubImage(cardMarkup.getRankCrop());
        Image clearRankImage = emptySpacesCropping.apply(rankImage);

        saveImage(clearRankImage, "cardRank_" + cardNum);
    }

    private void processCardSuit(Image image, int cardNum) {
        Image suitImage = image.getSubImage(cardMarkup.getSuitCrop());
        Image clearSuitImage = emptySpacesCropping.apply(suitImage);

        saveImage(clearSuitImage, "cardSuit_" + cardNum);
    }

    private void saveImage(Image image, String prefix) {
        File file = new File(dir, prefix + " " + System.currentTimeMillis() + ".png");

        try {
            ImageIO.write(image.getBufferedImage(), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
