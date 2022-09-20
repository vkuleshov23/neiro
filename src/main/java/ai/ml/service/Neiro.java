package ai.ml.service;

import ai.ml.model.Perceptron;
import ai.ml.model.PerceptronSymbol;
import ai.ml.util.NewNeiroCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Service
@Getter
@Setter
@RequiredArgsConstructor
public class Neiro implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(Neiro.class);

    private List<Perceptron> perceptrons = new ArrayList<>();

    public void createNewPerceptrones() {
        this.perceptrons = NewNeiroCreator.createPerceptrons();
    }

    public Perceptron findBySymbol(PerceptronSymbol perceptronSymbol) throws Exception {
        return perceptrons.stream()
                .filter( perceptron -> perceptron.equalsBySymbol(perceptronSymbol))
                .findAny().orElseThrow(() -> new Exception("No such perceptron exist!"));
    }

//    @PostConstruct
    public void print() {
//        createNewPerceptrones();
        perceptrons.forEach(System.out::println);
    }

}
