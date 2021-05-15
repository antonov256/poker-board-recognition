package com.antonov.poker.board_recognition.image_check;

import com.antonov.poker.board_recognition.recognition.model.Crop;
import com.antonov.poker.board_recognition.recognition.model.Image;

public class CardIsPresentedCheck implements ImageCheck {
    private final ImageIsEmptyCheck imageIsEmptyCheck;
    private final Crop fragmentCrop;

    public CardIsPresentedCheck(ImageIsEmptyCheck imageIsEmptyCheck, Crop fragmentCrop) {
        this.imageIsEmptyCheck = imageIsEmptyCheck;
        this.fragmentCrop = fragmentCrop;
    }

    @Override
    public Boolean check(Image image) {
        Image imageFragment = image.getSubImage(fragmentCrop);
        Boolean cropForCheckIsEmpty = imageIsEmptyCheck.check(imageFragment);
        Boolean cardIsPresented = cropForCheckIsEmpty;

        return cardIsPresented;
    }
}
