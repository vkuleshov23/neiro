package ai.ml.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PerceptronPixel implements Serializable {
    private final Integer x;
    private final Integer y;

    public PerceptronPixel(Integer x, Integer y) {;
        this.x = x;
        this.y = y;
    }
}
