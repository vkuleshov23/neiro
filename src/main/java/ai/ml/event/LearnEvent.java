package ai.ml.event;

import ai.ml.service.Neiro;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LearnEvent extends ApplicationEvent {

    Neiro neiro;

    public LearnEvent(Neiro neiro) {
        super(neiro);
        this.neiro = neiro;
    }
}
