package com.antonov.poker.board_recognition;

import com.antonov.poker.board_recognition.recognition.BoardRecognition;
import com.antonov.poker.board_recognition.recognition.model.Image;
import com.antonov.poker.board_recognition.recognition.model.SimpleImage;
import com.antonov.poker.board_recognition.poker.model.Board;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RecognitionApplication {
    private final String imagesPath;
    private final BoardRecognition boardRecognition;

    private final File unrecognizedDir;

    private int correctCount;
    private int totalCount;

    public RecognitionApplication(String imagesPath, BoardRecognition boardRecognition) {
        this.imagesPath = imagesPath;
        this.boardRecognition = boardRecognition;

        unrecognizedDir = new File(imagesPath, "unrecognized " + System.currentTimeMillis());
    }

    public String start() {
        File dir = new File(imagesPath);

        if(!dir.exists() || !dir.isDirectory())
            return "!dir.exists() || !dir.isDirectory(). dir = " + dir;

        File[] files = dir.listFiles();
        if(files == null)
            return "files == null";

        for(File f : files) {
            if(f == null || !f.exists() || !f.getName().endsWith(".png"))
                continue;

            try {
                BufferedImage bufferedImage = ImageIO.read(f);
                Image image = new SimpleImage(bufferedImage);

                Board recognizedBoard = boardRecognition.recognize(image);

                String fileStr = f.getName().replaceAll("\\.png", "");
                Board fileBoard = new Board.Utils().fromString(fileStr);

                System.out.println(f.getName() + " - " + recognizedBoard.toString());

                if(recognizedBoard.equals(fileBoard)) {
                    correctCount++;
                } else {
                    System.out.println("## ERROR " + f.getName());
                    copyUnrecognizedImage(f);
                }
            } catch (IOException e) {
                e.printStackTrace();
                copyUnrecognizedImage(f);
            }

            totalCount++;
        }

        String summary = "Correct: " + correctCount + " / " + totalCount + " (" + correctCount * 100d / totalCount + "%)";
        return summary;
    }

    private void copyUnrecognizedImage(File file) {
        if(!unrecognizedDir.exists()) {
            unrecognizedDir.mkdir();
        }

        try {
            Files.copy(file.toPath(), new File(unrecognizedDir, file.getName()).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
