package com.antonov.poker.board_recognition.recognition.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimpleImage implements Image {
    private final BufferedImage bufferedImage;
    private final Dimension dimension;

    public SimpleImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        this.dimension = new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public Image getSubImage(Crop crop) {
        BufferedImage bufferedImage = getBufferedImage()
                .getSubimage(
                        crop.getX(),
                        crop.getY(),
                        crop.getW(),
                        crop.getH()
                );

        return new SimpleImage(bufferedImage);
    }

    @Override
    public Color getColor(int x, int y) {
        return new Color(bufferedImage.getRGB(x, y));
    }
}
