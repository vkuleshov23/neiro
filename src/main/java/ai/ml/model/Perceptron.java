package ai.ml.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Perceptron implements Serializable {
    private final PerceptronSymbol perceptronSymbol;
    private final Map<PerceptronPixel, Double> pixelWeight;

    public boolean equalsBySymbol(PerceptronSymbol symbol) {
        return Objects.equals(perceptronSymbol, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perceptron that = (Perceptron) o;
        return Objects.equals(perceptronSymbol, that.perceptronSymbol);
    }

    @Override
    public int hashCode() {
        return perceptronSymbol.hashCode();
    }

}
