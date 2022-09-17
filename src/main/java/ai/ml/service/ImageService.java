package ai.ml.service;

import ai.ml.util.SizeConsts;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileController fileController;

    private String symbol = "";

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void saveImage(Image image) {
        this.fileController.saveImage(image, symbol);
    }




}
