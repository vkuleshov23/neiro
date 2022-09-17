package ai.ml.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PerceptronNumber extends Perceptron {

    private String number;

    public PerceptronNumber(String number) {
        super("Number");
        this.number = number;
    }

}
