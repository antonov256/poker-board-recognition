package com.antonov.poker.board_recognition.recognition;

import com.antonov.poker.board_recognition.exception.CantRecognizeException;
import com.antonov.poker.board_recognition.poker.model.Card;
import com.antonov.poker.board_recognition.recognition.model.Image;

public interface CardRecognition {
    Card recognize(Image cardImage) throws CantRecognizeException;
}
