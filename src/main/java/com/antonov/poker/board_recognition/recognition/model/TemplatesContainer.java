package com.antonov.poker.board_recognition.recognition.model;

import java.util.List;

public class TemplatesContainer <T> {
    private final List<Template<T>> templates;

    public TemplatesContainer(List<Template<T>> templates) {
        this.templates = templates;
    }

    public List<Template<T>> getTemplates() {
        return templates;
    }
}
