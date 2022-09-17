package ai.ml.service;

import ai.ml.util.SizeConsts;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

@Service
public class FileController {

    private static final String fileSystemDelimiter = "\\";

    private static final String way = "E:\\DataSet\\";



    public int filesCount(String fileWay) throws Exception {
        try {
            return Objects.requireNonNull(new File(way + fileWay).list()).length;
        } catch (Exception e) {
            if (new File(way + fileWay).mkdirs()) {
                return 0;
            }
        }
        throw new Exception("Can't create directory");
    }



    public void saveImage(Image image, String ... fileWaySlices) {
        try {
            String fileWay = toOneWay(fileWaySlices);
            File file = new File(way + fileWay + filesCount(fileWay) + ".png");
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(toPerceptronResolution(image), null);
            ImageIO.write(bufferedImage, "png", file);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String toOneWay(String ... fileWay) {
        StringBuilder oneWay = new StringBuilder();
        for(String slice : fileWay){
            oneWay.append(slice).append(fileSystemDelimiter);
        }
        return oneWay.toString();
    }

    private Image toPerceptronResolution(Image image) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(SizeConsts.xSize);
        imageView.setFitWidth(SizeConsts.ySize);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        return convertToBinary(imageView.snapshot(null, null));
    }

    private Image convertToBinary(Image image) {
        WritableImage writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = writableImage.getPixelReader();
        for (int i = 0; i < writableImage.getHeight(); i++) {
            for (int j = 0; j < writableImage.getWidth(); j++) {
                Color c = pixelReader.getColor(j, i);
                if (c.getRed() > 0 || c.getGreen() > 0 || c.getBlue() > 0) {
                    pixelWriter.setColor(j, i, Color.WHITE);
                } else {
                    pixelWriter.setColor(j, i, Color.BLACK);
                }
            }
        }
        return new ImageView(writableImage).snapshot(null, null);
    }
}
