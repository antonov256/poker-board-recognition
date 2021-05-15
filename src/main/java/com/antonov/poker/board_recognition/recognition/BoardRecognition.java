package com.antonov.poker.board_recognition.recognition;

import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.poker.model.Board;

public interface BoardRecognition {
    Board recognize(Image image);
}
