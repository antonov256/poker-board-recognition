package com.antonov.poker.board_recognition.image_check;

import com.antonov.poker.board_recognition.recognition.model.Image;

import java.awt.*;

public class ImageIsEmptyCheck implements ImageCheck {
    private final Color emptyColor;

    public ImageIsEmptyCheck(Color emptyColor) {
        this.emptyColor = emptyColor;
    }

    @Override
    public Boolean check(Image image) {
        int width = image.getDimension().getWidth();
        int height = image.getDimension().getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = image.getColor(x, y);
                if (!color.equals(emptyColor))
                    return false;
            }
        }

        return true;
    }
}
