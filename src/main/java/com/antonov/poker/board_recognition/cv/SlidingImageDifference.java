package com.antonov.poker.board_recognition.cv;

import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.Image;

public class SlidingImageDifference implements ImageDifference {
    private final ImageDifference imageDifference;

    public SlidingImageDifference(ImageDifference imageDifference) {
        this.imageDifference = imageDifference;
    }

    @Override
    public Double difference(Image original, Image candidate) {
        if (original.getDimension().getWidth() > candidate.getDimension().getWidth()
                || original.getDimension().getHeight() > candidate.getDimension().getHeight())
            throw new IllegalArgumentException("candidate size < original size: " + original.getDimension() + " < " + candidate.getDimension());

        int deltaX = candidate.getDimension().getWidth() - original.getDimension().getWidth();
        int deltaY = candidate.getDimension().getHeight() - original.getDimension().getHeight();

        Double minDifference = Double.MAX_VALUE;
        for(int x = 0; x < deltaX; x++) {
            for(int y = 0; y < deltaY; y++) {
                Crop crop = new Crop(x, y, original.getDimension().getWidth(), original.getDimension().getHeight());
                Image candidateSubImage = candidate.getSubImage(crop);

                Double difference = imageDifference.difference(original, candidateSubImage);
                if(difference < minDifference)
                    minDifference = difference;
            }
        }

        return minDifference;
    }
}
