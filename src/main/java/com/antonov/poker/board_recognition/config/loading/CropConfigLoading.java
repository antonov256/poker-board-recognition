package com.antonov.poker.board_recognition.config.loading;

import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.CropConfig;

import java.util.ResourceBundle;

public class CropConfigLoading {
    public CropConfig load() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("crop_config");

        Crop cropCard1 = readCard(resourceBundle, 1);
        Crop cropCard2 = readCard(resourceBundle, 2);
        Crop cropCard3 = readCard(resourceBundle, 3);
        Crop cropCard4 = readCard(resourceBundle, 4);
        Crop cropCard5 = readCard(resourceBundle, 5);

        CropConfig cropConfig = new CropConfig(
                cropCard1,
                cropCard2,
                cropCard3,
                cropCard4,
                cropCard5
        );

        return cropConfig;
    }

    private Crop readCard(ResourceBundle resourceBundle, int cardNum) {
        String cardName = "card" + cardNum;

        String x = resourceBundle.getString(cardName + ".x");
        String y = resourceBundle.getString(cardName + ".y");
        String w = resourceBundle.getString(cardName + ".w");
        String h = resourceBundle.getString(cardName + ".h");

        Crop crop = new Crop(
                Integer.parseInt(x),
                Integer.parseInt(y),
                Integer.parseInt(w),
                Integer.parseInt(h)
        );

        return crop;
    }
}
