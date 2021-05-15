package com.antonov.poker.board_recognition.config.parsing;

import com.antonov.poker.board_recognition.recognition.model.Crop;

import java.util.ResourceBundle;

public class CropResourceBundleParser implements ResourceBundleParser<Crop> {
    @Override
    public Crop parse(ResourceBundle resourceBundle, String prefix) {
        String x = resourceBundle.getString(prefix + ".x");
        String y = resourceBundle.getString(prefix + ".y");
        String w = resourceBundle.getString(prefix + ".w");
        String h = resourceBundle.getString(prefix + ".h");

        Crop crop = new Crop(
                Integer.parseInt(x),
                Integer.parseInt(y),
                Integer.parseInt(w),
                Integer.parseInt(h)
        );

        return crop;
    }
}
