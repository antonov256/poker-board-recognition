package com.antonov.poker.board_recognition.recognition;

import com.antonov.poker.board_recognition.exception.CantRecognizeException;
import com.antonov.poker.board_recognition.poker.model.CardRank;
import com.antonov.poker.board_recognition.recognition.model.Image;

public interface CardRankRecognition {
    CardRank recognize(Image rankImage) throws CantRecognizeException;
}
