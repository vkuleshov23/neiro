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
import org.springframework.stereotype.Component;


@Component
@FxmlView
@RequiredArgsConstructor
public class MainSceneController {

    @FXML
    private TextField symbol;
    @FXML
    private Canvas canvas;
    private final WritingPixels writingPixels;
    private final ImageService imageService;

    @FXML
    public void initialize() {
        writingPixels.setCanvas(canvas);
        writingPixels.clear();
    }

    public void clear(ActionEvent event) {
        System.out.println("CLEAR");
        writingPixels.clear();

    }

    public void save(ActionEvent actionEvent) {
        imageService.setSymbol(symbol.getText());
        imageService.saveImage(canvas.snapshot(null, null));
        System.out.println("saved");
        writingPixels.clear();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        writingPixels.printPixel(mouseEvent);
    }

}
