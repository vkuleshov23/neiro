package ai.ml.model;

import ai.ml.util.Consts;
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
    private final String perceptronSymbol;
    private final double[][] pixelWeight;

    public double getSum(int[][] bitMap, double referenceSum) {
        double sum = 0.0;
        for (int x = 0; x < pixelWeight.length; x++) {
            for (int y = 0; y < pixelWeight[0].length; y++) {
                sum += bitMap[x][y] * pixelWeight[x][y];
            }
        }
        return sum/referenceSum;
    }

    public boolean equalsBySymbol(String symbol) {
        return perceptronSymbol.equals(symbol);
    }

    public void punish(int[][] bitMap) {
        for (int x = 0; x < pixelWeight.length; x++) {
            for (int y = 0; y < pixelWeight[0].length; y++) {
                pixelWeight[x][y] += bitMap[x][y] == 0 ? Consts.step : -Consts.step;
                pixelWeight[x][y] = Math.min(pixelWeight[x][y], 1.0);
                pixelWeight[x][y] = Math.max(pixelWeight[x][y], 0.0);
            }
        }
    }

    public void prise(int[][] bitMap) {
        for (int x = 0; x < pixelWeight.length; x++) {
            for (int y = 0; y < pixelWeight[0].length; y++) {
                pixelWeight[x][y] += bitMap[x][y] == 0 ? -Consts.step : Consts.step;
                pixelWeight[x][y] = Math.min(pixelWeight[x][y], 1.0);
                pixelWeight[x][y] = Math.max(pixelWeight[x][y], 0.0);
            }
        }
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

    @Override
    public String toString() {
        String res = perceptronSymbol + " {";
        for (int x = 0; x < pixelWeight.length; x++) {
            for (int y = 0; y < pixelWeight[0].length; y++) {
                res += "({" + x + ":" + y + "} = " + pixelWeight[x][y] + ") ";
            }
        }
        res += "}";
        return  res;
    }

}
