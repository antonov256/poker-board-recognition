package com.antonov.poker.board_recognition.recognition.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Image {
    BufferedImage getBufferedImage();

    Dimension getDimension();

    Image getSubImage(Crop crop);

    Color getColor(int x, int y);
}
