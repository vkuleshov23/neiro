package ai.ml.model;

import lombok.*;

@Getter
@Setter
@ToString
public class PerceptronPixel extends Perceptron {
    private final Integer x;
    private final Integer y;

    public PerceptronPixel(Integer x, Integer y) {
        super("Pixel");
        this.x = x;
        this.y = y;
    }
}
