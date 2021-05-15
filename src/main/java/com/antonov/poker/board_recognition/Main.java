package com.antonov.poker.board_recognition;

import com.antonov.poker.board_recognition.config.RecognitionApplicationConfiguration;

import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You need to specify images path as argument");
            return;
        }

        String imagesDirPath = args[0];

        ResourceBundle resourceBundle = ResourceBundle.getBundle("settings");
        String templatesDirPath = resourceBundle.getString("templates.dir");

        RecognitionApplicationConfiguration recognitionApplicationConfiguration = new RecognitionApplicationConfiguration(templatesDirPath);

        RecognitionApplication recognitionApplication = new RecognitionApplication(
                imagesDirPath,
                recognitionApplicationConfiguration.getBoardRecognition()
        );

        String summary = recognitionApplication.start();
        System.out.println(summary);
    }
}
