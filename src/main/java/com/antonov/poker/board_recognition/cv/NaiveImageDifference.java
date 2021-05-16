package com.antonov.poker.board_recognition.cv;

import com.antonov.poker.board_recognition.recognition.model.Image;

import java.awt.*;

public class NaiveImageDifference implements ImageDifference {
    private final ColorsDifference colorsDifference;

    public NaiveImageDifference(ColorsDifference colorsDifference) {
        this.colorsDifference = colorsDifference;
    }

    @Override
    public Double difference(Image original, Image candidate) {
        if (!original.getDimension().equals(candidate.getDimension()))
            throw new IllegalArgumentException("Image dimensions not equal: " + original.getDimension() + ", " + candidate.getDimension());

        int width = original.getDimension().getWidth();
        int height = original.getDimension().getHeight();

        Double differenceSum = 0d;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color originalColor = original.getColor(x, y);
                Color candidateColor = candidate.getColor(x, y);

                Double difference = colorsDifference.difference(originalColor, candidateColor);
                differenceSum += difference;
            }
        }

        Double avgDifference = differenceSum / width / height;

        return avgDifference;
    }
}
