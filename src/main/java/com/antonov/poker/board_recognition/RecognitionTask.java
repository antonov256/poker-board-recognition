package com.antonov.poker.board_recognition;

import com.antonov.poker.board_recognition.poker.model.Board;
import com.antonov.poker.board_recognition.recognition.BoardRecognition;
import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.recognition.model.SimpleImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RecognitionTask {
    private final File imagesDir;
    private final BoardRecognition boardRecognition;

    private final File unrecognizedDir;

    private int correctCount;
    private int totalCount;

    public RecognitionTask(File imagesDir, BoardRecognition boardRecognition) {
        this.imagesDir = imagesDir;
        this.boardRecognition = boardRecognition;

        unrecognizedDir = new File(imagesDir, "unrecognized " + System.currentTimeMillis());
    }

    public void run() {
        if (!imagesDir.exists()) {
            System.err.println("imagesDir not exists: " + imagesDir);
            return;
        }

        if (!imagesDir.isDirectory()) {
            System.err.println("imagesDir is not directory: " + imagesDir);
            return;
        }

        File[] files = imagesDir.listFiles();
        if (files == null) {
            System.err.println("files == null. imagesDir: " + imagesDir);
            return;
        }

        for (File f : files) {
            if (f == null || !f.exists() || !f.getName().endsWith(".png"))
                continue;

            try {
                BufferedImage bufferedImage = ImageIO.read(f);
                Image image = new SimpleImage(bufferedImage);

                Board recognizedBoard = boardRecognition.recognize(image);

                String fileStr = f.getName().replaceAll("\\.png", "");
                Board fileBoard = new Board.Creating().fromString(fileStr);

                System.out.println(f.getName() + " - " + recognizedBoard.toString());

                if (recognizedBoard.equals(fileBoard)) {
                    correctCount++;
                } else {
                    System.err.println("## ERROR ## Wrong recognition for file: " + f.getName());
                    copyUnrecognizedImage(f);
                }
            } catch (IOException e) {
                e.printStackTrace();
                copyUnrecognizedImage(f);
            }

            totalCount++;
        }
    }

    private void copyUnrecognizedImage(File file) {
        if (!unrecognizedDir.exists()) {
            unrecognizedDir.mkdir();
        }

        try {
            Files.copy(file.toPath(), new File(unrecognizedDir, file.getName()).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
