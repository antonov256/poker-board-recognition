package com.antonov.poker.board_recognition.recognition;

import com.antonov.poker.board_recognition.poker.model.Board;
import com.antonov.poker.board_recognition.recognition.model.Image;

public interface BoardRecognition {
    Board recognize(Image image);
}
