package com.antonov.poker.board_recognition.cv;

import java.awt.*;

public interface ColorsDifference {
    Double difference(Color original, Color candidate);
}
