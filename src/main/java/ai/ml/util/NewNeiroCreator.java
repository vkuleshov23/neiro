package ai.ml.util;

import ai.ml.model.Perceptron;
import ai.ml.model.PerceptronPixel;
import ai.ml.model.PerceptronSymbol;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class NewNeiroCreator {

    public List<Perceptron> createPerceptrons() {
        List<Perceptron> perceptrons = new ArrayList<>();
        for (PerceptronSymbol symbol : getSymbols()) {
            perceptrons.add(new Perceptron(symbol, pixels()));
        }
        return perceptrons;
    }

    private List<PerceptronSymbol> getSymbols() {
        List<PerceptronSymbol> perceptronSymbols = new ArrayList<>();
        for (String symbol : Consts.symbols) {
            perceptronSymbols.add(new PerceptronSymbol(symbol));
        }
        return perceptronSymbols;
    }

    private Map<PerceptronPixel, Double> pixels() {
        Map<PerceptronPixel, Double> pixelsWeight = new ConcurrentHashMap<>(Consts.xSize * Consts.ySize);
        for (Integer x = 0; x < Consts.xSize; x++) {
            for (Integer y = 0; y < Consts.ySize; y++) {
                pixelsWeight.put(new PerceptronPixel(x, y), Math.random());
            }
        }
        return pixelsWeight;
    }
}
