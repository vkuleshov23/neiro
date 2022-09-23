package ai.ml.util;

import ai.ml.service.FileController;
import ai.ml.service.Neiro;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriteSaveProgress {

    Logger logger = LoggerFactory.getLogger(WriteSaveProgress.class);

    private final Neiro neiro;

    private final FileController fileController;

    public void save() {
        fileController.saveNeiro(neiro);
        neiro.print();
        logger.info("saved");
    }

    public void load() {
        try {
            neiro.setPerceptrons(fileController.loadNeiro().getPerceptrons());
        } catch (Exception e) {
            logger.warn(e.getMessage());
            neiro.postConstruct();
        }
        neiro.print();
        logger.info("loaded");
    }

}
