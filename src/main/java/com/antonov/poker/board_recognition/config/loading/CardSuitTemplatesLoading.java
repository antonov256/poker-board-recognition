package com.antonov.poker.board_recognition.config.loading;

import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.recognition.model.SimpleImage;
import com.antonov.poker.board_recognition.recognition.model.Template;
import com.antonov.poker.board_recognition.recognition.model.TemplatesContainer;
import com.antonov.poker.board_recognition.poker.model.CardSuit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CardSuitTemplatesLoading implements Loading<TemplatesContainer<CardSuit>> {
    private final File dir;

    public CardSuitTemplatesLoading(File dir) {
        this.dir = dir;
    }

    @Override
    public TemplatesContainer<CardSuit> load() {
        if(dir == null || !dir.exists())
            throw new IllegalArgumentException("Wrong templates dir: " + dir);

        List<Template<CardSuit>> templates = new ArrayList<>();
        File[] files = dir.listFiles();

        if(files == null) {
            throw new IllegalArgumentException("listFiles() == null in dir: " + dir);
        }

        for(File f : files) {
            if(f == null || !f.exists() || !f.getName().endsWith(".png"))
                continue;

            try {
                BufferedImage bufferedImage = ImageIO.read(f);
                Image image = new SimpleImage(bufferedImage);

                String name = f.getName().replaceAll("\\.png", "");
                name = name.split(" ")[0];

                CardSuit cardSuit = new CardSuit.Creating().fromString(name);
                Template<CardSuit> template = new Template<>(image, cardSuit);

                templates.add(template);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TemplatesContainer<CardSuit> templatesContainer = new TemplatesContainer<>(templates);
        return templatesContainer;
    }
}
