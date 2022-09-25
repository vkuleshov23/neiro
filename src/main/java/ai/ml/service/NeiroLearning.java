package ai.ml.service;

import ai.ml.FxApplication;
import ai.ml.event.LearnEvent;
import ai.ml.model.Perceptron;
import ai.ml.util.Consts;
import ai.ml.util.Randomizer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class NeiroLearning {

    Logger logger = LoggerFactory.getLogger(NeiroLearning.class);

    private final ImageService imageService;

    private final FileController fileController;

    @EventListener(LearnEvent.class)
    public void learn(LearnEvent learnEvent) {
        Neiro neiro = learnEvent.getNeiro();
        for (Perceptron perceptron : neiro.getPerceptrons()) {
//            new Thread(() -> perceptronLearnCycle(neiro, perceptron)).start();
            perceptronLearnCycle(neiro, perceptron);
        }
    }

    public void perceptronLearnCycle(Neiro neiro, Perceptron perceptron) {
        logger.info("Start thread for perceptron " + perceptron.getPerceptronSymbol());
        for (int i = 0; i < Consts.selectionSize; i++) {
            learnPerceptron(neiro, perceptron);
        }
        logger.info("End thread for perceptron " + perceptron.getPerceptronSymbol());
    }

    public void learnPerceptron(Neiro neiro, Perceptron perceptron) {
        try {
            String patternSymbol = Randomizer.randSymbol();
            String imageName = imageService.getRandomImage(patternSymbol);
            learnForImage(neiro, perceptron, imageName, patternSymbol);
        } catch (Exception e) {
//            logger.error(e.getMessage());
        }
    }

    private void learnForImage(Neiro neiro, Perceptron perceptron, String imageName, String patternSymbol) {
        try {
            Image image = fileController.loadImage(new File(imageName));
            int[][] bitMap = imageService.imageForNeiro(image, perceptron.getPerceptronSymbol());
            neiro.learn(perceptron, bitMap, imageService.getPixelSum(bitMap), patternSymbol);
        } catch (Exception e) {
//            logger.error(e.getMessage());
        }
    }

}
