package com.antonov.poker.board_recognition.recognition.model;

public class Template<T> {
    private final Image image;
    private final T object;

    public Template(Image image, T object) {
        this.image = image;
        this.object = object;
    }

    public Image getImage() {
        return image;
    }

    public T getObject() {
        return object;
    }
}
