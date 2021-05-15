package com.antonov.poker.board_recognition.config.parsing;

import java.util.ResourceBundle;

public interface ResourceBundleParser<T> {
    T parse(ResourceBundle resourceBundle, String prefix);
}
