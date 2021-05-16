package com.antonov.poker.board_recognition.template_cutting;

import com.antonov.poker.board_recognition.image_check.ImageCheck;
import com.antonov.poker.board_recognition.image_processing.ImageProcessor;
import com.antonov.poker.board_recognition.recognition.model.*;
import com.antonov.poker.board_recognition.image_check.CardIsPresentedCheck;
import com.antonov.poker.board_recognition.image_check.ImageIsEmptyCheck;
import com.antonov.poker.board_recognition.image_processing.EmptySpacesCropping;
import com.antonov.poker.board_recognition.recognition.model.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TemplateCuttingTask {
    private final CropConfig cropConfig;
    private final File imagesDir;
    private final File templatesOutputDir;
    private final CardMarkup cardMarkup;

    private final ImageCheck cardIsPresentedCheck;
    private final ImageProcessor emptySpacesCropping;

    public TemplateCuttingTask(File imagesDir, File templatesOutputDir, CropConfig cropConfig, CardMarkup cardMarkup) {
        this.imagesDir = imagesDir;

        String cuttingExecutionSubdirectoryName = String.valueOf(System.currentTimeMillis());
        this.templatesOutputDir = new File(templatesOutputDir, cuttingExecutionSubdirectoryName).getAbsoluteFile();

        if(!this.templatesOutputDir.exists()) {
            this.templatesOutputDir.mkdir();
        }

        this.cropConfig = cropConfig;
        this.cardMarkup = cardMarkup;

        cardIsPresentedCheck = new CardIsPresentedCheck(new ImageIsEmptyCheck(Color.white), cardMarkup.getCropForCheck());
        emptySpacesCropping = new EmptySpacesCropping(Color.white);
    }

    public void run() {
        if (!imagesDir.exists()) {
            System.err.println("imagesDir not exists: " + imagesDir);
            return;
        }

        if (!imagesDir.isDirectory()) {
            System.err.println("imagesDir is not directory: " + imagesDir);
            return;
        }

        File[] files = imagesDir.listFiles();
        if (files == null) {
            System.err.println("files == null. imagesDir: " + imagesDir);
            return;
        }

        for (File f : files) {
            if (f == null || !f.exists() || !f.getName().endsWith(".png"))
                continue;

            try {
                BufferedImage bufferedImage = ImageIO.read(f);
                Image image = new SimpleImage(bufferedImage);

                cutTemplatesFromImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cutTemplatesFromImage(Image image) {
        processCardImage(image.getSubImage(cropConfig.getCard1()), 1);
        processCardImage(image.getSubImage(cropConfig.getCard2()), 2);
        processCardImage(image.getSubImage(cropConfig.getCard3()), 3);
        processCardImage(image.getSubImage(cropConfig.getCard4()), 4);
        processCardImage(image.getSubImage(cropConfig.getCard5()), 5);
    }

    private void processCardImage(Image cardImage, int cardNum){
        if(!cardIsPresentedCheck.check(cardImage)) {
            saveImage(cardImage, "empty_card");
            return;
        }

        Image cardImageCropped = emptySpacesCropping.apply(cardImage);
        saveImage(cardImageCropped, "cropped_card_" + cardNum);

        processCardRank(cardImageCropped, cardNum);
        processCardSuit(cardImageCropped, cardNum);
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
        File file = new File(templatesOutputDir, prefix + " " + System.currentTimeMillis() + ".png");

        try {
            ImageIO.write(image.getBufferedImage(), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
