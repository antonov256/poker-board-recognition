package com.antonov.poker.board_recognition.config.loading;

import com.antonov.poker.board_recognition.poker.model.CardRank;
import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.recognition.model.SimpleImage;
import com.antonov.poker.board_recognition.recognition.model.Template;
import com.antonov.poker.board_recognition.recognition.model.TemplatesContainer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CardRankTemplatesLoading implements Loading<TemplatesContainer<CardRank>> {
    private final File dir;

    public CardRankTemplatesLoading(File dir) {
        this.dir = dir;
    }

    @Override
    public TemplatesContainer<CardRank> load() {
        if (dir == null || !dir.exists())
            throw new IllegalArgumentException("Wrong templates dir: " + dir);

        List<Template<CardRank>> templates = new ArrayList<>();
        File[] files = dir.listFiles();

        if (files == null) {
            throw new IllegalArgumentException("listFiles() == null in dir: " + dir);
        }

        for (File f : files) {
            if(f == null || !f.exists() || !f.getName().endsWith(".png"))
                continue;

            try {
                BufferedImage bufferedImage = ImageIO.read(f);
                Image image = new SimpleImage(bufferedImage);

                String name = f.getName().replaceAll("\\.png", "");
                name = name.split(" ")[0];
                CardRank cardRank = new CardRank.Creating().fromString(name);
                Template<CardRank> template = new Template<>(image, cardRank);

                templates.add(template);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        TemplatesContainer<CardRank> templatesContainer = new TemplatesContainer<>(templates);
        return templatesContainer;
    }
}
