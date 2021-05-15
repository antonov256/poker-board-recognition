package com.antonov.poker.board_recognition.cv;

import com.antonov.poker.board_recognition.recognition.model.Image;

public interface ImageDifference {
    Double difference(Image original, Image candidate);
}
