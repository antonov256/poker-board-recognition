package com.antonov.poker.board_recognition.config.loading;

import com.antonov.poker.board_recognition.config.parsing.ResourceBundleParser;
import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;

import java.util.ResourceBundle;

public class CropConfigLoading implements Loading<CropConfig> {
    private final String bundleName;
    private final ResourceBundleParser<Crop> cropParser;

    public CropConfigLoading(String bundleName, ResourceBundleParser<Crop> cropParser) {
        this.bundleName = bundleName;
        this.cropParser = cropParser;
    }

    @Override
    public CropConfig load() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName);

        Crop cropCard1 = cropParser.parse(resourceBundle, "card" + 1);
        Crop cropCard2 = cropParser.parse(resourceBundle, "card" + 2);
        Crop cropCard3 = cropParser.parse(resourceBundle, "card" + 3);
        Crop cropCard4 = cropParser.parse(resourceBundle, "card" + 4);
        Crop cropCard5 = cropParser.parse(resourceBundle, "card" + 5);

        CropConfig cropConfig = new CropConfig(
                cropCard1,
                cropCard2,
                cropCard3,
                cropCard4,
                cropCard5
        );

        return cropConfig;
    }
}
