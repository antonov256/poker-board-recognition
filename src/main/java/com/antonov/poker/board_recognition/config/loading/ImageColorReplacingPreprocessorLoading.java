package com.antonov.poker.board_recognition.config.loading;

import com.antonov.poker.board_recognition.config.parsing.ResourceBundleParser;
import com.antonov.poker.board_recognition.image_processing.ImageColorReplacingPreprocessor;
import com.antonov.poker.board_recognition.image_processing.ImageProcessor;

import java.awt.*;
import java.util.ResourceBundle;

public class ImageColorReplacingPreprocessorLoading implements Loading<ImageProcessor> {
    private final String bundleName;
    private final ResourceBundleParser<Color> colorParser;

    public ImageColorReplacingPreprocessorLoading(String bundleName, ResourceBundleParser<Color> colorParser) {
        this.bundleName = bundleName;
        this.colorParser = colorParser;
    }

    @Override
    public ImageColorReplacingPreprocessor load() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);

        Color oldGrayColor = colorParser.parse(resourceBundle, "recognition.preprocessor.oldColor");
        Color newWhiteColor = colorParser.parse(resourceBundle, "recognition.preprocessor.newColor");

        ImageColorReplacingPreprocessor imageColorReplacingPreprocessor = new ImageColorReplacingPreprocessor(oldGrayColor, newWhiteColor);

        return imageColorReplacingPreprocessor;
    }
}
