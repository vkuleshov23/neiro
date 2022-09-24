package ai.ml.service;

import ai.ml.model.Perceptron;
import ai.ml.util.Consts;
import ai.ml.util.NewNeiroCreator;
import javafx.util.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@Getter
@Setter
@RequiredArgsConstructor
public class Neiro implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Neiro.class);

    private List<Perceptron> perceptrons = new ArrayList<>();

    public void createNewPerceptrones() {
        this.perceptrons = NewNeiroCreator.createPerceptrons();
    }

    public Perceptron findBySymbol(String symbol) throws Exception {
        return perceptrons.stream()
                .filter( perceptron -> perceptron.equalsBySymbol(symbol))
                .findAny().orElseThrow(() -> new Exception("No such perceptron exist!"));
    }

    public List<Perceptron> findOther(String symbol) throws Exception {
        return perceptrons.stream()
                .filter(p -> !p.equalsBySymbol(symbol)).toList();
    }

    public void learn(int[][] bitMap, double referenceSum, String symbol) throws Exception {
        Pair<String, Double> predict = getPredicts(bitMap, referenceSum).stream()
                .findFirst().orElseThrow(() -> new Exception("Predicts is empty!"));
        logger.info("perceptron -> {} | symbol -> {}", predict.getKey(), symbol);
        if (!predict.getKey().equals(symbol)) {
            punish(bitMap, symbol);
        } else if (predict.getValue() < Consts.threshold) {
            punish(bitMap, symbol);
        }
    }

    public void punish(int[][] bitMap, String symbol) throws Exception {
        findBySymbol(symbol).prise(bitMap);
//        findOther(symbol).forEach(p -> p.punish(bitMap));
    }

    public List<Pair<String, Double>> getPredicts(int[][] bitMap, double referenceSum) {
        Comparator<Pair<String, Double>> comparator = (double1, double2) -> {
                return double2.getValue().compareTo(double1.getValue());
        };
        return perceptrons.stream().map(
                perceptron -> new Pair<>(perceptron.getPerceptronSymbol(), perceptron.getSum(bitMap, referenceSum)))
                .sorted(comparator).toList();
    }

    public void print() {
        perceptrons.forEach(perceptron ->  logger.info(perceptron.toString()));
    }

    public void postConstruct() {
        createNewPerceptrones();
    }

}
