package ai.ml.util;

import ai.ml.service.FileController;
import ai.ml.service.Neiro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriteSaveProgress {

    private final Neiro neiro;

    private final FileController fileController;

    public void save() {
        fileController.saveNeiro(neiro);
        neiro.print();
    }

    public void load() {
        neiro.setPerceptrons(fileController.loadNeiro().getPerceptrons());
        neiro.print();
    }

}
