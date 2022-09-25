package ai.ml.util;

import ai.ml.model.Perceptron;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class NewNeiroCreator {

    public List<Perceptron> createPerceptrons() {
        List<Perceptron> perceptrons = new ArrayList<>();
        for (String symbol : Consts.symbols) {
            perceptrons.add(new Perceptron(symbol, pixels()));
        }
        return perceptrons;
    }

    private double[][] pixels() {
        double[][] pixelsWeight = new double[Consts.xSize][Consts.ySize];
        for (Integer x = 0; x < Consts.xSize; x++) {
            for (Integer y = 0; y < Consts.ySize; y++) {
//                pixelsWeight[x][y] = Randomizer.randInt();
                pixelsWeight[x][y] = Consts.minWeight;
            }
        }
        return pixelsWeight;
    }
}
