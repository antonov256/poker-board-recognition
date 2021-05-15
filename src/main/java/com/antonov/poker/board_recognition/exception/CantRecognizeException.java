package com.antonov.poker.board_recognition.exception;

public class CantRecognizeException extends Exception {
    public CantRecognizeException() {
    }

    public CantRecognizeException(String message) {
        super(message);
    }

    public CantRecognizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
