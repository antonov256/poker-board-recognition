package com.antonov.poker.board_recognition.config.parsing;

import java.awt.*;
import java.util.ResourceBundle;

public class ColorResourceBundleParser implements ResourceBundleParser<Color> {
    @Override
    public Color parse(ResourceBundle resourceBundle, String prefix) {
        String colorStr = resourceBundle.getString(prefix);
        String[] channelsStr = colorStr.split(",");
        if (channelsStr.length != 3) {
            throw new IllegalArgumentException("Can't parse color from string: '" + colorStr + "' in resourceBundle " + resourceBundle.getBaseBundleName());
        }

        int r = Integer.parseInt(channelsStr[0].trim());
        int g = Integer.parseInt(channelsStr[1].trim());
        int b = Integer.parseInt(channelsStr[2].trim());

        return new Color(r, g, b);
    }
}
