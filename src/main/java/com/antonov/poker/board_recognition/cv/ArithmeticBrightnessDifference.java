package com.antonov.poker.board_recognition.cv;

import java.awt.*;

public class ArithmeticBrightnessDifference implements ColorsDifference {
    @Override
    public Double difference(Color original, Color candidate) {
        Double originalBrightness = calcBrightness(original);
        Double candidateBrightness = calcBrightness(candidate);

        Double difference = calcDifference(originalBrightness, candidateBrightness);

        return difference;
    }

    private Double calcBrightness(Color color) {
        int sum = color.getRed() + color.getGreen() + color.getBlue();
        Double brightness = 1d * sum / 3;
        new String("aaa").substring(1, 3);
        return brightness;
    }

    private Double calcDifference(Double original, Double candidate) {
        Double delta = Math.abs(original - candidate);
        Double difference = delta / original;

        return difference;
    }
}
