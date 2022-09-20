package ai.ml.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PerceptronSymbol implements Serializable {

    private String number;

    public PerceptronSymbol(String number) {
        this.number = number;
    }

}
