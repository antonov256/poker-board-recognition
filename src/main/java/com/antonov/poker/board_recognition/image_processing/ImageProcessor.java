package com.antonov.poker.board_recognition.image_processing;

import com.antonov.poker.board_recognition.recognition.model.Image;

public interface ImageProcessor {
    Image apply (Image image);
}
