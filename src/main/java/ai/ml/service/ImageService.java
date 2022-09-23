package ai.ml.service;

import ai.ml.util.Consts;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final FileController fileController;

    private String symbol = "";

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void saveImage(Image image) throws Exception{
        this.fileController.saveImage(image, symbol);
    }

    public double getPixelSum(int[][] bitMapImage) {
        double sum = 0.0;
        for (int x = 0; x < bitMapImage.length; x++) {
            for (int y = 0; y < bitMapImage[0].length; y++) {
                sum += bitMapImage[x][y];
            }
        }
        return sum;
    }


    public int[][] imageForNeiro(Image image) throws Exception {
        image = this.fileController.prepareImageFoNeiro(image);
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        int[][] bitMap = getBitMap(width, height, image.getPixelReader());
        return bitMap;
    }

    int[][] getBitMap(int width, int height, PixelReader pixelReader) throws Exception{
        int[][] bitImage = new int[Consts.xSize][Consts.ySize];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                Color color = pixelReader.getColor(x, y);
                if (color.equals(Color.WHITE)) {
                    bitImage[x][y] = 0;
                } else {
                    bitImage[x][y] = 1;
                }
            }
        }
        return bitImage;
    }
}
