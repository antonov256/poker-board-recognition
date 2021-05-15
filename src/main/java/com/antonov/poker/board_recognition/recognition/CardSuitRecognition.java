package com.antonov.poker.board_recognition.recognition;

import com.antonov.poker.board_recognition.exception.CantRecognizeException;
import com.antonov.poker.board_recognition.poker.model.CardSuit;
import com.antonov.poker.board_recognition.recognition.model.Image;

public interface CardSuitRecognition {
    CardSuit recognize(Image suitImage) throws CantRecognizeException;
}
