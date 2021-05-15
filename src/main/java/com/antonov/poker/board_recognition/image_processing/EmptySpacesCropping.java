package com.antonov.poker.board_recognition.image_processing;

import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.Image;

import java.awt.*;

public class EmptySpacesCropping implements ImageProcessor {
    private final Color emptyColor;

    public EmptySpacesCropping(Color emptyColor) {
        this.emptyColor = emptyColor;
    }

    @Override
    public Image apply(Image image) {
        int width = image.getDimension().getWidth();
        int height = image.getDimension().getHeight();

        int startX = 0;
        for(int x = 0; x < width; x++) {
            boolean isEmpty = checkColumnIsEmpty(image, x);
            if(isEmpty)
                startX = x;
            else
                break;
        }

        int startY = 0;
        for(int y = 0; y < width; y++) {
            boolean isEmpty = checkLineIsEmpty(image, y);
            if(isEmpty)
                startY = y;
            else
                break;
        }

        int endX = width - 1;
        for(int x = width - 1; x >= 0; x--) {
            boolean isEmpty = checkColumnIsEmpty(image, x);
            if(isEmpty)
                endX = x;
            else
                break;
        }

        int endY = height - 1;
        for(int y = height - 1; y >= 0; y--) {
            boolean isEmpty = checkLineIsEmpty(image, y);
            if(isEmpty)
                endY = y;
            else
                break;
        }

        if(endX < startX || endY < startY) {
            startX = 0;
            startY = 0;
            endX = width - 1;
            endY = height - 1;
        }

        Crop crop = new Crop(startX, startY, endX - startX, endY - startY);
        Image subImage = image.getSubImage(crop);

        return subImage;
    }

    private boolean checkLineIsEmpty(Image image, int y) {
        for(int x = 0; x < image.getDimension().getWidth(); x++) {
            if(!image.getColor(x, y).equals(emptyColor))
                return false;
        }

        return true;
    }

    private boolean checkColumnIsEmpty(Image image, int x) {
        for(int y = 0; y < image.getDimension().getHeight(); y++) {
            if(!image.getColor(x, y).equals(emptyColor))
                return false;
        }

        return true;
    }
}
