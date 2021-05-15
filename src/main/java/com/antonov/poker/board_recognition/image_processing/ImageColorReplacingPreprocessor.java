package com.antonov.poker.board_recognition.image_processing;

import com.antonov.poker.board_recognition.recognition.model.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageColorReplacingPreprocessor implements ImageProcessor {
    private final Color oldColor;
    private final Color newColor;

    public ImageColorReplacingPreprocessor(Color oldColor, Color newColor) {
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public Image apply(Image image) {
        BufferedImage bufferedImage = image.getBufferedImage();

        int width = image.getDimension().getWidth();
        int height = image.getDimension().getHeight();

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color color = image.getColor(x, y);

                if(color.equals(oldColor))
                    bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return image;
    }
}
