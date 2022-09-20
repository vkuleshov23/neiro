package ai.ml.controller;

import ai.ml.service.ImageService;
import ai.ml.service.WritingPixels;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@FxmlView
@Controller
@RequiredArgsConstructor
public class MainSceneController {

    @FXML
    private TextField symbol;
    @FXML
    private Canvas canvas;
    private final WritingPixels writingPixels;
    private final ImageService imageService;

    Logger logger = LoggerFactory.getLogger(MainSceneController.class);

    @FXML
    public void initialize() {
        writingPixels.setCanvas(canvas);
        writingPixels.clear();
    }

    public void clear(ActionEvent event) {
        writingPixels.clear();
        logger.debug("CLEARED");
    }

    public void save(ActionEvent actionEvent) {
        imageService.setSymbol(symbol.getText());
        imageService.saveImage(canvas.snapshot(null, null));
        writingPixels.clear();
        logger.debug("SAVED");
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        writingPixels.printPixel(mouseEvent);
    }

}
